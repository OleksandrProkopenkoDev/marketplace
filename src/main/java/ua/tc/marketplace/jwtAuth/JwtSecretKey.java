package ua.tc.marketplace.jwtAuth;

import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@AllArgsConstructor
@NoArgsConstructor
@Configuration
public class JwtSecretKey {

//    private final JwtConfig jwtConfig;
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    SecretKey secretKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
}
