package com.ride.map.dto;

import com.ride.map.enums.VehicleType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class VehicleDTO {

    @NotBlank(message = "Color is required")
    @Size(min = 3, message = "Color must be at least 3 characters long")
    private String color;

    @NotBlank(message = "Plate is required")
    @Size(min = 3, message = "Plate must be at least 3 characters long")
    private String plate;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @NotNull(message = "Vehicle type is required")
    private VehicleType vehicleType;

    public VehicleDTO(String color, String plate, int capacity, VehicleType vehicleType) {
        this.color = color;
        this.plate = plate;
        this.capacity = capacity;
        this.vehicleType = vehicleType;
    }
    public VehicleDTO(){

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }


}
