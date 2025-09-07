package com.ride.captain.controller;

import com.ride.captain.dto.*;
import com.ride.captain.entity.BlacklistedToken;
import com.ride.captain.entity.Captain;
import com.ride.captain.exception.CaptainNotFoundException;
import com.ride.captain.mapper.CaptainMapper;
import com.ride.captain.repo.BlacklistedTokenRepo;
import com.ride.captain.repo.CaptainRepo;
import com.ride.captain.service.impl.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;



@RestController
@RequestMapping(value = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class CaptainController {


    private final CaptainRepo captainRepo;
    private final IAuthService iAuthService;
    private final BlacklistedTokenRepo blacklistedTokenRepo;

    public CaptainController(CaptainRepo captainRepo,
                             IAuthService iAuthService,
                             BlacklistedTokenRepo blacklistedTokenRepo) {
        this.captainRepo = captainRepo;
        this.iAuthService = iAuthService;
        this.blacklistedTokenRepo = blacklistedTokenRepo;
    }


    @PostMapping("/register")
    public ResponseEntity<CaptainResponseDTO> register(@Valid @RequestBody CaptainRegisterDTO dto) {
        // Register captain and get token
        String token = iAuthService.register(dto); // This should also save the captain

        // Retrieve the saved captain from DB (optional if iAuthService returns it)
        Captain savedCaptain = captainRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CaptainNotFoundException("Captain", "email", dto.getEmail()));

        // Map embedded objects to DTOs
        FullNameDTO fullNameDTO = new FullNameDTO(
                savedCaptain.getFullName().getFirstname(),
                savedCaptain.getFullName().getLastname()
        );

        VehicleDTO vehicleDTO = null;
        if (savedCaptain.getVehicle() != null) {
            vehicleDTO = new VehicleDTO(
                    savedCaptain.getVehicle().getColor(),
                    savedCaptain.getVehicle().getPlate(),
                    savedCaptain.getVehicle().getCapacity(),
                    savedCaptain.getVehicle().getVehicleType()
            );
        }

        LocationDTO locationDTO = null;
        if (savedCaptain.getLocation() != null) {
            locationDTO = new LocationDTO(
                    savedCaptain.getLocation().getLtd(),
                    savedCaptain.getLocation().getLng()
            );
        }

        // Build response DTO
        CaptainResponseDTO responseDTO = new CaptainResponseDTO(
                savedCaptain.getId(),
                fullNameDTO,
                savedCaptain.getEmail(),
                token,
                savedCaptain.getStatus().name(),
                vehicleDTO,
                locationDTO
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }



    @PostMapping("/login")
    public ResponseEntity<CaptainResponseDTO> login(@Valid @RequestBody LoginDto dto) {
        String token = iAuthService.login(dto);

        Captain savedCaptain = captainRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CaptainNotFoundException("Captain", "email", dto.getEmail()));

        // Map embedded objects to DTOs
        FullNameDTO fullNameDTO = new FullNameDTO(
                savedCaptain.getFullName().getFirstname(),
                savedCaptain.getFullName().getLastname()
        );

        VehicleDTO vehicleDTO = null;
        if (savedCaptain.getVehicle() != null) {
            vehicleDTO = new VehicleDTO(
                    savedCaptain.getVehicle().getColor(),
                    savedCaptain.getVehicle().getPlate(),
                    savedCaptain.getVehicle().getCapacity(),
                    savedCaptain.getVehicle().getVehicleType()
            );
        }

        LocationDTO locationDTO = null;
        if (savedCaptain.getLocation() != null) {
            locationDTO = new LocationDTO(
                    savedCaptain.getLocation().getLtd(),
                    savedCaptain.getLocation().getLng()
            );
        }

        // Build response DTO
        CaptainResponseDTO responseDTO = new CaptainResponseDTO(
                savedCaptain.getId(),
                fullNameDTO,
                savedCaptain.getEmail(),
                token,
                savedCaptain.getStatus().name(),
                vehicleDTO,
                locationDTO
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDTO);
    }



    @PostMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        try {
            Optional<Captain> optionalCaptain = captainRepo.findById(userDetails.getUsername());

            Captain authCaptain = optionalCaptain.orElseThrow(() ->
                    new CaptainNotFoundException("Captain", "email", userDetails.getUsername()));

            FullNameDTO fullNameDTO = new FullNameDTO(
                    authCaptain.getFullName().getFirstname(),
                    authCaptain.getFullName().getLastname()
            );

            VehicleDTO vehicleDTO = null;
            if (authCaptain.getVehicle() != null) {
                vehicleDTO = new VehicleDTO(
                        authCaptain.getVehicle().getColor(),
                        authCaptain.getVehicle().getPlate(),
                        authCaptain.getVehicle().getCapacity(),
                        authCaptain.getVehicle().getVehicleType()
                );
            }

            LocationDTO locationDTO = null;
            if (authCaptain.getLocation() != null) {
                locationDTO = new LocationDTO(
                        authCaptain.getLocation().getLtd(),
                        authCaptain.getLocation().getLng()
                );
            }

            // Build response DTO
            CaptainResponseDTO responseDTO = new CaptainResponseDTO(
                    authCaptain.getId(),
                    fullNameDTO,
                    authCaptain.getEmail(),
                    authCaptain.getStatus().name(),
                    vehicleDTO,
                    locationDTO
            );


            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            BlacklistedToken blacklisted = new BlacklistedToken();
            blacklisted.setToken(token);
            blacklisted.setBlacklistedAt(LocalDateTime.now());
            blacklistedTokenRepo.save(blacklisted);

            return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
        }
        return ResponseEntity.badRequest().body(Map.of("message", "No token provided"));
    }

}
