package com.example.usersmicroservice.repositories;

import com.example.usersmicroservice.models.ERole;
import com.example.usersmicroservice.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository< Role , Long> {
    Optional<Role> findByName(ERole roleAdmin);
}
