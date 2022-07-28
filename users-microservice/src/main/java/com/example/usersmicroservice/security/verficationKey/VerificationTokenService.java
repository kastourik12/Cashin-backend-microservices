package com.example.usersmicroservice.security.verficationKey;

import com.example.usersmicroservice.models.CustomUser;
import com.example.usersmicroservice.repositories.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    public String generateVerificationToken(String username) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setUsername(username);
        verificationTokenRepository.save(verificationToken);
        return verificationToken.getToken();
    }
}
