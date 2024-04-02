package ua.com.hiringservice.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Stack;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.hiringservice.exception.UserApplicationNotFoundException;
import ua.com.hiringservice.model.dto.UserApplicationProcessDto;
import ua.com.hiringservice.model.dto.UserApplicationProcessStepDto;
import ua.com.hiringservice.model.dto.vacancy.VacancyDto;
import ua.com.hiringservice.model.dto.verification.VerificationDto;
import ua.com.hiringservice.model.enums.VerificationState;
import ua.com.hiringservice.model.enums.vacancy.step.status.UserApplicationProcessStepInfo;
import ua.com.hiringservice.model.enums.vacancy.step.status.UserApplicationProcessStepStatus;
import ua.com.hiringservice.repository.tour.TourPassingRepository;
import ua.com.hiringservice.service.KeycloakService;
import ua.com.hiringservice.service.UserApplicationService;
import ua.com.hiringservice.service.VacancyService;
import ua.com.hiringservice.service.VerificationService;

@SuppressWarnings("PMD")
@ExtendWith(MockitoExtension.class)
public class UserApplicationStepsCreatorTest {

  @Mock private VacancyService vacancyService;
  @Mock private VerificationService verificationService;
  @Mock private KeycloakService keycloakService;
  @Mock private UserApplicationService userApplicationService;
  @Mock private TourPassingRepository tourPassingRepository;
  @InjectMocks private UserApplicationStepsCreator userApplicationStepsCreator;

  UserApplicationProcessStepDto formApplicationStep() {
    UserApplicationProcessStepDto applicationStep = new UserApplicationProcessStepDto();
    applicationStep.setName(UserApplicationProcessStepInfo.APPLICATION.getName());
    applicationStep.setTitle(UserApplicationProcessStepInfo.APPLICATION.getTitle());
    applicationStep.setStatus(UserApplicationProcessStepStatus.ACCEPTED.name());
    return applicationStep;
  }

  UserApplicationProcessStepDto formVerificationStepWithStatusTodo() {
    UserApplicationProcessStepDto verificationStep = new UserApplicationProcessStepDto();
    verificationStep.setName(UserApplicationProcessStepInfo.VERIFICATION.getName());
    verificationStep.setTitle(UserApplicationProcessStepInfo.VERIFICATION.getTitle());
    verificationStep.setStatus(UserApplicationProcessStepStatus.TODO.name());
    return verificationStep;
  }

  UserApplicationProcessStepDto formVerificationStepWithStatusProgressing() {
    UserApplicationProcessStepDto verificationStep = new UserApplicationProcessStepDto();
    verificationStep.setName(UserApplicationProcessStepInfo.VERIFICATION.getName());
    verificationStep.setTitle(UserApplicationProcessStepInfo.VERIFICATION.getTitle());
    verificationStep.setStatus(UserApplicationProcessStepStatus.PROCESSING.name());
    return verificationStep;
  }

  UserApplicationProcessStepDto formVerificationStepWithStatusAccepted() {
    UserApplicationProcessStepDto verificationStep = new UserApplicationProcessStepDto();
    verificationStep.setName(UserApplicationProcessStepInfo.VERIFICATION.getName());
    verificationStep.setTitle(UserApplicationProcessStepInfo.VERIFICATION.getTitle());
    verificationStep.setStatus(UserApplicationProcessStepStatus.ACCEPTED.name());
    return verificationStep;
  }

  UserApplicationProcessStepDto formInterviewingStepWithStatusUntouched() {
    final UserApplicationProcessStepDto interviewingStep = new UserApplicationProcessStepDto();
    interviewingStep.setName(UserApplicationProcessStepInfo.INTERVIEWING.getName());
    interviewingStep.setTitle(UserApplicationProcessStepInfo.INTERVIEWING.getTitle());
    interviewingStep.setStatus(UserApplicationProcessStepStatus.UNTOUCHED.name());

    final UserApplicationProcessStepDto interviewingWithRecruiterStep =
        new UserApplicationProcessStepDto();
    interviewingWithRecruiterStep.setName(
        UserApplicationProcessStepInfo.INTERVIEWING_RECRUITER.getName());
    interviewingWithRecruiterStep.setTitle(
        UserApplicationProcessStepInfo.INTERVIEWING_RECRUITER.getTitle());
    interviewingWithRecruiterStep.setStatus(UserApplicationProcessStepStatus.UNTOUCHED.name());

    final UserApplicationProcessStepDto technicalInterviewingStep =
        new UserApplicationProcessStepDto();
    technicalInterviewingStep.setName(
        UserApplicationProcessStepInfo.INTERVIEWING_TECHNICAL.getName());
    technicalInterviewingStep.setTitle(
        UserApplicationProcessStepInfo.INTERVIEWING_TECHNICAL.getTitle());
    technicalInterviewingStep.setStatus(UserApplicationProcessStepStatus.UNTOUCHED.name());

    interviewingStep.setChildStep(new Stack<>());
    interviewingStep.getChildStep().push(interviewingWithRecruiterStep);
    interviewingStep.getChildStep().push(technicalInterviewingStep);

    return interviewingStep;
  }

