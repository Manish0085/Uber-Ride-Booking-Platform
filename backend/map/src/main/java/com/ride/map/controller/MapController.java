package com.ride.map.controller;

import com.ride.map.service.IMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maps")
public class MapController {

    private final IMapService iMapService;

    @Autowired
    public MapController(IMapService iMapService) {
        this.iMapService = iMapService;
    }

    // ✅ Get coordinates by address
    @GetMapping("/coordinates")
    public ResponseEntity<?> getCoordinates(@RequestParam String address) {
        try {
            Object coordinates = iMapService.getAddressCoordinate(address);
            return ResponseEntity.ok(coordinates);
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Coordinates not found");
        }
    }

    // ✅ Get distance & time between two points
    @GetMapping("/distance-time")
    public ResponseEntity<?> getDistanceTime(
            @RequestParam String origin,
            @RequestParam String destination) {
        try {
            Object distanceTime = iMapService.getDistanceTime(origin, destination);
            return ResponseEntity.ok(distanceTime);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }

    // ✅ Autocomplete suggestions
    @GetMapping("/autocomplete")
    public ResponseEntity<?> getAutoCompleteSuggestions(@RequestParam String input) {
        try {
            Object suggestions = iMapService.getAutoCompleteSuggestions(input);
            return ResponseEntity.ok(suggestions);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Internal server error");
        }
    }
}
