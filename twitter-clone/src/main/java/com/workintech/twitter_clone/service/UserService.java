package com.workintech.twitter_clone.service;


import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser(User user){

        if (userRepository.findByUsername(user.getUserName()).isPresent()){
            throw new RuntimeException("Bu kullanıcı adı zaten alınmış!");
        }
        return userRepository.save(user);
    }

    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("Kullanıcı bulunamadı!"));
    }
}