  UserApplicationProcessStepDto formInterviewingStepWithStatusTodo() {
    final UserApplicationProcessStepDto interviewingStep = new UserApplicationProcessStepDto();
    interviewingStep.setName(UserApplicationProcessStepInfo.INTERVIEWING.getName());
    interviewingStep.setTitle(UserApplicationProcessStepInfo.INTERVIEWING.getTitle());
    interviewingStep.setStatus(UserApplicationProcessStepStatus.TODO.name());

    final UserApplicationProcessStepDto interviewingWithRecruiterStep =
        new UserApplicationProcessStepDto();
    interviewingWithRecruiterStep.setName(
        UserApplicationProcessStepInfo.INTERVIEWING_RECRUITER.getName());
    interviewingWithRecruiterStep.setTitle(
        UserApplicationProcessStepInfo.INTERVIEWING_RECRUITER.getTitle());
    interviewingWithRecruiterStep.setStatus(UserApplicationProcessStepStatus.UNTOUCHED.name());

    final UserApplicationProcessStepDto technicalInterviewingStep =
        new UserApplicationProcessStepDto();
    technicalInterviewingStep.setName(
        UserApplicationProcessStepInfo.INTERVIEWING_TECHNICAL.getName());
    technicalInterviewingStep.setTitle(
        UserApplicationProcessStepInfo.INTERVIEWING_TECHNICAL.getTitle());
    technicalInterviewingStep.setStatus(UserApplicationProcessStepStatus.UNTOUCHED.name());

    interviewingStep.setChildStep(new Stack<>());
    interviewingStep.getChildStep().push(interviewingWithRecruiterStep);
    interviewingStep.getChildStep().push(technicalInterviewingStep);

    return interviewingStep;
  }

  @Test
  void
      getStepsByVacancyId_shouldThrowUserApplicationNotFoundException_whenUserIsNotAppliedOnVacancy() {
    UUID vacancyId = UUID.randomUUID();
    UUID currentUserKeycloakId = UUID.randomUUID();

    when(keycloakService.getKeycloakIdOfUserFromSecurityContext())
        .thenReturn(currentUserKeycloakId);

    when(userApplicationService.isUserApplicationExistByVacancyIdAndKeycloakId(
            vacancyId, currentUserKeycloakId))
        .thenReturn(Boolean.FALSE);

    assertThrows(
        UserApplicationNotFoundException.class,
        () -> {
          userApplicationStepsCreator.getStepsByVacancyId(vacancyId);
        });

    verifyNoInteractions(vacancyService);
  }

  @Test
  void
      getStepsByVacancyId_shouldReturnStepsOfApplicationWithStatusAcceptedAndInterviewingWithStatusTodoAndItsChildrenWithStatusesUntouched_whenUserAppliedOnVacancyAndVacancyHasNoFields() {
    UUID vacancyId = UUID.randomUUID();
    UUID currentUserKeycloakId = UUID.randomUUID();

    when(keycloakService.getKeycloakIdOfUserFromSecurityContext())
        .thenReturn(currentUserKeycloakId);

    when(userApplicationService.isUserApplicationExistByVacancyIdAndKeycloakId(
            vacancyId, currentUserKeycloakId))
        .thenReturn(Boolean.TRUE);

    assertDoesNotThrow(() -> UserApplicationNotFoundException.class);

    VacancyDto vacancyWithNoFields = new VacancyDto();
    when(vacancyService.getById(vacancyId)).thenReturn(vacancyWithNoFields);

    verifyNoInteractions(verificationService);
    verifyNoInteractions(tourPassingRepository);

    UserApplicationProcessDto userApplicationProcess = new UserApplicationProcessDto();
    userApplicationProcess.setSteps(new Stack<>());
    userApplicationProcess.getSteps().push(formApplicationStep());
    userApplicationProcess.getSteps().push(formInterviewingStepWithStatusTodo());
    assertEquals(
        userApplicationProcess, userApplicationStepsCreator.getStepsByVacancyId(vacancyId));
  }

