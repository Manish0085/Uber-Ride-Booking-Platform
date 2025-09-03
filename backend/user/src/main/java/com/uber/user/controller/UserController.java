package com.uber.user.controller;


import com.uber.user.dto.LoginDto;
import com.uber.user.dto.LoginResponseDto;
import com.uber.user.dto.ResponseDto;
import com.uber.user.dto.UserDTO;
import com.uber.user.service.IAuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class UserController {


    @Autowired
    private IAuthService iAuthService;

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


}
