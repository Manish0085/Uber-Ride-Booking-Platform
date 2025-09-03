package com.uber.user.dto;

public class LoginResponseDto {

    private String token;
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
