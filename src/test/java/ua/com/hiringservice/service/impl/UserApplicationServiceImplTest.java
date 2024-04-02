package ua.com.hiringservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mock.web.MockMultipartFile;
import ua.com.hiringservice.model.dto.UserApplicationFormDto;
import ua.com.hiringservice.model.dto.vacancy.VacancyDto;
import ua.com.hiringservice.model.entity.user.UserApplication;
import ua.com.hiringservice.model.entity.user.UserApplicationCv;
import ua.com.hiringservice.model.entity.user.UserDetails;
import ua.com.hiringservice.model.entity.vacancy.Vacancy;
import ua.com.hiringservice.repository.UserApplicationRepository;
import ua.com.hiringservice.repository.UserDetailsRepository;
import ua.com.hiringservice.repository.VacancyRepository;
import ua.com.hiringservice.repository.mongo.UserApplicationCvRepository;
import ua.com.hiringservice.service.EmailNotificationService;
import ua.com.hiringservice.service.KeycloakService;
import ua.com.hiringservice.service.UserDetailsService;
import ua.com.hiringservice.service.VacancyService;
import ua.com.hiringservice.util.mapper.UserApplicationMapper;
import ua.com.hiringservice.util.mapper.VacancyMapper;

@SuppressWarnings("PMD")
@ExtendWith(MockitoExtension.class)
// todo: add tests
class UserApplicationServiceImplTest {

  @Mock private UserApplicationCvRepository userApplicationCvRepository;
  @Mock private UserApplicationRepository userApplicationRepository;
  @Mock private KeycloakService keycloakService;
  @Mock private UserDetailsService userDetailsService;
  @Mock private UserDetailsRepository userDetailsRepository;
  @Mock private VacancyService vacancyService;
  @Mock private VacancyMapper vacancyMapper;
  @Mock private HttpServletRequest httpServletRequest;
  @Mock private VacancyRepository vacancyRepository;
  @Mock private EmailNotificationService emailNotificationService;
  @Mock private UserApplicationMapper userApplicationMapper;
  @InjectMocks private UserApplicationServiceImpl userApplicationService;

  @Test
  void isUserApplicationExistByVacancyIdAndKeycloakId_shouldReturnTrue_whenApplicationExists() {
    UUID vacancyId = UUID.randomUUID();
    UUID userKeycloakId = UUID.randomUUID();
    when(userApplicationRepository.findByVacancyIdAndUserDetailsUserKeycloakId(
            vacancyId, userKeycloakId))
        .thenReturn(Optional.of(new UserApplication()));

    boolean exists =
        userApplicationService.isUserApplicationExistByVacancyIdAndKeycloakId(
            vacancyId, userKeycloakId);

    assertTrue(exists);
  }

  @Test
  void
      isUserApplicationExistByVacancyIdAndKeycloakId_shouldReturnFalse_whenApplicationDoesNotExist() {
    // Arrange
    UUID vacancyId = UUID.randomUUID();
    UUID userKeycloakId = UUID.randomUUID();
    when(userApplicationRepository.findByVacancyIdAndUserDetailsUserKeycloakId(
            vacancyId, userKeycloakId))
        .thenReturn(Optional.empty());

    boolean exists =
        userApplicationService.isUserApplicationExistByVacancyIdAndKeycloakId(
            vacancyId, userKeycloakId);

    assertFalse(exists);
  }

  @Test
  void
      isUserApplicationExistByVacancyIdAndKeycloakId_shouldThrowException_whenRepositoryAccessErrorOccurs() {
    UUID vacancyId = UUID.randomUUID();
    UUID userKeycloakId = UUID.randomUUID();
    when(userApplicationRepository.findByVacancyIdAndUserDetailsUserKeycloakId(
            vacancyId, userKeycloakId))
        .thenThrow(new DataIntegrityViolationException("Database access error"));

    assertThrows(
        DataIntegrityViolationException.class,
        () ->
            userApplicationService.isUserApplicationExistByVacancyIdAndKeycloakId(
                vacancyId, userKeycloakId));
  }

  @Test
  void testCreateUserApplicationForUserFromSecurityContext_shouldAddRoleApplicantUpdateNameAndSave()
      throws IOException, MessagingException, TemplateException {

    final UUID currentUserKeycloakId = UUID.randomUUID();
    when(keycloakService.getKeycloakIdOfUserFromSecurityContext())
        .thenReturn(currentUserKeycloakId);
    when(userDetailsRepository.findByUserKeycloakId(currentUserKeycloakId))
        .thenReturn(Optional.of(new UserDetails()));
    when(vacancyService.getById(any(UUID.class))).thenReturn(new VacancyDto());
    when(userApplicationCvRepository.insert(any(UserApplicationCv.class)))
        .thenReturn(new UserApplicationCv());

    final Vacancy vacancy = new Vacancy();
    vacancy.setUserApplications(new ArrayList<>());
    when(vacancyMapper.toEntity(any(VacancyDto.class))).thenReturn(vacancy);

    final UserApplicationFormDto userApplicationFormDto = new UserApplicationFormDto();
    userApplicationFormDto.setVacancyId(UUID.randomUUID());
    userApplicationFormDto.setFirstName("John");
    userApplicationFormDto.setLastName("Doe");
    userApplicationFormDto.setCv(new MockMultipartFile("cv", new byte[0]));
    userApplicationFormDto.setTermsAndConditionsState(Boolean.TRUE);
    userApplicationFormDto.setVacancyId(UUID.randomUUID());

    userApplicationService.createUserApplicationForUserFromSecurityContext(
        userApplicationFormDto, httpServletRequest);

    verify(keycloakService).addApplicantRoleToUserByKeycloakId(currentUserKeycloakId);
    verify(keycloakService).updateNameOfUserFromSecurityContext("John", "Doe");
    verify(userApplicationRepository).save(any());
  }

  @Test
  void getOrCreateUserDetailsByKeycloakId_shouldReturnUserDetails_whenUserDetailsExist()
      throws Exception {
    UUID userKeycloakId = UUID.randomUUID();
    UserDetails userDetails = new UserDetails();
    userDetails.setUserKeycloakId(userKeycloakId);
    when(userDetailsRepository.findByUserKeycloakId(userKeycloakId))
        .thenReturn(Optional.of(userDetails));

    Method method =
        UserApplicationServiceImpl.class.getDeclaredMethod(
            "getOrCreateUserDetailsByKeycloakId", UUID.class);
    method.setAccessible(true);

    UserDetails result = (UserDetails) method.invoke(userApplicationService, userKeycloakId);

    assertNotNull(result);
    assertEquals(userKeycloakId, result.getUserKeycloakId());
  }

  @Test
  void getOrCreateUserDetailsByKeycloakId_shouldCreateUserDetails_whenUserDetailsDoNotExist()
      throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
    UUID userKeycloakId = UUID.randomUUID();
    UserDetails userDetails = new UserDetails();
    userDetails.setUserKeycloakId(userKeycloakId);
    when(userDetailsRepository.findByUserKeycloakId(userKeycloakId)).thenReturn(Optional.empty());
    when(userDetailsService.createUserDetailsForUserFromSecurityContext()).thenReturn(userDetails);

    Method method =
        UserApplicationServiceImpl.class.getDeclaredMethod(
            "getOrCreateUserDetailsByKeycloakId", UUID.class);
    method.setAccessible(true);

    UserDetails result = (UserDetails) method.invoke(userApplicationService, userKeycloakId);

    assertNotNull(result);
    assertEquals(userKeycloakId, result.getUserKeycloakId());
  }
}
