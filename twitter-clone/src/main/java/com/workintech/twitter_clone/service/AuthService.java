package com.workintech.twitter_clone.service;
import com.workintech.twitter_clone.dto.request.LoginRequest;
import com.workintech.twitter_clone.dto.request.UserRequestDto;
import com.workintech.twitter_clone.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse register(UserRequestDto request);
    AuthResponse login(LoginRequest request);
}
