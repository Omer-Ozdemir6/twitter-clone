package com.workintech.twitter_clone.service;


import com.workintech.twitter_clone.dto.request.UserRequestDto;
import com.workintech.twitter_clone.dto.response.UserResponseDto;


import java.util.List;



public interface UserService {

    List<UserResponseDto> getAll();
    UserResponseDto findById(Long id);
    UserResponseDto create(UserRequestDto userRequestDto);
    UserResponseDto update(Long id, UserRequestDto userRequestDto);
    void deleteById(Long id);

    UserResponseDto findByUsername(String username);

}
