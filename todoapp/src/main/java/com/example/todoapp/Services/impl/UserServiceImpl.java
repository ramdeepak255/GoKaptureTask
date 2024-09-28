package com.example.todoapp.Services.impl;

import com.example.todoapp.Entity.User;
import com.example.todoapp.Models.UserInput;
import com.example.todoapp.Repository.UserRepository;
import com.example.todoapp.Services.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public User registerUser(UserInput userInput) {
        User user = new User();
        user.setUserName(userInput.getUserName());
        user.setPassword(passwordEncoder.encode(userInput.getPassword()));
        user.setEmailId(userInput.getEmail());
        user.setPhoneNum(userInput.getPhoneNum());
        return userRepository.save(user);
    }

    // Authenticate user and generate JWT
    public String loginUser(String username, String password) {
        User user = userRepository.findByUserName(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return generateToken(user);
        }
        throw new RuntimeException("Invalid username or password");
    }

    // Generate JWT token
    private String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUserName())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day expiration
                .signWith(SignatureAlgorithm.HS512, getKey()) // Use a strong secret key
                .compact();
    }

    private String getKey(){
        try{
            KeyGenerator key=KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk=key.generateKey();
            return Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

}
