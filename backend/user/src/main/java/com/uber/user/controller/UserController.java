package com.uber.user.controller;

import com.uber.user.dto.*;
import com.uber.user.entity.BlacklistedToken;
import com.uber.user.entity.User;
import com.uber.user.repo.BlacklistedTokenRepo;
import com.uber.user.repo.UserRepo;
import com.uber.user.service.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Tag(
        name = "Authentication API",
        description = "Endpoints for user registration, login, profile and logout"
)
public class UserController {

    @Autowired
    private IAuthService iAuthService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BlacklistedTokenRepo blacklistedTokenRepo;

    @Operation(
            summary = "Register new user",
            description = "Creates a new user account and returns JWT token"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User registered successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad Request (Validation error or User already registered)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Resource not found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    })
    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register(@Valid @RequestBody UserDTO dto) {
        String token = iAuthService.register(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto("User registered successfully", token, dto));
    }


    @Operation(
            summary = "Login user",
            description = "Authenticate user with email and password, returns JWT token"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login successful, JWT token returned"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid email or password"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginDto dto) {
        String token = iAuthService.login(dto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new LoginResponseDto(token, dto));
    }


    @Operation(
            summary = "Get user profile",
            description = "Fetch authenticated user profile using JWT token"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Profile fetched successfully"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized - Invalid or missing JWT token"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
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

            return ResponseEntity.ok(profile);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @Operation(
            summary = "Logout user",
            description = "Invalidate JWT token by adding it to blacklist"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User logged out successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Bad request - No token provided"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error"
            )
    })
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
