package com.dev.oussama.gateway_service.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import reactor.core.publisher.Mono;

@Configuration
public class RateLimiterConfig {
    
    @Bean
    public KeyResolver userKeyResolver() {
        return exchange ->
            Mono.just(exchange.getRequest().getHeaders().getFirst("X-User-ID"))
                .defaultIfEmpty("anonymous");
    }
}
