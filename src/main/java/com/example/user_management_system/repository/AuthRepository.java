package com.example.user_management_system.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user_management_system.entity.Auth;

public interface AuthRepository extends JpaRepository<Auth, UUID> {

    Optional<Auth> findByEmail(String email);

    Optional<Auth> findByPhone(String phone);
}
