package com.example.usersservice.controllers;

import com.example.usersservice.repositories.CustomUserRepository;
import com.example.usersservice.services.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
public class CustomUserController {
    private final CustomUserService customUserService;

    @GetMapping("/getBalance")
   public ResponseEntity<Double> getBalance(@RequestParam String userId) {
        return this.customUserService.getBalance(userId);
    }

}
