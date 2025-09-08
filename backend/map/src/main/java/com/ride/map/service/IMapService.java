package com.ride.map.service;

import java.util.List;
import java.util.Map;

public interface IMapService {

    Map<String, Double> getAddressCoordinate(String address);
    Object getDistanceTime(String origin, String destination);
    List<String> getAutoCompleteSuggestions(String input);
    Object getCaptainsInTheRadius(double lat, double lng, double radiusKm);
}

