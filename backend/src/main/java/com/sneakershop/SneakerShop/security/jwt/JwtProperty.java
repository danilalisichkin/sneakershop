package com.sneakershop.SneakerShop.security.jwt;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Data
@Builder
@ConfigurationProperties(prefix = "token")
public class JwtProperty {
    private String jwtIssuer;
    private String jwtSecret;
    private Duration jwtLifetime;
}
