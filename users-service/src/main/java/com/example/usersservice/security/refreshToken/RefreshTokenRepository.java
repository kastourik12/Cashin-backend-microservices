package com.example.usersservice.security.refreshToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUsername(String username);
    void deleteByToken(String token);
    Optional<RefreshToken> findByToken(String token);

    @Modifying
    void deleteByUsername(String username);
}

