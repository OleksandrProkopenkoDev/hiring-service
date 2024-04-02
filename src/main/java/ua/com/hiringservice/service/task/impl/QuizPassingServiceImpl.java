package ua.com.hiringservice.service.task.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.hiringservice.exception.AnswerInTaskPassingNotFoundException;
import ua.com.hiringservice.exception.IllegalTaskTypeException;
import ua.com.hiringservice.exception.TaskNotPublishedException;
import ua.com.hiringservice.exception.TaskPassingNotFoundException;
import ua.com.hiringservice.exception.UserAlreadyPassingTaskException;
import ua.com.hiringservice.model.dto.task.AnswerDto;
import ua.com.hiringservice.model.dto.task.QuizPassingResponseDto;
import ua.com.hiringservice.model.entity.task.Answer;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.model.entity.task.TaskQuestion;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.model.enums.exam.TaskType;
import ua.com.hiringservice.repository.TaskPassingRepository;
import ua.com.hiringservice.service.KeycloakService;
import ua.com.hiringservice.service.task.AssessmentService;
import ua.com.hiringservice.service.task.QuizPassingService;
import ua.com.hiringservice.service.task.TaskService;
import ua.com.hiringservice.util.mapper.task.AnswerMapper;
import ua.com.hiringservice.util.mapper.task.TaskPassingMapper;

