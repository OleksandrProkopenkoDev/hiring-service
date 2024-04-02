package ua.com.testsupport.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 * @author Artyom Kondratenko
 * @since 12/17/23
 */
@TestConfiguration
public class PostgresContainerConfig {

  @ServiceConnection
  @Bean
  public static PostgreSQLContainer<?> postgreContainer() {
    final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15.2-alpine");
    container.start();
    return container;
  }
}
