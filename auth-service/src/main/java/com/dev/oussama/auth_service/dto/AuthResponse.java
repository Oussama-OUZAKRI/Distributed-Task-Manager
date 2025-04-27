package com.dev.oussama.auth_service.dto;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
  private String accessToken;
  @Builder.Default
  private String tokenType = "Bearer";
  private Instant expiresAt;
  private String email;
  private List<String> roles;
}