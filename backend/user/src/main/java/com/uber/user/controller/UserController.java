package com.uber.user.controller;


import com.uber.user.dto.*;
import com.uber.user.entity.BlacklistedToken;
import com.uber.user.entity.User;
import com.uber.user.repo.BlacklistedTokenRepo;
import com.uber.user.repo.UserRepo;
import com.uber.user.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {


    @Autowired
    private IAuthService iAuthService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BlacklistedTokenRepo blacklistedTokenRepo;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody UserDTO dto) {
        String token = iAuthService.register(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto("User registered successfully", token, dto));
    }


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto dto) {
        String token = iAuthService.login(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginResponseDto(token, dto));
    }


    @PostMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Optional<User> optionalUser = userRepo.findById(userDetails.getUsername());

            User authUser = optionalUser.orElseThrow(() ->
                    new RuntimeException("User not found for: " + userDetails.getUsername()));

            ProfileDto profile = new ProfileDto();
            profile.setUserId(authUser.getId());
            profile.setName(authUser.getName());
            profile.setEmail(authUser.getEmail());

            System.out.println("Authenticated");
            return ResponseEntity.ok(profile);

        } catch (Exception e) {
            System.out.println("UnAuthenticated");
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }



    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            BlacklistedToken blacklisted = new BlacklistedToken();
            blacklisted.setToken(token);
            blacklisted.setBlacklistedAt(LocalDateTime.now());
            blacklistedTokenRepo.save(blacklisted);

            return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "No token provided"));
    }

}
