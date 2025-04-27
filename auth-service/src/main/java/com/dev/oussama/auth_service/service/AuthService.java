package com.dev.oussama.auth_service.service;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.dev.oussama.auth_service.dto.AuthRequest;
import com.dev.oussama.auth_service.dto.AuthResponse;
import com.dev.oussama.auth_service.model.User;
import com.dev.oussama.auth_service.repository.UserRepository;
import com.dev.oussama.auth_service.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final AuthenticationManager authenticationManager;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public AuthResponse register(AuthRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
    }

    User user = User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .username(request.getEmail().split("@")[0])
            .roles(List.of("ROLE_USER"))
            .build();
    
    userRepository.save(user);
    
    return generateAuthResponse(user);
  }

  public AuthResponse login( AuthRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword())
    );
    
    User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    
    return generateAuthResponse(user);
  }

  private AuthResponse generateAuthResponse(User user) {
    return AuthResponse.builder()
            .accessToken(jwtService.generateToken(user))
            .expiresAt(Instant.now().plusMillis(jwtService.getJwtExpiration()))
            .email(user.getEmail())
            .roles(user.getRoles())
            .build();
  }
}