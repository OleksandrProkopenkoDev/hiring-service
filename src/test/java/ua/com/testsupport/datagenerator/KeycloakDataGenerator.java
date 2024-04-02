package ua.com.testsupport.datagenerator;

import java.util.UUID;
import ua.com.hiringservice.model.dto.KeycloakUserDto;

/**
 * This class provides methods to generate test data for Keycloack service.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
public final class KeycloakDataGenerator {

  private static final UUID KEYCLOAK_USER_ID =
      UUID.fromString("22fca1a0-fc80-46ca-9d99-878cbc1c8c24");
  private static final String USER_FIRST_NAME = "John";
  private static final String USER_LAST_NAME = "Dow";
  private static final String USER_EMAIL = "john.dow@monitaet.com";

  private KeycloakDataGenerator() {
    throw new UnsupportedOperationException(
        "Utility KeycloakDataGenerator class cannot be instantiated");
  }

  public static UUID generateUserKeycloakIdData() {
    return KEYCLOAK_USER_ID;
  }

  public static KeycloakUserDto generateKeycloakUserDto() {
    return KeycloakUserDto.builder()
        .userId(KEYCLOAK_USER_ID.toString())
        .firstName(USER_FIRST_NAME)
        .lastName(USER_LAST_NAME)
        .email(USER_EMAIL)
        .build();
  }
}
