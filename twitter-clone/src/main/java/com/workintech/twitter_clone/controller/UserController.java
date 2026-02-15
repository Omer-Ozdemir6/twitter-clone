package com.workintech.twitter_clone.controller;



import com.workintech.twitter_clone.dto.request.UserRequestDto;
import com.workintech.twitter_clone.dto.response.UserResponseDto;
import com.workintech.twitter_clone.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserResponseDto> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(@PathVariable("id") Long id){
        return userService.findById(id);
    }


    @PutMapping("/{id}")

    public UserResponseDto update(@PathVariable("id") Long id, @RequestBody UserRequestDto userRequestDto){
        return userService.update(id,userRequestDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") Long id){
        userService.deleteById(id);
    }

}
