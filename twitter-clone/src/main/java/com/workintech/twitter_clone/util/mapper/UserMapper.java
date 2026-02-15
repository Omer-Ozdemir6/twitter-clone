package com.workintech.twitter_clone.util.mapper;

import com.workintech.twitter_clone.dto.request.UserRequestDto;
import com.workintech.twitter_clone.dto.response.UserResponseDto;
import com.workintech.twitter_clone.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponseDto toResponseDto(User user){
        return new UserResponseDto(user.getId(), user.getUsername(), user.getEmail());
    }

    public User toEntity(UserRequestDto userRequestDto){
        User user = new User();
        user.setUsername(userRequestDto.username());
        user.setEmail(userRequestDto.email());
        user.setPassword(userRequestDto.password());
        return user;
    }

}
