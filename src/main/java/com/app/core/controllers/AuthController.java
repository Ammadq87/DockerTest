package com.app.core.controllers;

import com.app.core.models.User;
import com.app.core.services.AuthService;
import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.app.core.config.JwtUtil.addJwtCookie;
import static com.app.core.config.JwtUtil.clearCookies;

@RestController
@RequestMapping(path = "api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService as) {
        this.authService = as;
    }

    @PostMapping(path = "/register")
    public ResponseEntity<String> register(@RequestBody User user, HttpServletRequest req, HttpServletResponse res) {
        try {
            authService.register(user);
            req.getSession(true);
            String jwt = addJwtCookie(res, user);
            return new ResponseEntity<>(jwt, HttpStatus.CREATED);
        } catch (AuthException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody User loginBody, HttpServletRequest req, HttpServletResponse res) {
        try {
            // Create session if DNE
            req.getSession(true);
            User user = authService.login(loginBody.getEmail(), loginBody.getPassword());
            String jwt = addJwtCookie(res, user);
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (AuthException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logout(HttpServletRequest req, HttpServletResponse res) {
        HttpSession session = req.getSession();
        clearCookies(req, res);
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}