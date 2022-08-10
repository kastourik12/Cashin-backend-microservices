package com.example.usersservice.services;

import com.example.usersservice.models.CustomUser;
import com.example.usersservice.repositories.CustomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserService {
    private final CustomUserRepository customUserRepository;

    public void updateCredit(Long id, Double balance) {
        CustomUser user = customUserRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if(user.getCredit() == null) {
            user.setCredit(balance);
            }
            else {
            user.setCredit(user.getCredit() + balance);
            }
        customUserRepository.save(user);
    }

    public ResponseEntity<Double> getBalance(String userId) {
        CustomUser user = customUserRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return ResponseEntity.ok(user.getCredit());
    }
}
