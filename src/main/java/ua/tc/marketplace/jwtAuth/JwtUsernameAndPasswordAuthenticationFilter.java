package ua.tc.marketplace.jwtAuth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ua.tc.marketplace.model.entity.User;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

@AllArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter
        extends UsernamePasswordAuthenticationFilter {


    private final AuthenticationManager authenticationManager;
//    private final JwtConfig jwtConfig;
@Value("${jwt.token-prefix}")
private String tokenPrefix;
    @Value("${jwt.token-expiration-after-days}")
    private String tokenExpirationAfterDays;
    private final SecretKey secretKey;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response)
            throws AuthenticationException {
        //verifies credentials
        try {
            AuthRequest authRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), AuthRequest.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authRequest.email(),
                    authRequest.password());
            Authentication authenticate = authenticationManager.authenticate(authentication);
            return authenticate;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //this method will be invoked after attempt authentication is succesfull
    // we will create token here. If auth fails method not invoked
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        User authUser = (User) authResult.getPrincipal();
        String token = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("userId", authUser.getId())
                .claim("email", authUser.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(LocalDate.now().plusDays(Long.parseLong(tokenExpirationAfterDays)).atStartOfDay().toInstant(ZoneOffset.UTC)))
                .signWith(secretKey)
                .compact();
        response.addHeader(
                "Authorization",
                tokenPrefix+ token);
//		next filter

    }


}