package com.ride.map.service.impl;

import com.ride.map.dto.CaptainResponseDTO;
import com.ride.map.service.CaptainFeignClient;
import com.ride.map.service.IMapService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapServiceImpl implements IMapService {

    @Value("${google.maps.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final CaptainFeignClient captainFeignClient;

    public MapServiceImpl(RestTemplate restTemplate, CaptainFeignClient client) {
        this.restTemplate = restTemplate;
        this.captainFeignClient = client;
    }

    @Override
    public Map<String, Double> getAddressCoordinate(String address) {
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="
                + UriUtils.encode(address, StandardCharsets.UTF_8)
                + "&key=" + apiKey;

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        if (response != null && "OK".equals(response.get("status"))) {
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
            if (!results.isEmpty()) {
                Map<String, Object> geometry = (Map<String, Object>) ((Map<String, Object>) results.get(0).get("geometry")).get("location");
                Double lat = ((Number) geometry.get("lat")).doubleValue();
                Double lng = ((Number) geometry.get("lng")).doubleValue();

                Map<String, Double> location = new HashMap<>();
                location.put("lat", lat);
                location.put("lng", lng);

                return location;
            }
        }
        throw new RuntimeException("Unable to fetch coordinates from Google Maps API");
    }

    @Override
    public Object getDistanceTime(String origin, String destination) {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="
                + origin + "&destinations=" + destination + "&key=" + apiKey;

        Map response = restTemplate.getForObject(url, Map.class);
        if (response != null && "OK".equals(response.get("status"))) {
            return ((List<Map<String, Object>>)((Map<String, Object>)((List) response.get("rows")).get(0))
                    .get("elements")).get(0);
        }
        throw new RuntimeException("Unable to fetch distance and time");
    }

    @Override
    public List<String> getAutoCompleteSuggestions(String input) {
        String url = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input="
                + input + "&key=" + apiKey;

        Map response = restTemplate.getForObject(url, Map.class);
        if (response != null && "OK".equals(response.get("status"))) {
            List<Map<String, Object>> predictions = (List<Map<String, Object>>) response.get("predictions");

            List<String> suggestions = new ArrayList<>();
            for (Map<String, Object> prediction : predictions) {
                suggestions.add((String) prediction.get("description"));
            }
            return suggestions;
        }
        throw new RuntimeException("Unable to fetch suggestions");
    }

    @Override
    public List<CaptainResponseDTO> getCaptainsInTheRadius(double lat, double lng, double radiusKm) {
        List<CaptainResponseDTO> allCaptains = captainFeignClient.getAllCaptains(); // Feign call

        // Filter captains within radius using Haversine formula
        return allCaptains.stream()
                .filter(c -> {
                    double distance = calculateDistance(lat, lng, c.getLocation().getLtd(), c.getLocation().getLng());
                    return distance <= radiusKm;
                })
                .collect(Collectors.toList());
    }



    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        double R = 6371; // Radius of the earth in km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
