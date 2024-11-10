package com.app.core.controllers;

import com.app.core.models.follow.FollowPairID;
import com.app.core.utils.JwtUtil;
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
    @GetMapping("/following/{username}")
    public ResponseEntity<List<FollowerInfo>> getFollowing(@PathVariable String username) {
        try {
            var followers = followService.getFollowing(username);
            return new ResponseEntity<>(followers, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/followers/{username}")
    public ResponseEntity<List<FollowerInfo>> getFollowers(@PathVariable String username) {
        try {
            var following = followService.getFollowers(username);
            return new ResponseEntity<>(following, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // POST
    @PostMapping("/{userB}")
    public ResponseEntity<Object> followAccount(HttpServletRequest request, @PathVariable String userB) {
        try {
            String userA = (String) JwtUtil.getAttributeFromToken("username", request);
            FollowPairID followPairID = FollowPairID.builder().userB(userB).userA(userA).build();
            FollowPair followPair = FollowPair.builder().followPairID(followPairID).build();
            followService.followAccount(followPair);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException | AccountException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
