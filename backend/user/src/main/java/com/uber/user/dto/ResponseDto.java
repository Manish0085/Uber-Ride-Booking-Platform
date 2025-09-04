package com.uber.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        description = "Response after user registration or any auth operation"
)
public class ResponseDto {

    @Schema(
            description = "Response message",
            example = "User registered successfully"
    )
    private String message;

    @Schema(
            description = "JWT token issued for the user",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    private String token;

    @Schema(
            description = "Details of the registered user"
    )
    private UserDTO user;

    public ResponseDto() {}

    public ResponseDto(String message, String token, UserDTO user) {
        this.message = message;
        this.token = token;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public UserDTO getUser() {
        return user;
    }
    public void setUser(UserDTO user) {
        this.user = user;
    }
}
