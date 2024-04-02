package ua.com.hiringservice.config.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/** Replace this stub by correct Javadoc. */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

  private static final String[] WHITELIST = {"/keycloak/**", "/v3/api-docs/**", "/swagger-ui/**"};
  private static final String[] HTTP_GET_WHITELIST = {
    "/api/v1/vacancies", "/api/v1/vacancies/{id}", "/api/v1/vacancies/{id}/description"
  };

  private final JwtAuthConverter jwtAuthConverter;

  @Bean
  @SuppressWarnings("PMD.SignatureDeclareThrowsException")
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(
            request ->
                request
                    .requestMatchers(WHITELIST)
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, HTTP_GET_WHITELIST)
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .cors(Customizer.withDefaults())
        .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
    http.oauth2ResourceServer(
        server -> server.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)));
    http.sessionManagement(session -> session.sessionCreationPolicy(STATELESS));
    return http.build();
  }
}
