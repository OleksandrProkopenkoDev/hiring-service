package ua.com.hiringservice.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for OpenAPI documentation of the Quiz service. Defines security requirements
 * for bearer authentication and specifies the API information.
 *
 * <p>The class is annotated with {@link Configuration} to indicate that it contains bean
 * definitions. It utilizes annotations from the {@link io.swagger.v3.oas.annotations} package for
 * configuring OpenAPI documentation, including security schemes and API information.
 */
@Configuration
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer",
    in = SecuritySchemeIn.HEADER)
public class OpenApiConfig {

  public static final String TITLE = "Hiring service";
  public static final String QUIZ_SERVICE_DESCRIPTION =
      "Java app for work quizzes, enabling assessment through interactive tests.";
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
                .description(QUIZ_SERVICE_DESCRIPTION)
                .version(DOCUMENTATION_VERSION));
  }
}
