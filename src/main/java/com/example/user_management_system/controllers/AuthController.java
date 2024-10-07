package com.example.user_management_system.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.user_management_system.dto.AuthDto;
import com.example.user_management_system.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // Constructor-based injection (recommended)
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody AuthDto.RegisterDto requestBody) {
        Object result = authService.register(requestBody);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "User register successful");
        response.put("data", result);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AuthDto.LoginDto requestBody) {
        // Object result = authService.register(requestBody);
        Object result = authService.login(requestBody);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "User login successfully");
        response.put("data", result);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<Map<String, Object>> updatePassword(
            @Valid @RequestBody AuthDto.UpdatePasswordDto requestBody) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String id = authentication.getName();
        Object result = authService.updatePassword(id, requestBody);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Password Update successful");
        response.put("data", result);
        return ResponseEntity.ok(response);
    }

}
