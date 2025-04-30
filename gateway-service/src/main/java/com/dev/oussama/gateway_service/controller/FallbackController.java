package com.dev.oussama.gateway_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController("/fallback")
public class FallbackController {

    @GetMapping("/task")
    public Mono<ResponseEntity<String>> taskFallback() {
        return createFallbackResponse("Task Service");
    }

    @GetMapping("/auth")
    public Mono<ResponseEntity<String>> authFallback() {
        return createFallbackResponse("Auth Service");
    }

    @GetMapping("/history")
    public Mono<ResponseEntity<String>> historyFallback() {
        return createFallbackResponse("History Service");
    }

    private Mono<ResponseEntity<String>> createFallbackResponse(String serviceName) {
        return Mono.just(ResponseEntity
            .status(HttpStatus.SERVICE_UNAVAILABLE)
            .header("Content-Type", "application/json")
            .body(String.format("""
                {
                    "status": 503,
                    "service": "%s",
                    "message": "Service temporarily unavailable",
                    "recovery": "Please try again later"
                }
                """, serviceName)));
    }
}
