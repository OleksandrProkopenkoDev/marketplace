package ua.tc.marketplace.jwtAuth;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ua.tc.marketplace.config.UserDetailsImpl;
import ua.tc.marketplace.exception.model.ErrorResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final ObjectMapper mapper;
    private final UserDetailsImpl userDetails;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        Map<String, Object> errorDetails = new HashMap<>();
        try {
            String accessToken = jwtUtil.resolveToken(request);
            if (accessToken == null ) {
                ErrorResponse errorResponse = new ErrorResponse(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Missing token",
                        request.getRequestURI());
                errorResponse.appendToResponse(response,mapper);
                //TODO we should think of one place for errorResponse messages
                return;
            }
            Claims claims = jwtUtil.resolveClaims(request);

            if(claims != null & jwtUtil.validateClaims(claims)){
                String email = claims.getSubject();
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(email,"", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Expired token",
                        request.getRequestURI());
                errorResponse.appendToResponse(response,mapper);
                //TODO we should think of one place for errorResponse messages
                return;
            }

        }catch (Exception e){
//            responseErrorMessage(response,"Invalid or missing token",request.getRequestURI());
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or missing token",
                    request.getRequestURI());
            errorResponse.appendToResponse(response,mapper);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void responseErrorMessage(HttpServletResponse response,
                                      String message,
                                      String requestUri) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ErrorResponse errorResponse = new ErrorResponse(
                HttpServletResponse.SC_UNAUTHORIZED,
                message,
                requestUri);
        String errorResponseJson = mapper.writeValueAsString(errorResponse);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(errorResponseJson);
    }




}
