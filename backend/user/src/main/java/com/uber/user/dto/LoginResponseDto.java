package com.uber.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for login response")
public class LoginResponseDto {

    @Schema(
            description = "JWT token generated after successful login",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    private String token;

    @Schema(
            description = "Login request details"
    )
    private LoginDto loginDto;

    public LoginResponseDto(String token, LoginDto loginDto) {
        this.token = token;
        this.loginDto = loginDto;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginDto getLoginDto() {
        return loginDto;
    }

    public void setLoginDto(LoginDto loginDto) {
        this.loginDto = loginDto;
    }
}
