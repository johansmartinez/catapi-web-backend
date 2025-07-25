package com.quiz.jm.catapiweb.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDto {
    @NotBlank(message = "Username cannot be empty")
    @Size(min = 3, max = 50)
    private String username;
    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    public UserRegisterDto( String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}