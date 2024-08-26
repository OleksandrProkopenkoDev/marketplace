package ua.tc.marketplace.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
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
import ua.tc.marketplace.jwtAuth.JwtAuthorizationFilter;
import ua.tc.marketplace.service.impl.UserDetailsServiceImpl;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {
  private final JwtAuthorizationFilter jwtAuthorizationFilter;
  private static final String DEFAULT_SUCCESS_PAGE = "/api/v1/demo";
  private static final String[] WHITELIST = {
    "/v3/api-docs/**", "/swagger-ui/**", DEFAULT_SUCCESS_PAGE, "/api/v1/user/login"
  };
  private static final String CREATE_USER_POST_URL = "/api/v1/user/signup";

  @Bean
  public UserDetailsService userDetailsService() {
    return new UserDetailsServiceImpl();
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(Customizer.withDefaults())
        .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
        .authorizeHttpRequests(
            config ->
                config
                    .requestMatchers(WHITELIST)
                    .permitAll()
                    .requestMatchers(HttpMethod.POST, CREATE_USER_POST_URL)
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .formLogin(formLogin -> formLogin.permitAll().defaultSuccessUrl(DEFAULT_SUCCESS_PAGE));
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

  //  @Bean
  //  public WebMvcConfigurer corsConfigurer() {
  //    return new WebMvcConfigurer() {
  //      @Override
  //      public void addCorsMappings(@Nonnull CorsRegistry registry) {
  //        registry.addMapping("/**")
  //            .allowedOrigins("*")
  //            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD")
  //            .allowedHeaders("*")
  //            .allowCredentials(false);
  //      }
  //    };
  //  }

  @Bean
  AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }
}