  @Test
  void
      getStepsByVacancyId_shouldReturnStepsOfApplicationWithStatusAcceptedAndVerificationWithStatusTodoAndInterviewingAndItsChildrenWithStatusesUntouched_whenUserAppliedOnVacancyAndVacancyHasFieldNeedsCitizenshipVerificationSetOnTrueAndVerificationHasNoOwnStatus() {
    UUID vacancyId = UUID.randomUUID();
    UUID currentUserKeycloakId = UUID.randomUUID();

    when(keycloakService.getKeycloakIdOfUserFromSecurityContext())
        .thenReturn(currentUserKeycloakId);

    when(userApplicationService.isUserApplicationExistByVacancyIdAndKeycloakId(
            vacancyId, currentUserKeycloakId))
        .thenReturn(Boolean.TRUE);

    assertDoesNotThrow(() -> UserApplicationNotFoundException.class);

    VacancyDto vacancyFieldNeedsCitizenshipVerificationSetOnTrue = new VacancyDto();
    vacancyFieldNeedsCitizenshipVerificationSetOnTrue.setNeedsCitizenshipVerification(Boolean.TRUE);
    when(vacancyService.getById(vacancyId))
        .thenReturn(vacancyFieldNeedsCitizenshipVerificationSetOnTrue);

    VerificationDto verificationWithNoOwnStatus = new VerificationDto();
    when(verificationService.getVerificationByKeycloakId(currentUserKeycloakId.toString()))
        .thenReturn(verificationWithNoOwnStatus);

    verifyNoInteractions(tourPassingRepository);

    UserApplicationProcessDto userApplicationProcess = new UserApplicationProcessDto();
    userApplicationProcess.setSteps(new Stack<>());
    userApplicationProcess.getSteps().push(formApplicationStep());
    userApplicationProcess.getSteps().push(formVerificationStepWithStatusTodo());
    userApplicationProcess.getSteps().push(formInterviewingStepWithStatusUntouched());
    assertEquals(
        userApplicationProcess, userApplicationStepsCreator.getStepsByVacancyId(vacancyId));
  }

  @Test
  void
      getStepsByVacancyId_shouldReturnStepsOfApplicationWithStatusAcceptedAndVerificationWithStatusProcessingAndInterviewingAndItsChildrenWithStatusesUntouched_whenUserAppliedOnVacancyAndVacancyHasFieldNeedsCitizenshipVerificationSetOnTrueAndVerificationOwnStatusIsPending() {
    UUID vacancyId = UUID.randomUUID();
    UUID currentUserKeycloakId = UUID.randomUUID();

    when(keycloakService.getKeycloakIdOfUserFromSecurityContext())
        .thenReturn(currentUserKeycloakId);

    when(userApplicationService.isUserApplicationExistByVacancyIdAndKeycloakId(
            vacancyId, currentUserKeycloakId))
        .thenReturn(Boolean.TRUE);

    assertDoesNotThrow(() -> UserApplicationNotFoundException.class);

    VacancyDto vacancyFieldNeedsCitizenshipVerificationSetOnTrue = new VacancyDto();
    vacancyFieldNeedsCitizenshipVerificationSetOnTrue.setNeedsCitizenshipVerification(Boolean.TRUE);
    when(vacancyService.getById(vacancyId))
        .thenReturn(vacancyFieldNeedsCitizenshipVerificationSetOnTrue);

    VerificationDto verificationWithOwnStatusPending = new VerificationDto();
    verificationWithOwnStatusPending.setVerificationState(VerificationState.PENDING);
    when(verificationService.getVerificationByKeycloakId(currentUserKeycloakId.toString()))
        .thenReturn(verificationWithOwnStatusPending);

    verifyNoInteractions(tourPassingRepository);

    UserApplicationProcessDto userApplicationProcess = new UserApplicationProcessDto();
    userApplicationProcess.setSteps(new Stack<>());
    userApplicationProcess.getSteps().push(formApplicationStep());
    userApplicationProcess.getSteps().push(formVerificationStepWithStatusProgressing());
    userApplicationProcess.getSteps().push(formInterviewingStepWithStatusUntouched());
    assertEquals(
        userApplicationProcess, userApplicationStepsCreator.getStepsByVacancyId(vacancyId));
  }
}
