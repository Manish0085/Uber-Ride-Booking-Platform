package com.uber.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User profile information")
public class ProfileDto {

    @Schema(
            description = "Unique ID of the user",
            example = "manish123"
    )
    private String userId;

    @Schema(
            description = "Full name of the user",
            example = "Stiphen Marrek"
    )
    private String name;

    @Schema(
            description = "Email of the user",
            example = "marrekstiphen23@gmaill.com"
    )
    private String email;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
