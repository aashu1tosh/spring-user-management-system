package com.example.user_management_system.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // This method handles validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);

        // To get all error message
        // Map<String, String> errors = new HashMap<>();
        // ex.getBindingResult().getFieldErrors()
        // .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        String firstErrorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .findFirst() // Get the first error
                .map(error -> error.getDefaultMessage()) // Extract the error message
                .orElse("Validation error");
        response.put("message", firstErrorMessage);

        // Return a bad request status with the error messages
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<Map<String, Object>> handleHttpException(HttpException ex) {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, ex.getStatus());
    }
}