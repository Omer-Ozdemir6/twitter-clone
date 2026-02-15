package com.workintech.twitter_clone.service;


import com.workintech.twitter_clone.dto.request.UserRequestDto;
import com.workintech.twitter_clone.dto.response.UserResponseDto;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.repository.UserRepository;
import com.workintech.twitter_clone.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> getAll() {
       return userRepository.findAll()
               .stream()
               .map(userMapper::toResponseDto)
               .toList();
    }

    @Override
    public UserResponseDto findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()){
            return userMapper.toResponseDto(optionalUser.get());
        }
        throw new RuntimeException("Kullanıcı bulunamadı, id: " + id);
    }

    @Override
    public UserResponseDto create(UserRequestDto userRequestDto) {
        User user = userMapper.toEntity(userRequestDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);
        return userMapper.toResponseDto(savedUser);
    }

    @Override
    public UserResponseDto update(Long id, UserRequestDto userRequestDto) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Güncellenecek kullanıcı bulunamadı, id: " + id));
        existingUser.setUsername(userRequestDto.username());
        existingUser.setEmail(userRequestDto.email());

        if(userRequestDto.password() != null) {
            existingUser.setPassword(passwordEncoder.encode(userRequestDto.password()));
        }

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDto(updatedUser);
    }

    @Override
    public void deleteById(Long id) {
    userRepository.deleteById(id);
    }

    @Override
    public UserResponseDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı, username: " + username));
        return  userMapper.toResponseDto(user);
    }
}
