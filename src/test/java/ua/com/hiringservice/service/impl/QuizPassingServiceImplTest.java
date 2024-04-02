package ua.com.hiringservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.com.testsupport.datagenerator.AnswerDataGenerator.getDto;
import static ua.com.testsupport.datagenerator.KeycloakDataGenerator.generateKeycloakUserDto;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateNotPublishedTask;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generatePublishedTask;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateSequence;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateTaskId;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateTaskPassingInProgress;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateTaskPassingWithAnswers;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateTaskPassingWithoutAnswers;
import static ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData.getJsonMatchInstance;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.com.hiringservice.exception.AnswerInTaskPassingNotFoundException;
import ua.com.hiringservice.exception.TaskNotPublishedException;
import ua.com.hiringservice.exception.TaskPassingNotFoundException;
import ua.com.hiringservice.exception.UserAlreadyPassingTaskException;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.content.questioncontent.SingleChoiceTestQuestionContent;
import ua.com.hiringservice.model.dto.KeycloakUserDto;
import ua.com.hiringservice.model.dto.task.AnswerDto;
import ua.com.hiringservice.model.entity.task.Answer;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.repository.TaskPassingRepository;
import ua.com.hiringservice.repository.TaskRepository;
import ua.com.hiringservice.service.KeycloakService;
import ua.com.hiringservice.service.task.AnswerService;
import ua.com.hiringservice.service.task.AssessmentService;
import ua.com.hiringservice.service.task.TaskService;
import ua.com.hiringservice.service.task.impl.QuizPassingServiceImpl;
import ua.com.hiringservice.util.mapper.task.AnswerMapper;
import ua.com.hiringservice.util.mapper.task.TaskMapper;
import ua.com.hiringservice.util.mapper.task.TaskPassingMapper;

/** Replace this stub by correct Javadoc. */
@SpringBootTest(
    classes = {
      QuizPassingServiceImpl.class,
    })
@SuppressWarnings("PMD.LinguisticNaming")
class QuizPassingServiceImplTest {

  @MockBean private AnswerService answerService;
  @MockBean private TaskRepository taskRepository;
  @MockBean private TaskPassingRepository taskPassingRepository;
  @MockBean private TaskPassingMapper taskPassingMapper;
  @MockBean private TaskMapper taskMapper;
  @MockBean private AssessmentService assessmentService;
  @MockBean private AnswerMapper answerMapper;
  @MockBean private KeycloakService keycloakService;
  @MockBean private TaskService taskService;

  @Autowired private QuizPassingServiceImpl quizPassingService;

  @Test
  void startQuizPassing_shouldReturnException_whenUserPassAnotherQuizNow() {
    final KeycloakUserDto userDto = generateKeycloakUserDto();
    final UUID userKeycloakId = UUID.fromString(userDto.getUserId());
    final UUID quizId = generateTaskId();

    when(keycloakService.getKeycloakUserInfoFromSecurityContext()).thenReturn(userDto);
    when(taskPassingRepository.isUserPassTaskNow(userKeycloakId)).thenReturn(true);

    assertThrows(
        UserAlreadyPassingTaskException.class,
        () -> quizPassingService.startQuizPassing(quizId),
        "Expected UserAlreadyPassingQuizException when the user is already passing another quiz.");
  }

  @Test
  void startQuizPassing_shouldReturnException_whenQuizNotPublished() {
    final KeycloakUserDto userDto = generateKeycloakUserDto();
    final UUID userKeycloakId = UUID.fromString(userDto.getUserId());
    final UUID quizId = generateTaskId();
    final Task task = generateNotPublishedTask();

    when(keycloakService.getKeycloakUserInfoFromSecurityContext()).thenReturn(userDto);
    when(taskPassingRepository.isUserPassTaskNow(userKeycloakId)).thenReturn(false);
    when(taskService.findTaskById(quizId)).thenReturn(task);

    assertThrows(
        TaskNotPublishedException.class,
        () -> quizPassingService.startQuizPassing(quizId),
        "Expected QuizNotPublishedException when attempting to start an unpublished quiz.");
  }

  @Test
  void getAnswerByQuestion_shouldThrowException_whenQuizPassingNotFound() {
    final KeycloakUserDto userDto = generateKeycloakUserDto();
    final UUID userKeycloakId = UUID.fromString(userDto.getUserId());
    final UUID quizId = generateTaskId();
    final Integer sequence = generateSequence();

    when(keycloakService.getKeycloakUserInfoFromSecurityContext()).thenReturn(userDto);
    when(taskPassingRepository.findTaskPassingByTaskIdAndUserKeycloakIdAndStatus(
            quizId, userKeycloakId, PassingStatus.IN_PROGRESS))
        .thenReturn(Optional.empty());

    assertThrows(
        TaskPassingNotFoundException.class,
        () -> quizPassingService.getAnswerByQuestion(quizId, sequence),
        "Expected QuizPassingNotFoundException when quiz passing is not found.");
  }

