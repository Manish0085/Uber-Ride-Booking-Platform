package com.ride.captain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


public class FullNameDTO {

    @NotBlank(message = "Firstname is required")
    @Size(min = 3, message = "Firstname must be at least 3 characters long")
    private String firstname;

    @Size(min = 3, message = "Lastname must be at least 3 characters long")
    private String lastname;

    public FullNameDTO(){

    }

    public FullNameDTO(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
