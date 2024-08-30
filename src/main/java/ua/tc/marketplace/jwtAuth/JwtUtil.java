package ua.tc.marketplace.jwtAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ua.tc.marketplace.model.entity.User;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
@AllArgsConstructor
@Slf4j
public class JwtUtil {
    private final JwtConfig jwtConfig;


    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime()
                + TimeUnit.SECONDS.toMillis(jwtConfig.getTokenExpirationAfterSeconds()));
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(tokenValidity)
                .signWith(jwtConfig.getSecretKeyHmac(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims parseJwtClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtConfig.getSecretKeyHmac()).build()
                .parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest request) {
//        try {
        String token = resolveToken(request);
        if (token != null) {
            return parseJwtClaims(token);
        }
        return null;
//        } catch (ExpiredJwtException ex) {
//            log.debug("Expired exception - {}", ex.getMessage());
//            request.setAttribute("expired", ex.getMessage());
//            throw ex;
//        } catch (Exception ex) {
//            log.debug("invalid exception - {}", ex.getMessage());
//            request.setAttribute("invalid", ex.getMessage());
//            throw ex;
//        }
    }

    public String resolveToken(HttpServletRequest request) {

        String bearerToken = request.getHeader(jwtConfig.getAuthorizationHeader());
        if (bearerToken != null && bearerToken.startsWith(jwtConfig.getTokenPrefix())) {
            return bearerToken.substring(jwtConfig.getTokenPrefix().length());
        }
        return null;
    }

    public boolean validateClaims(Claims claims) {
//        try {
        return claims.getExpiration().after(new Date());
//        } catch (Exception e) {
//            throw e;
//        }
    }
}
