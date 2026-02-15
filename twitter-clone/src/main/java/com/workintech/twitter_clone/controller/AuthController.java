package com.workintech.twitter_clone.controller;

import com.workintech.twitter_clone.dto.request.LoginRequest;
import com.workintech.twitter_clone.dto.request.UserRequestDto;
import com.workintech.twitter_clone.dto.response.AuthResponse;
import com.workintech.twitter_clone.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody UserRequestDto request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}