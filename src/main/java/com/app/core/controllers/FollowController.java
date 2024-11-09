package com.app.core.controllers;

import com.app.core.config.JwtUtil;
import com.app.core.exceptions.AccountException;
import com.app.core.models.follow.FollowPair;
import com.app.core.models.follow.FollowerInfo;
import com.app.core.services.FollowService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/follow")
public class FollowController {

    private final FollowService followService;

    @Autowired
    public FollowController(FollowService followService) {
        this.followService = followService;
    }

    // GET
    @GetMapping("/following/{userId}")
    public ResponseEntity<List<FollowerInfo>> getFollowing(@PathVariable String userId) {
        try {
            var followers = followService.getFollowers(userId);
            return new ResponseEntity<>(followers, HttpStatus.OK);
        } catch (AccountException e){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<FollowerInfo>> getFollowers(@PathVariable String userId) {
        try {
            var following = followService.getFollowing(userId);
            return new ResponseEntity<>(following, HttpStatus.OK);
        } catch (RuntimeException | AccountException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST
    @PostMapping("/follow")
    public ResponseEntity<Object> followAccount(@RequestBody FollowPair followPair) {
        try {
            followService.followAccount(followPair);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException | AccountException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // DELETE
    @DeleteMapping("/{userB}")
    public ResponseEntity<String> unfollowAccount(HttpServletRequest request, @PathVariable String userB) {
        try {
            var user = JwtUtil.parseJwt(request);

            if (user == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }

            String userId = (String) user.get("userId");

            if (userId == null) {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            } else if (userId.equals(userB)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            followService.unfollowAccount(userId, userB);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
