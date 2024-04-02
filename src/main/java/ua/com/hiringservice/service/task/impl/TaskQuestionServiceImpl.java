package ua.com.hiringservice.service.task.impl;

import static ua.com.hiringservice.util.validations.TaskValidation.throwIfQuestionIsNotUnique;
import static ua.com.hiringservice.util.validations.TaskValidation.throwIfSequenceIsInvalid;
import static ua.com.hiringservice.util.validations.TaskValidation.throwIfTaskIsAlreadyPublished;
import static ua.com.hiringservice.util.validations.TaskValidation.throwIfTaskQuestionBelongsToOtherTask;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.hiringservice.exception.QuestionNotFoundException;
import ua.com.hiringservice.exception.TaskNotFoundException;
import ua.com.hiringservice.exception.TaskQuestionNotFoundException;
import ua.com.hiringservice.model.dto.task.QuestionDto;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.dto.task.TaskQuestionDto;
import ua.com.hiringservice.model.entity.task.Question;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskQuestion;
import ua.com.hiringservice.repository.QuestionRepository;
import ua.com.hiringservice.repository.TaskQuestionRepository;
import ua.com.hiringservice.repository.TaskRepository;
import ua.com.hiringservice.service.task.QuestionService;
import ua.com.hiringservice.service.task.TaskQuestionService;
import ua.com.hiringservice.util.TaskQuestionIndexManager;
import ua.com.hiringservice.util.mapper.task.QuestionMapper;
import ua.com.hiringservice.util.mapper.task.TaskMapper;
import ua.com.hiringservice.util.mapper.task.TaskQuestionMapper;

/** Service implementation for managing TaskQuestions within a Task. */
@Slf4j
@Service
@AllArgsConstructor
public class TaskQuestionServiceImpl implements TaskQuestionService {

  private final TaskRepository taskRepository;
  private final QuestionRepository questionRepository;
  private final TaskQuestionRepository taskQuestionRepository;
  private final TaskMapper taskMapper;
  private final QuestionMapper questionMapper;
  private final TaskQuestionMapper taskQuestionMapper;
  private final QuestionService questionService;

  @Override
  @Transactional
  public TaskDto addTaskQuestionToTask(UUID taskId, TaskQuestionDto taskQuestionDto) {
    final Task task = findTaskById(taskId);

    throwIfTaskIsAlreadyPublished(task);

    // we ignore passed indexInTask in DTO, and set it automatically
    taskQuestionDto.setIndexInTask(TaskQuestionIndexManager.getNextIndex(task));

    // if question not exists in db, save new question
    if (taskQuestionDto.getQuestionDto().getId() == null) {
      final QuestionDto newQuestion = questionService.save(taskQuestionDto.getQuestionDto());
      taskQuestionDto.setQuestionDto(newQuestion);
    }

    // check if there are no questions saved in db with new Question id (questions for Task should
    // be unique)
    throwIfQuestionIsNotUnique(task, taskQuestionDto);

    final TaskQuestion taskQuestion = taskQuestionMapper.toEntity(taskQuestionDto);

    task.getTaskQuestions().add(taskQuestion);

    // this is needed to recalculate totalDuration and totalQuestions
    fetchQuestionsFromDbTo(task);
    task.calculateDerivedFields();

    final List<TaskQuestionDto> taskQuestionDtos =
        task.getTaskQuestions().stream().map(taskQuestionMapper::toDto).toList();

    throwIfSequenceIsInvalid(taskQuestionDtos);

    return taskMapper.toDto(taskRepository.save(task));
  }

  @Override
  @Transactional
  public TaskDto deleteTaskQuestionFromTask(UUID taskId, UUID taskQuestionId) {
    final Task task = findTaskById(taskId);

    throwIfTaskIsAlreadyPublished(task);

    throwIfTaskQuestionBelongsToOtherTask(task, taskQuestionId);

    final TaskQuestion taskQuestion = findTaskQuestionById(taskQuestionId);

    task.getTaskQuestions().remove(taskQuestion);

    taskQuestionRepository.delete(taskQuestion);

    TaskQuestionIndexManager.organizeIndexesIn(task);

    // this is needed to recalculate totalDuration and totalQuestions
    fetchQuestionsFromDbTo(task);
    task.calculateDerivedFields();

    return taskMapper.toDto(taskRepository.save(task));
  }

  @Override
  @Transactional
  public TaskDto updateTaskQuestion(
      UUID taskId, UUID taskQuestionId, TaskQuestionDto taskQuestionDto) {

    final TaskQuestion taskQuestion = findTaskQuestionById(taskQuestionId);
    final UUID oldTaskQuestionId = taskQuestion.getId();
    final Question question = findQuestionById(taskQuestionDto.getQuestionDto().getId());
    taskQuestion.setQuestion(question);
    taskQuestionDto.setQuestionDto(questionMapper.toDto(question));

    if (taskQuestionDto.getId() == null || !taskQuestionDto.getId().equals(taskQuestionId)) {
      taskQuestionDto.setId(taskQuestionId);
    }

    taskQuestionDto.setIndexInTask(taskQuestion.getIndexInTask()); // ignore new indexInTask
    taskQuestionMapper.updateEntityFromDto(taskQuestion, taskQuestionDto);

    final Task task = findTaskById(taskId);

    throwIfTaskIsAlreadyPublished(task);

    final List<TaskQuestion> taskQuestions =
        task.getTaskQuestions().stream()
            .map(item -> item.getId().equals(oldTaskQuestionId) ? taskQuestion : item)
            .toList();
    task.setTaskQuestions(taskQuestions);

    final List<TaskQuestionDto> taskQuestionDtos =
        task.getTaskQuestions().stream().map(taskQuestionMapper::toDto).toList();

    throwIfSequenceIsInvalid(taskQuestionDtos);

    task.calculateDerivedFields();

    taskQuestionRepository.save(taskQuestion);

    return taskMapper.toDto(task);
  }

  private Task findTaskById(UUID taskId) {
    return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
  }

  private TaskQuestion findTaskQuestionById(UUID taskQuestionId) {
    return taskQuestionRepository
        .findById(taskQuestionId)
        .orElseThrow(() -> new TaskQuestionNotFoundException(taskQuestionId));
  }

  private Question findQuestionById(final UUID id) {
    return questionRepository.findById(id).orElseThrow(() -> new QuestionNotFoundException(id));
  }

  @Override
  public void fetchQuestionsFromDbTo(Task task) {
    // adding question`s data for ability calculate totalDuration
    final List<UUID> questionsIdList =
        task.getTaskQuestions().stream()
            .map(taskQuestion -> taskQuestion.getQuestion().getId())
            .toList();
    final List<Question> questions = questionRepository.findAllById(questionsIdList);
    for (int i = 0; i < questions.size(); i++) {
      task.getTaskQuestions().get(i).setQuestion(questions.get(i));
    }
  }
}
