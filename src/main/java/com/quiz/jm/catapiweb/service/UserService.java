package com.quiz.jm.catapiweb.service;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quiz.jm.catapiweb.dto.UserLoginDto;
import com.quiz.jm.catapiweb.dto.UserRegisterDto;
import com.quiz.jm.catapiweb.dto.UserResponseDto;
import com.quiz.jm.catapiweb.exception.ResourceNotFoundException;
import com.quiz.jm.catapiweb.model.User;
import com.quiz.jm.catapiweb.repository.UserRepository;
import com.quiz.jm.catapiweb.util.JwtUtil;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public UserResponseDto registerUser(UserRegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User newUser = new User();
        newUser.setUsername(registerDto.getUsername());
        newUser.setEmail(registerDto.getEmail());
        String encryptedPassword = passwordEncoder.encode(registerDto.getPassword());
        newUser.setPassword(encryptedPassword);

        User savedUser = userRepository.save(newUser);
        return convertToUserResponseDto(savedUser);
    }

    public String loginUser(UserLoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtUtil.generateToken(userDetails);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid username or password", e);
        }
    }

    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
        return convertToUserResponseDto(user);
    }

    private UserResponseDto convertToUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }
}