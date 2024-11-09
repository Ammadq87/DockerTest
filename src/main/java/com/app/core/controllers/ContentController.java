package com.app.core.controllers;

import com.app.core.models.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/content")
public class ContentController {

    // POST
    @PostMapping()
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        try {

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // PUT
    @PutMapping("/like/{postId}")
    public ResponseEntity<String> likePost(@PathVariable String postId) {}



}
