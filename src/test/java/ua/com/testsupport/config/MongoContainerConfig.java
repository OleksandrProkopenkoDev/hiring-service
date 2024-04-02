package ua.com.testsupport.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MongoDBContainer;

/**
 * @author Artyom Kondratenko
 * @since 12/17/23
 */
@TestConfiguration
@EnableAutoConfiguration
public class MongoContainerConfig {

  @Bean
  @ServiceConnection
  public static MongoDBContainer mongoContainer() {
    final MongoDBContainer container = new MongoDBContainer("mongo:7.0.4");
    container.start();
    return container;
  }
}
