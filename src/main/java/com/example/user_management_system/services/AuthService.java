package com.example.user_management_system.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.user_management_system.dto.AuthDto;
import com.example.user_management_system.entity.Auth;
import com.example.user_management_system.exception.HttpException;
import com.example.user_management_system.mapper.AuthMapper;
import com.example.user_management_system.repository.AuthRepository;

@Service
public class AuthService {

    private final AuthRepository authRepository;
    private final BcryptService bcryptService;
    private final JwtTokenService jwtTokenService;

    public AuthService(AuthRepository authRepository, BcryptService bcryptService, JwtTokenService jwtTokenService) {
        this.authRepository = authRepository;
        this.bcryptService = bcryptService;
        this.jwtTokenService = jwtTokenService;
    }

    public Object register(AuthDto.RegisterDto data) {

        if (authRepository.findByEmail(data.getEmail()).isPresent()) {
            throw HttpException.badRequest("Email is already registered.");
        }

        // Check if phone already exists
        if (authRepository.findByPhone(data.getPhone()).isPresent()) {
            throw HttpException.badRequest("Phone number is already registered.");
        }

        String hash = bcryptService.hashPassword(data.getPassword());
        data.setEmail(data.getEmail().toLowerCase());
        data.setPassword(hash);
        Auth user = AuthMapper.INSTANCE.toEntity(data);
        authRepository.save(user);
        return null;
    }

    public Object login(AuthDto.LoginDto data) {
        // Check if email exists
        Optional<Auth> check = authRepository.findByEmail(data.getEmail().toLowerCase());

        if (check.isPresent()) {
            Auth user = check.get();
            if (bcryptService.checkPassword(data.getPassword(), user.getPassword())) {
                String token = jwtTokenService.generateToken(user.getId().toString());
                Map<String, Object> response = new HashMap<>();
                response.put("data", user);
                response.put("token", token);
                return response;
            } else {
                throw HttpException.badRequest("Invalid Credentials");
            }
        } else {
            throw HttpException.badRequest("Email not found");
        }
    }

    public Object updatePassword(String id, AuthDto.UpdatePasswordDto data) {
        Optional<Auth> check = authRepository.findById(UUID.fromString(id));
        if (check.isPresent()) {
            Auth user = check.get();
            if (bcryptService.checkPassword(data.getOldPassword(), user.getPassword())) {
                String hash = bcryptService.hashPassword(data.getNewPassword());
                user.setPassword(hash);
                authRepository.save(user);
                Map<String, Object> response = new HashMap<>();
                response.put("data", user);
                return response;
            } else {
                throw HttpException.unauthorized("Invalid Credentials");
            }
        } else {
            throw HttpException.internalServerError("Error");
        }
    }
}