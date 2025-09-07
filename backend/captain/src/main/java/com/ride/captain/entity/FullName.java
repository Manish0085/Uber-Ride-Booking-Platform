package com.ride.captain.entity;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.*;

@Embeddable
public class FullName {

    @NotBlank(message = "Firstname is required")
    @Size(min = 3, message = "Firstname must be at least 3 characters long")
    private String firstname;

    @Size(min = 3, message = "Lastname must be at least 3 characters long")
    private String lastname;

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
