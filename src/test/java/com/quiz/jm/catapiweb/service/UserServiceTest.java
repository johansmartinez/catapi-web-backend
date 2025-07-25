package com.quiz.jm.catapiweb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.quiz.jm.catapiweb.dto.UserLoginDto;
import com.quiz.jm.catapiweb.dto.UserRegisterDto;
import com.quiz.jm.catapiweb.dto.UserResponseDto;
import com.quiz.jm.catapiweb.exception.ResourceNotFoundException;
import com.quiz.jm.catapiweb.model.User;
import com.quiz.jm.catapiweb.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;
    private UserRegisterDto registerDto;
    private UserLoginDto loginDto;

    @BeforeEach
    void setUp() {
        testUser = new User(1L, "testuser", "encodedPassword", "test@example.com");
        registerDto = new UserRegisterDto("newuser", "password123", "new@example.com");
        loginDto = new UserLoginDto("testuser", "rawPassword");
    }

    @Test
    void registerUser_Success() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        UserResponseDto result = userService.registerUser(registerDto);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void registerUser_UsernameAlreadyExists_ThrowsException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));

        assertThrows(IllegalArgumentException.class, () -> userService.registerUser(registerDto));
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    void loginUser_UserNotFound_ThrowsException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.loginUser(loginDto));
    }

    @Test
    void loginUser_InvalidPassword_ThrowsException() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> userService.loginUser(loginDto));
    }
}