package ua.com.hiringservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/** Entry point to Spring boot application. */
@SuppressWarnings("PMD.UseUtilityClass")
@SpringBootApplication
@EnableCaching
public class HiringServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(HiringServiceApplication.class, args);
  }
}