/**
 * Service implementation for managing quiz passing operations.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@SuppressWarnings("PMD.ExcessiveImports")
@AllArgsConstructor
@Service
public class QuizPassingServiceImpl implements QuizPassingService {

  private final KeycloakService keycloakService;
  private final TaskService taskService;
  private final TaskPassingRepository taskPassingRepository;
  private final AnswerMapper answerMapper;
  private final AssessmentService assessmentService;
  private final TaskPassingMapper taskPassingMapper;

  @Override
  @Transactional
  public void startQuizPassing(UUID taskId) {

    final UUID userKeycloakId = getUserId();

    if (isUserPassTaskNow(userKeycloakId)) {
      throw new UserAlreadyPassingTaskException(userKeycloakId);
    }

    final Task task = taskService.findTaskById(taskId);

    if (!task.getType().equals(TaskType.QUIZ)) {
      throw new IllegalTaskTypeException(task.getType());
    }

    if (task.getPublished().equals(false)) {
      throw new TaskNotPublishedException(task.getId());
    }

    final TaskPassing taskPassing =
        TaskPassing.builder()
            .task(task)
            .userKeycloakId(userKeycloakId)
            .status(PassingStatus.IN_PROGRESS)
            .answers(new ArrayList<>())
            .build();

    taskPassing
        .getTask()
        .getTaskQuestions()
        .forEach(
            taskQuestion -> {
              taskQuestion
                  .getQuestion()
                  .getContent()
                  .setMaxGrade(taskPassing.getTask().getMaxScore());
              taskPassing.addAnswer(buildAnswer(taskQuestion));
            });

    taskPassingRepository.save(taskPassing);
  }

  @Override
  public AnswerDto getAnswerByQuestion(UUID taskId, Integer indexInTask) {
    final UUID userKeycloakId = getUserId();

    final TaskPassing taskPassing = findTaskPassing(userKeycloakId, taskId);

    return answerMapper.toUserDto(
        getAnswerFromTaskPassingByIndex(taskId, indexInTask, taskPassing));
  }

  @Override
  @Transactional
  public void finishQuizPassing(UUID taskId) {

    final UUID userKeycloakId = getUserId();

    final TaskPassing taskPassing = findTaskPassing(userKeycloakId, taskId);

    taskPassing.setStatus(PassingStatus.ANSWERED);
    taskPassing.getAnswers().forEach(answer -> answer.setStatus(PassingStatus.ANSWERED));
    assessTaskPassing(taskPassing);

    taskPassingRepository.save(taskPassing);
  }

  @Override
  public Page<QuizPassingResponseDto> getAssessedDoneTaskPassings(Pageable pageable) {
    final UUID userKeycloakId = getUserId();
    final Page<TaskPassing> taskPassings =
        taskPassingRepository.findAllByUserKeycloakIdAndStatus(
            userKeycloakId, PassingStatus.GRADED, pageable);
    return mapToQuizPassingResponseDtoPage(taskPassings);
  }

  @Override
  public Page<QuizPassingResponseDto> getAssessedDoneQuizPassingByTaskId(
      UUID userId, UUID taskId, Pageable pageable) {
    final UUID keycloakUserId = Objects.requireNonNullElseGet(userId, this::getUserId);

    final Page<TaskPassing> taskPassings =
        taskPassingRepository.findByUserKeycloakIdAndStatusAndTaskId(
            keycloakUserId, PassingStatus.GRADED, taskId, pageable);

    return mapToQuizPassingResponseDtoPage(taskPassings);
  }

  @Transactional
  public Boolean assessTaskPassing(TaskPassing taskPassing) {

    final List<Answer> answers = taskPassing.getAnswers();
    final Integer maxScore = taskPassing.getTask().getMaxScore();
    answers.forEach(
        answer -> {
          answer.getContent().setMaxGrade(maxScore);
          assessmentService.assessAnswerIfAutoAssessable(answer);
        });

    if (!getNotAssessedAnswers(answers).isEmpty()) {
      return false;
    }

    taskPassing.setStatus(PassingStatus.GRADED);
    return true;
  }

  private List<Answer> getNotAssessedAnswers(List<Answer> answers) {
    return answers.stream().filter(answer -> answer.getStatus() != PassingStatus.GRADED).toList();
  }

  private boolean isUserPassTaskNow(final UUID userKeycloakId) {
    return taskPassingRepository.isUserPassTaskNow(userKeycloakId);
  }

  private Answer buildAnswer(final TaskQuestion taskQuestion) {
    return Answer.builder()
        .duration(taskQuestion.getQuestion().getDuration())
        .status(PassingStatus.IN_PROGRESS)
        .content(taskQuestion.getQuestion().getContent())
        .indexInTask(taskQuestion.getIndexInTask())
        .weight(taskQuestion.getWeight())
        .build();
  }

  private TaskPassing findTaskPassing(UUID userKeycloakId, UUID taskId) {
    return taskPassingRepository
        .findTaskPassingByTaskIdAndUserKeycloakIdAndStatus(
            taskId, userKeycloakId, PassingStatus.IN_PROGRESS)
        .orElseThrow(
            () ->
                new TaskPassingNotFoundException(
                    userKeycloakId, taskId, PassingStatus.IN_PROGRESS));
  }

  private Answer getAnswerFromTaskPassingByIndex(
      UUID taskId, Integer sequence, TaskPassing taskPassing) {
    return taskPassing.getAnswers().stream()
        .filter(answer -> answer.getIndexInTask().equals(sequence))
        .findFirst()
        .orElseThrow(() -> new AnswerInTaskPassingNotFoundException(sequence, taskId));
  }

  @NotNull
  private UUID getUserId() {
    return UUID.fromString(keycloakService.getKeycloakUserInfoFromSecurityContext().getUserId());
  }

  @NotNull
  private Page<QuizPassingResponseDto> mapToQuizPassingResponseDtoPage(
      Page<TaskPassing> taskPassings) {

    return taskPassings
        .map(taskPassingMapper::toQuizPassingResponseDto)
        .filter(quizPassingResponseDto -> quizPassingResponseDto.getType().equals(TaskType.QUIZ))
        .stream()
        .collect(
            Collectors.collectingAndThen(
                Collectors.toList(),
                list ->
                    PageableExecutionUtils.getPage(
                        list, taskPassings.getPageable(), taskPassings::getTotalElements)));
  }
}
