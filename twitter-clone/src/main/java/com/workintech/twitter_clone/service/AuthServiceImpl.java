package com.workintech.twitter_clone.service;

import com.workintech.twitter_clone.dto.request.LoginRequest;
import com.workintech.twitter_clone.dto.request.UserRequestDto;
import com.workintech.twitter_clone.dto.response.AuthResponse;
import com.workintech.twitter_clone.entity.Role;
import com.workintech.twitter_clone.entity.User;
import com.workintech.twitter_clone.repository.RoleRepository;
import com.workintech.twitter_clone.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;



@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(UserRequestDto request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        Role userRole = roleRepository.findByAuthority("USER")
                .orElseThrow(() -> new RuntimeException("USER rolü bulunamadı"));
        user.setAuthorities(Set.of(userRole));

        User savedUser = userRepository.save(user);
        return new AuthResponse(savedUser.getUsername(), "Kullanıcı başarıyla kaydedildi");
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        return new AuthResponse(request.username(), "Giriş Başarılı");
    }
}
