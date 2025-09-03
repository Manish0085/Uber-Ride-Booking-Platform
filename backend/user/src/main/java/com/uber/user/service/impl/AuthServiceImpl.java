package com.uber.user.service.impl;


import com.uber.user.dto.LoginDto;
import com.uber.user.dto.UserDTO;
import com.uber.user.entity.User;
import com.uber.user.exception.ResourceNotFoundException;
import com.uber.user.exception.UserAlreadyRegisteredException;
import com.uber.user.repo.UserRepo;
import com.uber.user.service.IAuthService;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.uber.user.util.JwtUtil;

import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {


    @Autowired
    private UserRepo userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(UserDTO dto) {
        Optional<User> existing = userRepository.findByEmail(dto.getEmail());
        if (existing.isPresent()) {
            throw new UserAlreadyRegisteredException("User with email "+dto.getEmail()+" already registered.");
        }

        User user = new User(dto.getName(), dto.getEmail(), passwordEncoder.encode(dto.getPassword()));
        userRepository.save(user);
        return jwtUtil.generateToken(user.getId());
    }

    @Override
    public String login(LoginDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", dto.getEmail()));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(user.getId());
    }


}
