package com.app.core.services;

import com.app.core.config.HashingUtil;
import com.app.core.exceptions.HashException;
import com.app.core.models.User;
import com.app.core.repositories.AuthDAO;
import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class AuthService {
    private final AuthDAO authDAO;

    public AuthService(AuthDAO ar) {
        this.authDAO = ar;
    }

    public void register(User u) throws AuthException {
        try {
            Optional<User> numberOfUsers = authDAO.findUserByEmailOrUsername(u.getEmail(), u.getUsername());

            if (numberOfUsers.isPresent()) {
                throw new AuthException("Email or Username already exists");
            }

            u.setPassword(HashingUtil.getHash(u.getPassword()));
            u.setId(UUID.randomUUID().toString());
            u.setCreatedOn(LocalDateTime.now());
            u.setLastUpdated(LocalDateTime.now());
            authDAO.save(u);
        } catch (HashException | RuntimeException e) {
            throw new AuthException(e.getMessage());
        }
    }

    public User login(String email, String password) throws AuthException {
        try {
            Optional<User> found = authDAO.findUserByEmailAndPassword(email, HashingUtil.getHash(password));

            if (found.isEmpty()) {
                throw new AuthException("Invalid email or password");
            }

            return found.get();
        } catch (HashException | RuntimeException e) {
            throw new AuthException(e.getMessage());
        }
    }
}
