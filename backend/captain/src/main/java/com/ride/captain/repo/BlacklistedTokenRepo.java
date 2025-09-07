package com.ride.captain.repo;

import com.ride.captain.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedTokenRepo extends JpaRepository<BlacklistedToken, String> {

    boolean existsByToken(String token);

}
