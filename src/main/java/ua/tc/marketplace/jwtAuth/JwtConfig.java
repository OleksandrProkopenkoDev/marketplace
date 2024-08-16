//package ua.tc.marketplace.jwtAuth;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpHeaders;
//
//@Getter
//@Setter
//@Configuration
//@ConfigurationProperties(prefix = "jwt")
//public class JwtConfig {
//
////    @Value("${jwt.secretKey}")
//    private String secretKey;
//
////    @Value("${jwt.tokenPrefix}")
//    private String tokenPrefix;
//
////    @Value("${jwt.tokenExpirationAfterDays}")
//    private Integer tokenExpirationAfterDays;
//
//    public String getAuthorizationHeader() {
//        return HttpHeaders.AUTHORIZATION;
//    }
//}
