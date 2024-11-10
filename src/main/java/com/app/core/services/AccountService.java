package com.app.core.services;

import com.app.core.exceptions.AccountException;
import com.app.core.models.User;
import com.app.core.repositories.AccountDAO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class AccountService {
    private final AccountDAO accountDAO;

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public User getAccountInformation(String username) throws AccountException {
        // Maybe rework this to get all information like goals and following
        Optional<User> user = accountDAO.findById(username);

        if (user.isPresent()) {
            return user.get();
        }

        throw new AccountException("User not found");
    }

    public List<User> getAccountsBySearchParam(String searchParam) {
        Optional<List<User>> results = accountDAO.findAccountsBySearchParam(searchParam);
        return results.orElseGet(ArrayList::new);
    }

    public void updateAccountInformation(User newUser) throws AccountException {
        Optional<User> result = accountDAO.findById(newUser.getUsername());

        if (result.isEmpty()) {
            throw new AccountException("User not found");
        }

        User currUser = result.get();

        if (!currUser.getUsername().equals(newUser.getUsername())) {
            Optional<List<User>> username = accountDAO.findUserByUsername(newUser.getUsername());

            if (username.isPresent() && !username.get().isEmpty()) {
                throw new AccountException("Username already in use");
            }

        }

        if (!currUser.getEmail().equals(newUser.getEmail())) {
            Optional<List<User>> email = accountDAO.findUserByEmail(newUser.getEmail());

            if (email.isPresent() && !email.get().isEmpty()) {
                throw new AccountException("Email already in use");
            }
        }

        if (newUser.getPassword() == null || newUser.getPassword().isEmpty()) {
            throw new AccountException("Password cannot be empty");
        }

        // ToDo: add validations later like DOB, password checks, etc
        newUser.setLastUpdated(LocalDateTime.now());
        newUser.setCreatedOn(currUser.getCreatedOn());
        accountDAO.save(newUser);
    }
}
