package com.app.core.controllers;

import com.app.core.exceptions.AccountException;
import com.app.core.models.Following;
import com.app.core.services.FollowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/follow")
public class FollowController {

    FollowService followService;

    // POST
    @PostMapping("/follow")
    public ResponseEntity<Object> followAccount(@RequestBody Following following) {
        try {
            followService.followAccount(following);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException | AccountException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping()
    public ResponseEntity<String> unfollowAccount(@RequestBody Following unfollow) {
        try {
            followService.unfollowAccount(unfollow);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (AccountException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
