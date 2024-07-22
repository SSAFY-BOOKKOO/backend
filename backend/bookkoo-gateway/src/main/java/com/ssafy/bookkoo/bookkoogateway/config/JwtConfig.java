package com.ssafy.bookkoo.bookkoogateway.config;


import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String issuer;
    private String secretKey;

    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
