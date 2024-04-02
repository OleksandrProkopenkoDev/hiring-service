package ua.com.hiringservice.config;

import static freemarker.template.Configuration.VERSION_2_3_32;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuration defining bean which setting up a FreeMarker template engine.
 *
 * @author Artem Dreveckyi
 */
@Configuration
public class FreeMarkerConfig {

  /**
   * Provides a FreeMarkerConfiguration Bean with a specified template loader path.
   *
   * @return Configuration with the template loader path.
   */
  @Primary
  @Bean(name = "freemarkerConfiguration")
  public freemarker.template.Configuration freeMarkerConfiguration() {
    final freemarker.template.Configuration configuration =
        new freemarker.template.Configuration(VERSION_2_3_32);
    configuration.setClassForTemplateLoading(this.getClass(), "/templates/email");
    return configuration;
  }
}
