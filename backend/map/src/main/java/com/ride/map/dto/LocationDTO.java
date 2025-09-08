package com.ride.map.dto;

import jakarta.validation.constraints.NotNull;


public class LocationDTO {

    @NotNull(message = "Latitude is required")
    private Double ltd;

    @NotNull(message = "Longitude is required")
    private Double lng;

    public LocationDTO(Double ltd, Double lng) {
        this.ltd = ltd;
        this.lng = lng;
    }
    public LocationDTO(){

    }

    public Double getLtd() {
        return ltd;
    }

    public void setLtd(Double ltd) {
        this.ltd = ltd;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
