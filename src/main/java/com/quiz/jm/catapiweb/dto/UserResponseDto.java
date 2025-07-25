package com.quiz.jm.catapiweb.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;

    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
}