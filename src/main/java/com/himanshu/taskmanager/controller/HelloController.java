package com.himanshu.taskmanager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello, Himanshu";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to my task manager.";
    }
}
