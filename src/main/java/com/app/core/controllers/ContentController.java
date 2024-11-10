package com.app.core.controllers;

import com.app.core.exceptions.AccountException;
import com.app.core.models.content.Content;
import com.app.core.services.ContentService;
import com.app.core.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService cs) { this.contentService = cs; }

    // POST
    @PostMapping
    public ResponseEntity<String> createContent(@RequestBody Content post) {
        try {
            contentService.createContent(post);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // GET
    @GetMapping("/{username}")
    public ResponseEntity<List<Content>> getContentByUsername(@PathVariable String username) {
        try {
            var content = contentService.getContentByUsername(username);
            return new ResponseEntity<>(content, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/feed")
    public ResponseEntity<List<Content>> getFeed(HttpServletRequest request) {
        try {
            String username = (String) JwtUtil.getAttributeFromToken("username", request);
            var feed = contentService.getFeedForUser(username);
            return new ResponseEntity<>(feed, HttpStatus.OK);
        } catch (AccountException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // PUT
    @PutMapping("/like/{postId}")
    public ResponseEntity<String> likeContent(HttpServletRequest request, @PathVariable String postId) {
        try {
            String username = (String) JwtUtil.getAttributeFromToken("username", request);
            contentService.likeContent(username, postId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException | AccountException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
