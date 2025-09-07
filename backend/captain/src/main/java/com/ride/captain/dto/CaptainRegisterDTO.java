package com.ride.captain.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;


@Builder
public class CaptainRegisterDTO {

    @NotNull(message = "Full name is required")
    @Valid
    private FullNameDTO fullName;

    @Email(message = "Please enter a valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotNull(message = "Vehicle details are required")
    @Valid
    private VehicleDTO vehicle;

    public FullNameDTO getFullName() {
        return fullName;
    }

    public void setFullName(FullNameDTO fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }
}
