package com.example.testtask.controller;

import com.example.testtask.dto.AuthRequest;
import com.example.testtask.dto.AuthResponse;
import com.example.testtask.dto.RegisterRequest;
import com.example.testtask.exception.UserNotFoundException;
import com.example.testtask.model.User;
import com.example.testtask.model.enums.Role;
import com.example.testtask.model.enums.Status;
import com.example.testtask.repository.UserRepository;
import com.example.testtask.security.jwt.JwtTokenProvider;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(Role.USER))
                .enabled(true)
                .status(Status.ACTIVE)
                .build();

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                )
        );

        String token = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<?> me(Principal principal) {
        String username = principal.getName();
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        return ResponseEntity.ok(user);
    }
}

