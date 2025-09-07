package com.ride.captain.repo;

import com.ride.captain.entity.Captain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.Optional;

@Repository
public interface CaptainRepo extends JpaRepository<Captain, String> {
    Optional<Captain> findByEmail(String email);
}
