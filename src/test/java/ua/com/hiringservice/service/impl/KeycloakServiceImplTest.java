package ua.com.hiringservice.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.util.Collections;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientResource;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.RoleResource;
import org.keycloak.admin.client.resource.RoleScopeResource;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;
import ua.com.hiringservice.config.KeycloakClientConfig;
import ua.com.hiringservice.exception.keycloack.KeycloakClientIdNotFoundException;
import ua.com.hiringservice.exception.keycloack.KeycloakRealmNotFoundException;
import ua.com.hiringservice.exception.keycloack.KeycloakRoleNotFoundException;
import ua.com.hiringservice.exception.keycloack.KeycloakUserNotFoundException;
import ua.com.hiringservice.exception.keycloack.KeycloakUserProvideInvalidCredentialsException;
import ua.com.hiringservice.model.dto.KeycloakUserDto;
import ua.com.hiringservice.model.enums.KeycloakRole;
import ua.com.hiringservice.util.mapper.KeycloakUserMapper;

@SuppressWarnings("PMD")
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class KeycloakServiceImplTest {
  private final String realm = "monitet-service-realm";
  private final String clientId = "monitet-service-client";
  private final String clientSecret = "VBY5gvsH4YaU60nBDSv6e0MzEYjACCOP";
  private final String url = "http://localhost:9080";
  private final String clientResourceId = UUID.randomUUID().toString();
  private final UUID keycloakId = UUID.randomUUID();

  @Mock private Keycloak keycloak;
  @Mock private KeycloakClientConfig config;
  @Mock private RealmResource realmResource;
  @Mock private ClientsResource clientsResource;
  @Mock private ClientRepresentation clientRepresentation;
  @Mock private ClientResource clientResource;
  @Mock private UsersResource usersResource;
  @Mock private UserResource userResource;
  @Mock private UserRepresentation userRepresentation;
  @Mock private RolesResource rolesResource;
  @Mock private RoleResource roleResource;
  @Mock private RoleRepresentation roleRepresentation;
  @Mock private RoleMappingResource roleMappingResource;
  @Mock private RoleScopeResource roleScopeResource;
  @Mock private KeycloakUserMapper keycloakUserMapper;
  @Mock private RestTemplate restTemplate;
  @Mock private ResponseEntity<AccessTokenResponse> response;
  @InjectMocks private KeycloakServiceImpl keycloakService;

  /* todo problem: test can be passed only when main keycloak service is run -
  tests can't use our dev services we shod use testcontainers */
  @Test
  void getAccessToken_shouldReturnToken() {
    String token = "token";

    AccessTokenResponse accessToken = new AccessTokenResponse();
    accessToken.setToken(token);

    response = ResponseEntity.status(HttpStatus.ACCEPTED).body(accessToken);

    when(config.getRealm()).thenReturn(realm);
    when(config.getClientId()).thenReturn(clientId);
    when(config.getClientSecret()).thenReturn(clientSecret);
    when(config.getUrl()).thenReturn(url);
    when(restTemplate.exchange(
            (URI) any(),
            (HttpMethod) any(),
            (HttpEntity<?>) any(),
            (ParameterizedTypeReference<AccessTokenResponse>) any()))
        .thenReturn(response);

    String username = "u-1@email.com";
    String password = "u-pass-1";
    AccessTokenResponse result = keycloakService.getAccessToken(username, password);
    assertThat(result.getToken()).isNotNull();
  }

  /* todo problem: test can be passed only when main keycloak service is run -
  tests can't use our dev services we shod use testcontainers */
  @Test
  void getAccessToken_shouldThrowException_whenWrongUsername() {
    String usernameNotExisting = "u-11@email.com";
    String password = "u-pass-1";
    String token =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI"
            + "6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    AccessTokenResponse accessToken = new AccessTokenResponse();
    accessToken.setToken(token);

    response = ResponseEntity.status(HttpStatus.ACCEPTED).body(accessToken);

    when(config.getRealm()).thenReturn(realm);
    when(config.getClientId()).thenReturn(clientId);
    when(config.getClientSecret()).thenReturn(clientSecret);
    when(config.getUrl()).thenReturn(url);
    when(restTemplate.exchange(
            (URI) any(),
            (HttpMethod) any(),
            (HttpEntity<?>) any(),
            (ParameterizedTypeReference<AccessTokenResponse>) any()))
        .thenThrow(new KeycloakUserProvideInvalidCredentialsException());

    Assertions.assertThatThrownBy(
            () -> keycloakService.getAccessToken(usernameNotExisting, password))
        .isInstanceOf(KeycloakUserProvideInvalidCredentialsException.class);
  }

  @Test
  @WithMockUser("aab213ab-9549-49e7-8306-c2b482bbd2b6")
  void updateNameOfUserFromSecurityContext_shouldUpdateName() {
    UUID currentUserKeycloakId = UUID.randomUUID();

    when(config.getRealm()).thenReturn(realm);
    when(keycloak.realm(anyString())).thenReturn(realmResource);
    when(realmResource.users()).thenReturn(usersResource);
    when(usersResource.get(anyString())).thenReturn(userResource);

    userRepresentation.setId(currentUserKeycloakId.toString());
    when(userResource.toRepresentation()).thenReturn(userRepresentation);
    String firsName = "firstName";
    String lastName = "lastName";
    KeycloakUserDto updatedKeycloakUserDto =
        new KeycloakUserDto(currentUserKeycloakId.toString(), firsName, lastName, "");

    doNothing().when(userRepresentation).setFirstName(anyString());
    doNothing().when(userRepresentation).setLastName(anyString());

    KeycloakUserDto result =
        keycloakService.updateNameOfUserFromSecurityContext(firsName, lastName);

    assertThat(result).isEqualTo(keycloakUserMapper.toDto(userRepresentation));

    verify(keycloak).realm(anyString());

    verify(realmResource).users();
    verify(usersResource).get(anyString());
    verify(userResource, times(2)).toRepresentation();

    verify(userRepresentation).setFirstName(anyString());
    verify(userRepresentation).setLastName(anyString());

    verify(keycloakUserMapper, times(2)).toDto(any(UserRepresentation.class));
  }

  @Test
  void addApplicantRoleToUserByKeycloakId() {
    when(config.getRealm()).thenReturn(realm);
    when(config.getClientId()).thenReturn(clientId);
    when(keycloak.realm(anyString())).thenReturn(realmResource);
    when(realmResource.clients()).thenReturn(clientsResource);
    final var clientRepresentations = Collections.singletonList(clientRepresentation);
    when(clientsResource.findByClientId(anyString())).thenReturn(clientRepresentations);
    when(clientRepresentation.getId()).thenReturn(clientResourceId);
    when(clientsResource.get(anyString())).thenReturn(clientResource);
    when(realmResource.users()).thenReturn(usersResource);
    when(usersResource.get(anyString())).thenReturn(userResource);
    when(clientResource.roles()).thenReturn(rolesResource);
    when(rolesResource.get(anyString())).thenReturn(roleResource);
    when(roleResource.toRepresentation()).thenReturn(roleRepresentation);
    when(userResource.roles()).thenReturn(roleMappingResource);
    when(roleMappingResource.clientLevel(anyString())).thenReturn(roleScopeResource);
    when(clientResource.toRepresentation()).thenReturn(clientRepresentation);

    keycloakService.addApplicantRoleToUserByKeycloakId(keycloakId);

    verify(keycloak).realm(anyString());

    verify(realmResource, times(2)).clients();
    verify(clientsResource).findByClientId(anyString());
    verify(clientRepresentation, times(2)).getId();
    verify(clientsResource).get(anyString());

    verify(realmResource).users();
    verify(usersResource).get(anyString());

    verify(clientResource).roles();
    verify(rolesResource).get(anyString());
    verify(roleResource).toRepresentation();

    verify(userResource).roles();
    verify(roleMappingResource).clientLevel(anyString());
    verify(roleScopeResource).add(anyList());
    verify(clientResource).toRepresentation();
  }

  @Test
  void addApplicantRoleToUserByKeycloakId_shouldThrowException_whenRealmNotFound() {
    when(config.getRealm()).thenReturn(realm);
    when(keycloak.realm(anyString())).thenThrow(new KeycloakRealmNotFoundException(realm));
    var keycloakId = UUID.randomUUID();
    Assertions.assertThatThrownBy(
            () -> keycloakService.addApplicantRoleToUserByKeycloakId(keycloakId))
        .isInstanceOf(KeycloakRealmNotFoundException.class)
        .hasMessageContaining("Realm resource with name: %s not found.", realm);

    verify(keycloak).realm(anyString());
  }

  @Test
  void addApplicantRoleToUserByKeycloakId_shouldThrowException_whenClientNotFound() {
    when(config.getRealm()).thenReturn(realm);
    when(config.getClientId()).thenReturn(clientId);
    when(keycloak.realm(anyString())).thenReturn(realmResource);
    when(realmResource.clients()).thenReturn(clientsResource);
    final var clientRepresentations = Collections.singletonList(clientRepresentation);
    when(clientsResource.findByClientId(anyString())).thenReturn(clientRepresentations);
    when(clientRepresentation.getId()).thenReturn(clientResourceId);
    when(clientsResource.get(anyString()))
        .thenThrow(new KeycloakClientIdNotFoundException(clientId));

    Assertions.assertThatThrownBy(
            () -> keycloakService.addApplicantRoleToUserByKeycloakId(keycloakId))
        .isInstanceOf(KeycloakClientIdNotFoundException.class)
        .hasMessageContaining("Keycloak client id: %s not found.", clientId);

    verify(keycloak).realm(anyString());

    verify(realmResource, times(2)).clients();
    verify(clientsResource).findByClientId(anyString());
    verify(clientRepresentation).getId();
    verify(clientsResource).get(anyString());
  }

  @Test
  void addApplicantRoleToUserByKeycloakId_shouldThrowException_whenUserNotFound() {
    when(config.getRealm()).thenReturn(realm);
    when(config.getClientId()).thenReturn(clientId);
    when(keycloak.realm(anyString())).thenReturn(realmResource);
    when(realmResource.clients()).thenReturn(clientsResource);
    final var clientRepresentations = Collections.singletonList(clientRepresentation);
    when(clientsResource.findByClientId(anyString())).thenReturn(clientRepresentations);
    when(clientRepresentation.getId()).thenReturn(clientResourceId);
    when(clientsResource.get(anyString())).thenReturn(clientResource);
    when(realmResource.users()).thenReturn(usersResource);
    when(usersResource.get(anyString()))
        .thenThrow(new KeycloakUserNotFoundException(keycloakId.toString()));
    Assertions.assertThatThrownBy(
            () -> keycloakService.addApplicantRoleToUserByKeycloakId(keycloakId))
        .isInstanceOf(KeycloakUserNotFoundException.class)
        .hasMessageContaining("Keycloak user with id: %s not found.", keycloakId.toString());

    verify(keycloak).realm(anyString());

    verify(realmResource, times(2)).clients();
    verify(clientsResource).findByClientId(anyString());
    verify(clientRepresentation).getId();
    verify(clientsResource).get(anyString());

    verify(realmResource).users();
    verify(usersResource).get(anyString());
  }

  @Test
  void addApplicantRoleToUserByKeycloakId_shouldThrowException_whenRoleNotFound() {
    when(config.getRealm()).thenReturn(realm);
    when(config.getClientId()).thenReturn(clientId);
    when(keycloak.realm(anyString())).thenReturn(realmResource);
    when(realmResource.clients()).thenReturn(clientsResource);
    final var clientRepresentations = Collections.singletonList(clientRepresentation);
    when(clientsResource.findByClientId(anyString())).thenReturn(clientRepresentations);
    when(clientRepresentation.getId()).thenReturn(clientResourceId);
    when(clientsResource.get(anyString())).thenReturn(clientResource);
    when(realmResource.users()).thenReturn(usersResource);
    when(usersResource.get(anyString())).thenReturn(userResource);
    when(clientResource.roles()).thenReturn(rolesResource);
    when(rolesResource.get(anyString())).thenReturn(roleResource);
    when(roleResource.toRepresentation())
        .thenThrow(new KeycloakRoleNotFoundException(KeycloakRole.ROLE_APPLICANT.name()));

    Assertions.assertThatThrownBy(
            () -> keycloakService.addApplicantRoleToUserByKeycloakId(keycloakId))
        .isInstanceOf(KeycloakRoleNotFoundException.class)
        .hasMessageContaining(
            "Keycloak role with name: %s not found.", KeycloakRole.ROLE_APPLICANT.name());

    verify(keycloak).realm(anyString());

    verify(realmResource, times(2)).clients();
    verify(clientsResource).findByClientId(anyString());
    verify(clientRepresentation).getId();
    verify(clientsResource).get(anyString());

    verify(realmResource).users();
    verify(usersResource).get(anyString());

    verify(clientResource).roles();
    verify(rolesResource).get(anyString());
    verify(roleResource).toRepresentation();
  }
}
