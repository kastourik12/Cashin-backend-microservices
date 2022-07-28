package com.example.usersmicroservice.security.refreshToken;

import com.example.usersmicroservice.exception.CustomException;
import com.example.usersmicroservice.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;


@RequiredArgsConstructor
@Service
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;
    public String generateRefreshToken(Authentication authentication) {
     String  token = jwtUtils.generateRefreshToken(authentication);
     RefreshToken refreshToken = RefreshToken.builder().token(token).username(authentication.getName()).createdAt(Instant.now()).build();
     refreshTokenRepository.save(refreshToken);
     return  token;

    }



    public String getUsernameFromRefreshToken(String token) {
     if(refreshTokenRepository.findByToken(token).isPresent()) {
         return refreshTokenRepository.findByToken(token).get().getUsername();
     }
        return null;
    }

    public void removeAllTokensByuser(String username) {
        this.refreshTokenRepository.deleteByUsername(username);
    }
}
