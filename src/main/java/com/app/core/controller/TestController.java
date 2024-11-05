package com.app.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class TestController {

    @GetMapping
    public String test() {
        return "Hello World";
    }

    @GetMapping("/msg/{msg}")
    public String test(@PathVariable("msg") String msg) {
        return "Hello " + msg;
    }
}
