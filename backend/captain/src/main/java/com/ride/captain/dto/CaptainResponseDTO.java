package com.ride.captain.dto;

import lombok.*;


public class CaptainResponseDTO {

    private String id;
    private FullNameDTO fullName;
    private String email;

    private String token;
    private String status;

    private VehicleDTO vehicle;
    private LocationDTO location;

    public CaptainResponseDTO(String id, FullNameDTO fullName, String email, String token, String status, VehicleDTO vehicle, LocationDTO location) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.token = token;
        this.status = status;
        this.vehicle = vehicle;
        this.location = location;
    }

    public CaptainResponseDTO(String id, FullNameDTO fullName, String email, String status, VehicleDTO vehicle, LocationDTO location) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.status = status;
        this.vehicle = vehicle;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }
}
