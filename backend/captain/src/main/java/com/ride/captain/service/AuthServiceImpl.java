package com.ride.captain.service;

import com.ride.captain.dto.CaptainRegisterDTO;
import com.ride.captain.dto.LoginDto;
import com.ride.captain.entity.Captain;
import com.ride.captain.exception.CaptainAlreadyRegisteredException;
import com.ride.captain.exception.CaptainNotFoundException;
import com.ride.captain.filter.JwtFilter;
import com.ride.captain.mapper.CaptainMapper;
import com.ride.captain.repo.CaptainRepo;
import com.ride.captain.service.impl.IAuthService;
import com.ride.captain.util.JwtUtil;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {


    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CaptainRepo captainRepo;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(CaptainRegisterDTO dto) {
        Optional<Captain> existing = captainRepo.findByEmail(dto.getEmail());
        if (existing.isPresent()) {
            throw new CaptainAlreadyRegisteredException(
                    "User with email " + dto.getEmail() + " already registered."
            );
        }

        Captain captain = new Captain();   // empty entity
        CaptainMapper mapper = new CaptainMapper();
        captain = mapper.mapToCaptain(dto, captain);

        // encode password before saving
        captain.setPassword(passwordEncoder.encode(captain.getPassword()));

        captainRepo.save(captain);

        return jwtUtil.generateToken(captain.getId());
    }

    @Override
    public String login(LoginDto dto) {
        Captain user = captainRepo.findByEmail(dto.getEmail())
                .orElseThrow(() -> new CaptainNotFoundException("User", "email", dto.getEmail()));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(user.getId());
    }

}