  @Test
  void getAnswerByQuestion_throwsException_whenAnswerBySequenceNotFound() {
    final KeycloakUserDto userDto = generateKeycloakUserDto();
    final UUID userKeycloakId = UUID.fromString(userDto.getUserId());
    final UUID quizId = generateTaskId();
    final Integer sequence = generateSequence();
    final TaskPassing taskPassing = generateTaskPassingWithoutAnswers();

    when(keycloakService.getKeycloakUserInfoFromSecurityContext()).thenReturn(userDto);
    when(taskPassingRepository.findTaskPassingByTaskIdAndUserKeycloakIdAndStatus(
            quizId, userKeycloakId, PassingStatus.IN_PROGRESS))
        .thenReturn(Optional.of(taskPassing));

    assertThrows(
        AnswerInTaskPassingNotFoundException.class,
        () -> quizPassingService.getAnswerByQuestion(quizId, sequence),
        "Expected AnswerInQuizPassingNotFoundException when answer by sequence is not found.");
  }

  @Test
  void getAnswerByQuestion() {
    final KeycloakUserDto userDto = generateKeycloakUserDto();
    final UUID userKeycloakId = UUID.fromString(userDto.getUserId());
    final UUID quizId = generateTaskId();
    final Integer sequence = generateSequence();
    final TaskPassing taskPassing = generateTaskPassingWithAnswers();
    final AnswerDto answerDto = getDto();

    when(keycloakService.getKeycloakUserInfoFromSecurityContext()).thenReturn(userDto);
    when(taskPassingRepository.findTaskPassingByTaskIdAndUserKeycloakIdAndStatus(
            quizId, userKeycloakId, PassingStatus.IN_PROGRESS))
        .thenReturn(Optional.of(taskPassing));
    when(answerMapper.toUserDto(any(Answer.class))).thenReturn(answerDto);

    assertNotNull(
        quizPassingService.getAnswerByQuestion(quizId, sequence), "Expected a non-null AnswerDto.");
  }

  @Test
  void finishQuizPassing() {
    final KeycloakUserDto userDto = generateKeycloakUserDto();
    final UUID userKeycloakId = UUID.fromString(userDto.getUserId());
    final UUID quizId = generateTaskId();
    final TaskPassing taskPassingInProgress = generateTaskPassingInProgress();

    when(keycloakService.getKeycloakUserInfoFromSecurityContext()).thenReturn(userDto);
    when(taskPassingRepository.findTaskPassingByTaskIdAndUserKeycloakIdAndStatus(
            quizId, userKeycloakId, PassingStatus.IN_PROGRESS))
        .thenReturn(Optional.of(taskPassingInProgress));

    quizPassingService.finishQuizPassing(quizId);

    verify(taskPassingRepository).save(any(TaskPassing.class));
  }

  @Test
  void assessQuizPassing_whenFullyAutoAssessable() {
    final Task task = generatePublishedTask();
    final TaskPassing mockTaskPassing = mock(TaskPassing.class);
    final ArrayList<Answer> answers = new ArrayList<>();

    answers.add(
        Answer.builder()
            .status(PassingStatus.GRADED)
            .content(new SingleChoiceTestQuestionContent())
            .build());

    when(mockTaskPassing.getTask()).thenReturn(task);
    when(mockTaskPassing.getAnswers()).thenReturn(answers);

    final Boolean result = quizPassingService.assessTaskPassing(mockTaskPassing);

    verify(assessmentService, times(1)).assessAnswerIfAutoAssessable(any(Answer.class));
    verify(mockTaskPassing).setStatus(PassingStatus.GRADED);
    assertEquals(true, result, "assessQuizPassing should return true");
  }

  @Test
  void assessQuizPassing_whenNotFullyAutoAssessable() {
    final Task task = generatePublishedTask();
    final TaskPassing mockTaskPassing = mock(TaskPassing.class);
    final ArrayList<Answer> answers = new ArrayList<>();
    final Content content = getJsonMatchInstance();

    answers.add(Answer.builder().content(content).build());
    answers.add(Answer.builder().content(content).build());
    answers.add(Answer.builder().content(content).build());

    when(mockTaskPassing.getTask()).thenReturn(task);
    when(mockTaskPassing.getAnswers()).thenReturn(answers);

    final Boolean result = quizPassingService.assessTaskPassing(mockTaskPassing);

    verify(assessmentService, times(3)).assessAnswerIfAutoAssessable(any(Answer.class));
    verify(mockTaskPassing, times(0)).setScore(any());
    verify(mockTaskPassing, times(0)).setStatus(any(PassingStatus.class));
    assertEquals(false, result, "assessQuizPassing should return false");
  }
}
