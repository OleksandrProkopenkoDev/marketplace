package ua.tc.marketplace.config;

import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ua.tc.marketplace.jwtAuth.JwtAuthorizationFilter;
import ua.tc.marketplace.service.impl.UserDetailsServiceImpl;

@Slf4j
@EnableWebSecurity
@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

  private final JwtAuthorizationFilter jwtAuthorizationFilter;

  private static final String ALL_URL = "/**";
  public static final String GET_ONE_DEMO_URL = "/api/v1/demo";
  private static final String DEFAULT_SUCCESS_PAGE = GET_ONE_DEMO_URL;
  private static final String CREATE_USER_POST_URL = "/api/v1/user/signup";
  public static final String LOGIN_URL = "/api/v1/user/login";
  public static final String LOGOUT_URL = "/api/v1/user/logout";
  public static final String GET_ALL_DEMO_URL = "/api/v1/demo/all";
  public static final String SWAGGER_DOCS = "/v3/api-docs/**";
  public static final String SWAGGER_UI_PAGES = "/swagger-ui/**";
  public static final String SWAGGER_UI_MAIN = "/swagger-ui.html";

  private static final String[] WHITELIST = {
    DEFAULT_SUCCESS_PAGE,
    SWAGGER_DOCS,
    SWAGGER_UI_PAGES,
    SWAGGER_UI_MAIN,
    GET_ONE_DEMO_URL,
    GET_ALL_DEMO_URL,
    LOGIN_URL,
    DEFAULT_SUCCESS_PAGE
  };

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .authenticationProvider(authenticationProvider())
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(
            config ->
                config
                    .requestMatchers(WHITELIST)
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, CREATE_USER_POST_URL)
                    .permitAll()
                    .requestMatchers(
                        HttpMethod.GET,
                        "/api/v1/ad",
                        "/api/v1/ad/{adId}",
                        "/api/v1/photo/ad/{adId}",
                        "/api/v1/file/ad/{adId}",
                        "/api/v1/file/ad/{adId}/photo/{photoId}",
                        "/api/v1/category")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .logout(
            logout ->
                logout
                    .logoutUrl(LOGOUT_URL)
                    .invalidateHttpSession(true) // Invalidate session
                    .clearAuthentication(true) // Clear authentication
                    .deleteCookies("JSESSIONID") // If using cookies
                    .logoutSuccessHandler(
                        (request, response, authentication) ->
                            response.setStatus(HttpServletResponse.SC_OK))
                    .permitAll());
    return http.build();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setUserDetailsService(userDetailsService());
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("*"));
    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
    configuration.setAllowedHeaders(List.of("*"));
    configuration.setAllowCredentials(false);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration(ALL_URL, configuration);
    return source;
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
