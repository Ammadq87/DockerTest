package com.app.core.config;


import com.app.core.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "4475354fc6ad9678d38bd8eebc23be8a37ca38e36e34a6d242ce6379ba00a3c7";

    public static String generateToken(String username,
                                       String name,
                                       String email,
                                       String userId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("name", name)
                .claim("email", email)
                .claim("username", username)
                .claim("userID", userId)
                .setSubject(name).setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static Boolean isTokenExpired(String token) {
        Claims claims = parseToken(token);

        if (claims == null)
            return false;

        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

    public static String addJwtCookie(HttpServletResponse res, User user) {
        String jwtToken = JwtUtil.generateToken(user.getUsername(), user.getName(), user.getEmail(), user.getId());

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

    public static String getCookieByName(Cookie[] cookies, String name) {
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}