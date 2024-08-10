package ua.tc.marketplace.config;

import java.util.List;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI documentation.
 *
 * <p>The class is annotated with {@link Configuration} to indicate that it contains bean
 * definitions. It utilizes annotations from the {@link io.swagger.v3.oas.annotations} package for
 * configuring OpenAPI documentation.
 */
@Configuration
public class OpenApiConfig {

  public static final String TITLE = "Marketplace app";
  public static final String MARKETPLACE_APP_DESCRIPTION =
      "Java app to help homeless animals find new home";
  public static final String DOCUMENTATION_VERSION = "v1.0";

  /**
   * Bean definition for creating the OpenAPI instance.
   *
   * @return The configured OpenAPI instance.
   */
  @Bean
  public OpenAPI openApi() {
    return new OpenAPI()
        .info(
            new io.swagger.v3.oas.models.info.Info()
                .title(TITLE)
                .description(MARKETPLACE_APP_DESCRIPTION)
                .version(DOCUMENTATION_VERSION))
        .addServersItem(
            new Server()
                .url("https://marketplace-production-c9c4.up.railway.app")
                .description("Production Server"))
        .addServersItem(new Server().url("http://localhost:8080").description("Local Server"))
        .components(new Components()
            .addSecuritySchemes("basicAuth",
                new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("basic")))
        .security(List.of(new SecurityRequirement().addList("basicAuth")));
  }
}
