package ua.com.hiringservice.service.task.impl;

import static ua.com.hiringservice.util.validations.QuestionValidation.validateTaskDto;
import static ua.com.hiringservice.util.validations.TaskValidation.throwIfPassingScoreIsGreaterThanMaxScore;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.hiringservice.exception.TaskNotFoundException;
import ua.com.hiringservice.model.dto.task.PracticalTaskImageDto;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.repository.QuestionRepository;
import ua.com.hiringservice.repository.TaskPassingRepository;
import ua.com.hiringservice.repository.TaskRepository;
import ua.com.hiringservice.service.task.PracticalTaskImageService;
import ua.com.hiringservice.service.task.TaskQuestionService;
import ua.com.hiringservice.service.task.TaskService;
import ua.com.hiringservice.util.mapper.task.TaskMapper;

/**
 * Implementation of the {@link TaskService} interface providing operations for managing taskzes and
 * associated questions.
 *
 * @author Oleksandr Prokopenko
 * @version 1.2
 * @since 2024-01-04
 */
@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {

  private final TaskRepository taskRepository;
  private final QuestionRepository questionRepository;
  private final TaskPassingRepository taskPassingRepository;
  private final TaskMapper taskMapper;
  private final TaskQuestionService taskQuestionService;
  private final PracticalTaskImageService imageService;

  @Override
  public TaskDto createTask(TaskDto taskDto) {
    validateTaskDto(taskDto, questionRepository);

    final Task task = taskMapper.toEntity(taskDto);
    // its hotfix according to bug where in post method we put existing ID, and we update existing
    // task todo needed to solve this issue more correctly and clearly
    task.setId(null);

    taskQuestionService.fetchQuestionsFromDbTo(task);

    return taskMapper.toDto(taskRepository.save(task));
  }

  @Override
  public TaskDto findById(UUID id) {
    final Task task = findTaskById(id);
    return toDtoWithImages(task);
  }

  private TaskDto toDtoWithImages(Task task) {
    final TaskDto taskDto = taskMapper.toDto(task);
    if (taskDto.getPracticalTaskDto() != null) {
      final List<PracticalTaskImageDto> diagramDtos = imageService.findAllImagesByIds(task);
      taskDto.getPracticalTaskDto().setImageDtos(diagramDtos);
    }
    return taskDto;
  }

  @Override
  public Page<TaskDto> findAll(Pageable pageable) {
    return taskRepository.findAll(pageable).map(this::toDtoWithImages);
  }

  @Override
  @Transactional
  public TaskDto updateTask(UUID taskId, TaskDto taskDto) {
    throwIfPassingScoreIsGreaterThanMaxScore(taskDto);

    final Task task = findTaskById(taskId);

    taskMapper.updateEntityFromDto(task, taskDto);

    return taskMapper.toDto(taskRepository.save(task));
  }

  @Override
  public void deleteTaskById(final UUID id) {
    final List<TaskPassing> taskPassingList = taskPassingRepository.findAllByTaskId(id);
    taskPassingList.forEach(taskPassing -> taskPassing.setTask(null));
    taskPassingRepository.saveAll(taskPassingList);

    taskRepository.deleteById(id);
  }

  @Override
  public TaskDto publish(UUID taskId) {
    final Task taskById = findTaskById(taskId);

    taskById.setPublished(true);
    taskById.setPublishedAt(ZonedDateTime.now());

    return taskMapper.toDto(taskRepository.save(taskById));
  }

  @Override
  public TaskDto unPublish(UUID taskId) {
    final Task taskById = findTaskById(taskId);

    taskById.setPublished(false);
    taskById.setPublishedAt(null);

    return taskMapper.toDto(taskRepository.save(taskById));
  }

  @Override
  public TaskDto getTaskForPassing(final UUID taskId) {
    final Task task = findTaskById(taskId);
    final TaskDto taskDto = taskMapper.toDto(task);
    taskDto.setTaskQuestionDtos(null);

    return taskDto;
  }

  @Override
  public Task findTaskById(UUID taskId) {
    return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
  }
}
