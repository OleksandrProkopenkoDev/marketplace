//package ua.tc.marketplace.jwtAuth;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jws;
//import io.jsonwebtoken.JwtException;
//import io.jsonwebtoken.Jwts;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//import com.google.common.base.Strings;
//import javax.crypto.SecretKey;
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Set;
//
//@AllArgsConstructor
//public class JwtTokenVerifier extends OncePerRequestFilter {
//
//    private final SecretKey secretKey;
////    private final JwtConfig jwtConfig;
//@Value("${jwt.token-prefix}")
//private String tokenPrefix;
//
//    @Override
//    protected void doFilterInternal(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            FilterChain filterChain)
//            throws ServletException, IOException {
//
////		get the token from the header
//        String authorizationHeader = request.getHeader("Authorization");
//        //if token is not present -> not authorized and go to next filter
//        if(Strings.isNullOrEmpty(authorizationHeader)
//                || !authorizationHeader.startsWith(tokenPrefix)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//
//        String token = authorizationHeader.replace(tokenPrefix, "");
//        try {
//            Jws<Claims> claimsJws =
//                    Jwts.parser()
//                            .setSigningKey(secretKey)
//                            .parseClaimsJws(token);
//            Claims body = claimsJws.getBody();
//            String username = body.getSubject();
//
//            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();
//            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
//
//            Authentication authentication = new UsernamePasswordAuthenticationToken(
//                    username, null, simpleGrantedAuthorities);
//
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            //the client who send the token is now authenticated
//
//        }catch (JwtException e) {
//            throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//}
