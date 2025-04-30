package com.dev.oussama.gateway_service.security;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter implements GlobalFilter {

  private final JwtService jwtService;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();
    String path = request.getPath().value();
    
    // Bypass auth for certain paths
    if (path.startsWith("/api/auth") || path.startsWith("/actuator")) {
      return chain.filter(exchange);
    }

    String token = extractToken(request);
    if (token == null) {
      return unauthorizedResponse(exchange, "Missing token");
    }

    try {
      if (jwtService.isTokenValid(token)) {
        String userId = jwtService.extractUsername(token);
        exchange.getRequest().mutate()
          .header("X-User-ID", userId)
          .build();
        return chain.filter(exchange);
      }
      return unauthorizedResponse(exchange, "Invalid token");
    } catch (Exception e) {
      return unauthorizedResponse(exchange, "Token validation error");
    }
  }

  private String extractToken(ServerHttpRequest request) {
    String header = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    if (header != null && header.startsWith("Bearer ")) {
      return header.substring(7);
    }
    return null;
  }

  private Mono<Void> unauthorizedResponse(ServerWebExchange exchange, String message) {
    ServerHttpResponse response = exchange.getResponse();
    response.setStatusCode(HttpStatus.UNAUTHORIZED);
    response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
    String body = String.format("{\"error\": \"Unauthorized\", \"message\": \"%s\"}", message);
    return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
  }
}
