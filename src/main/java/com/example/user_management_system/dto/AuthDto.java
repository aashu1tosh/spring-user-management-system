package com.example.user_management_system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthDto {

    @Data
    @NoArgsConstructor
    public static class RegisterDto {
        @NotBlank(message = "Name is mandatory")
        private String name;

        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        private String email;

        @NotBlank(message = "Password is mandatory")
        private String password;

        @NotBlank(message = "Phone is mandatory")
        private String phone;

        public RegisterDto(String email, String name, String password, String phone) {
            this.email = email;
            this.name = name;
            this.password = password;
            this.phone = phone;
        }
    }

    @Data
    @NoArgsConstructor
    public static class LoginDto {
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        private String email;

        @NotBlank(message = "Password is mandatory")
        private String password;

        public LoginDto(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    @Data
    @NoArgsConstructor
    public static class UpdatePasswordDto {

        @NotBlank(message = "New Password is mandatory")
        private String newPassword;

        @NotBlank(message = "Old Password is mandatory")
        private String oldPassword;

        public UpdatePasswordDto(String newPassword, String oldPassword) {
            this.newPassword = newPassword;
            this.oldPassword = newPassword;
        }
    }
}
