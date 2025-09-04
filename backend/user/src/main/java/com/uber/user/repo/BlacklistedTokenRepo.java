package com.uber.user.repo;

import com.uber.user.entity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistedTokenRepo extends JpaRepository<BlacklistedToken, String> {

    boolean existsByToken(String token);

}
