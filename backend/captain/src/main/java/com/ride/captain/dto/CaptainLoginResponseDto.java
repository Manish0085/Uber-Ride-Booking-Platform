package com.ride.captain.dto;

import lombok.*;


public class CaptainLoginResponseDto {

    private String token;  // JWT token

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CaptainResponseDTO getCaptain() {
        return captain;
    }

    public void setCaptain(CaptainResponseDTO captain) {
        this.captain = captain;
    }

    private CaptainResponseDTO captain;  // captain details to return
}
