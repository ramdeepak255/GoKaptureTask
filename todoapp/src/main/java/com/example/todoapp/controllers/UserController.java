package com.example.todoapp.controllers;

import com.example.todoapp.Entity.User;
import com.example.todoapp.Models.UserInput;
import com.example.todoapp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserInput userInput) {
        userService.registerUser(userInput);
        return ResponseEntity.ok("User registered successfully");
    }

    // User Login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam(name = "userName") String userName,@RequestParam(name = "password") String password) {
        String token = userService.loginUser(userName, password);
        return ResponseEntity.ok(token);
    }

}
