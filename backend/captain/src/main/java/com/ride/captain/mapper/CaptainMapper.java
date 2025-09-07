package com.ride.captain.mapper;

import com.ride.captain.dto.*;
import com.ride.captain.entity.*;

public class CaptainMapper {

    // DTO -> Entity
    public Captain mapToCaptain(CaptainRegisterDTO dto, Captain captain) {
        if (dto == null) return captain;

        FullName fullName = new FullName();
        fullName.setFirstname(dto.getFullName().getFirstname());
        fullName.setLastname(dto.getFullName().getLastname());

        Vehicle vehicle = new Vehicle();
        vehicle.setColor(dto.getVehicle().getColor());
        vehicle.setPlate(dto.getVehicle().getPlate());
        vehicle.setCapacity(dto.getVehicle().getCapacity());
        vehicle.setVehicleType(dto.getVehicle().getVehicleType());

        captain.setFullName(fullName);
        captain.setEmail(dto.getEmail());
        captain.setPassword(dto.getPassword()); // remember to encode in service
        captain.setVehicle(vehicle);

        return captain;
    }

    // Entity -> ResponseDTO
    public CaptainResponseDTO mapToCaptainDTO(Captain captain, CaptainResponseDTO dto) {
        if (captain == null) return dto;

        FullNameDTO fullNameDTO = new FullNameDTO();
        fullNameDTO.setFirstname(captain.getFullName().getFirstname());
        fullNameDTO.setLastname(captain.getFullName().getLastname());

        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setColor(captain.getVehicle().getColor());
        vehicleDTO.setPlate(captain.getVehicle().getPlate());
        vehicleDTO.setCapacity(captain.getVehicle().getCapacity());
        vehicleDTO.setVehicleType(captain.getVehicle().getVehicleType());

        LocationDTO locationDTO = null;
        if (captain.getLocation() != null) {
            locationDTO = new LocationDTO();
            locationDTO.setLtd(captain.getLocation().getLtd());
            locationDTO.setLng(captain.getLocation().getLng());
        }

        dto.setId(captain.getId());
        dto.setFullName(fullNameDTO);
        dto.setEmail(captain.getEmail());
//        dto.setSocketId(captain.getSocketId());
        dto.setStatus(captain.getStatus().name());
        dto.setVehicle(vehicleDTO);
        dto.setLocation(locationDTO);

        return dto;
    }
}
