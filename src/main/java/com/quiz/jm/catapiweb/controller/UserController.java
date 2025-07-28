package com.quiz.jm.catapiweb.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.quiz.jm.catapiweb.dto.UserLoginDto;
import com.quiz.jm.catapiweb.dto.UserRegisterDto;
import com.quiz.jm.catapiweb.dto.UserResponseDto;
import com.quiz.jm.catapiweb.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("user")
@Tag(name = "User Management", description = "Operations related to user registration, login, and profile.") 
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Register a new user",
               description = "Registers a new user with the provided username, password, and email.",
               responses = {
                   @ApiResponse(responseCode = "201", description = "User registered successfully",
                                content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
                   @ApiResponse(responseCode = "400", description = "Invalid input or username already exists")
               })
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterDto registerDto) {
        UserResponseDto registeredUser = userService.registerUser(registerDto);
        UserLoginDto loginDto = new UserLoginDto(registerDto.getUsername(), registerDto.getPassword());
        String jwt = userService.loginUser(loginDto);
        return new ResponseEntity<>(jwt, HttpStatus.CREATED);
    }

    @Operation(summary = "Login an existing user",
               description = "Logs in a user with the provided username and password.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "User logged in successfully",
                                content = @Content(schema = @Schema(implementation = String.class))),
                   @ApiResponse(responseCode = "401", description = "Invalid username or password")
               })
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDto loginDto) {
        String jwt = userService.loginUser(loginDto);
        return ResponseEntity.ok(jwt); // Devuelve el token JWT
    }

    @Operation(summary = "Get user profile",
               description = "Retrieves the profile of the currently authenticated user.",
               responses = {
                   @ApiResponse(responseCode = "200", description = "User profile retrieved successfully",
                                content = @Content(schema = @Schema(implementation = UserResponseDto.class))),
                   @ApiResponse(responseCode = "401", description = "User not authenticated")
               })
    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); 

        UserResponseDto userProfile = userService.getUserByUsername(username);
        return ResponseEntity.ok(userProfile);
    }
}