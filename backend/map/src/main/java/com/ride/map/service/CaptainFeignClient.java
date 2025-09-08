package com.ride.map.service;


import com.ride.map.dto.CaptainResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "captain-service", url = "http://localhost:8081")
public interface CaptainFeignClient {

    @GetMapping("/captains")
    List<CaptainResponseDTO> getAllCaptains();

    @GetMapping("/captains/{id}")
    CaptainResponseDTO getCaptainById(@PathVariable Long id);
}
