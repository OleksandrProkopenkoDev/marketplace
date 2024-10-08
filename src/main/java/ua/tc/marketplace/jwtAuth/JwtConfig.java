package ua.tc.marketplace.jwtAuth;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

  private String secretKey;
  private String tokenPrefix;
  private Integer tokenExpirationAfterDays;
  private static Long SECONDS_PER_DAY = 24L * 60L * 60L;

  public String getAuthorizationHeader() {
    return HttpHeaders.AUTHORIZATION;
  }

  public Long getTokenExpirationAfterSeconds() {
    return tokenExpirationAfterDays * SECONDS_PER_DAY;
  }

  public SecretKey getSecretKeyHmac() {
    return Keys.hmacShaKeyFor(secretKey.getBytes());
  }
}
