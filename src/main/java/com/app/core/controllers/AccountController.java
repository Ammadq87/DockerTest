package com.app.core.controllers;

import com.app.core.exceptions.AccountException;
import com.app.core.models.User;
import com.app.core.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

@Repository
@RequestMapping("/api/v1/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {this.accountService = accountService;}

    // GET

    /**
     * Get account based on user ID
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccountInformation(@PathVariable String id) {
        try {
            var user = accountService.getAccountInformation(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (AccountException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Search for accounts via username
     * @param param
     * @return
     */
    @GetMapping("/search/{param}")
    public ResponseEntity<Object> getAccountsBySearchParam(@PathVariable String param) {
        try {
            var results = accountService.getAccountsBySearchParam(param);
            return new ResponseEntity<>(results, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT

    /**
     * Updates the logged-in user's account
     * @param user
     * @return
     */
    @PutMapping()
    public ResponseEntity<String> updateAccountInformation(@RequestBody User user) {
        try {
            accountService.updateAccountInformation(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AccountException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
