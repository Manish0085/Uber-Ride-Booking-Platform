package com.ride.captain.entity;


import com.ride.captain.enums.Status;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "captains", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class Captain {
    @Id
    private String id;

    @Embedded
    private FullName fullName;

    private String email;
    private String password;
//    private String socketId;

    @Enumerated(EnumType.STRING)
    private Status status = Status.INACTIVE;

    @Embedded
    private Vehicle vehicle;

    @Embedded
    private Location location;

    public Captain() {
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FullName getFullName() {
        return fullName;
    }

    public void setFullName(FullName fullName) {
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
