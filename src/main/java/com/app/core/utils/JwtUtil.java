package com.app.core.utils;

import com.app.core.exceptions.AccountException;
import com.app.core.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class JwtUtil {

    private static byte[] JWT_SECRET;

    @Value("${JWT_SECRET}")
    private byte[] jwtSecretTemp;

    @PostConstruct
    public void init() {
        JWT_SECRET = jwtSecretTemp;
    }

    public static byte[] getJwtSecret() {
        return JWT_SECRET;
    }

    public static String generateToken(String username,
                                       String name,
                                       String email) {
        return Jwts.builder()
                .setSubject(username)
                .claim("name", name)
                .claim("email", email)
                .claim("username", username)
                .setSubject(name).setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET)
                .compact();
    }

    public static Claims parseJwt(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return Jwts.claims();
        }

        String token = authorizationHeader.substring(7);

        return parseToken(token);
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public static Boolean isTokenExpired(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        Claims claims = parseToken(token);

        if (claims == null)
            return false;

        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    public static String addJwtCookie(HttpServletResponse res, User user) {
        String jwtToken = generateToken(user.getUsername(), user.getName(), user.getEmail());

        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setPath("/"); // cookie is available to all routes of the application
        cookie.setHttpOnly(false); // cookie if sent ONLY over HTTP, JS cannot access if true for security reasons
        cookie.setSecure(false); // inaccessible to JavaScript, only true when using HTTPS
        cookie.setMaxAge(60 * 60); // 1 minute
        res.addCookie(cookie);

        return jwtToken;
    }

    public static void clearCookies(HttpServletRequest req, HttpServletResponse res) {
        for (Cookie c : req.getCookies()) {
            c.setValue(null);
            c.setMaxAge(0);
            c.setSecure(true);
            c.setHttpOnly(true);
            c.setPath("/");
            res.addCookie(c);
        }
    }

    public static Object getAttributeFromToken(String attribute, HttpServletRequest request) throws AccountException {
        var user = parseJwt(request);

        if (user == null) {
            throw new AccountException("Unauthorized Account");
        }

        if (user.containsKey(attribute)) {
            return user.get(attribute);
        }

        throw new RuntimeException("Attribute does not exist");
    }

    public JwtUtil() {
        // empty constructor
    }
}