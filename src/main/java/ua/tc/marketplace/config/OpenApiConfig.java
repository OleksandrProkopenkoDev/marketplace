package ua.tc.marketplace.config;

import io.swagger.v3.oas.models.OpenAPI;
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
                .version(DOCUMENTATION_VERSION));
  }
}
