package com.example.todoapp.Services;

import com.example.todoapp.Entity.User;
import com.example.todoapp.Models.UserInput;

public interface UserService {

    public User registerUser(UserInput userInput);

    public String loginUser(String username, String password);
}
