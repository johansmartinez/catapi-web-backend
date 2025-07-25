package com.quiz.jm.catapiweb.dto;


import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class UserLoginDto {
    

    @NotBlank(message = "Username cannot be empty")
    private String username;
    @NotBlank(message = "Password cannot be empty")
    private String password;

    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}