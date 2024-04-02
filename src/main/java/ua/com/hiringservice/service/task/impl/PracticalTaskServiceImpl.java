package ua.com.hiringservice.service.task.impl;

import static ua.com.hiringservice.util.validations.TaskValidation.throwIfTaskIsAlreadyPublished;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.hiringservice.exception.CannotAddPracticalTaskToQuizException;
import ua.com.hiringservice.exception.CannotRemovePracticalTaskFromQuizException;
import ua.com.hiringservice.exception.PracticalTaskNotFoundException;
import ua.com.hiringservice.model.dto.task.PracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskImageDto;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.entity.task.PracticalTask;
import ua.com.hiringservice.model.entity.task.PracticalTaskImage;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.enums.exam.TaskType;
import ua.com.hiringservice.repository.PracticalTaskRepository;
import ua.com.hiringservice.repository.TaskRepository;
import ua.com.hiringservice.service.task.PracticalTaskImageService;
import ua.com.hiringservice.service.task.PracticalTaskService;
import ua.com.hiringservice.service.task.TaskService;
import ua.com.hiringservice.util.mapper.task.PracticalTaskImageMapper;
import ua.com.hiringservice.util.mapper.task.PracticalTaskMapper;
import ua.com.hiringservice.util.mapper.task.TaskMapper;

/**
 * Implementation of the {@link PracticalTaskService} interface. Performs CRUD operations over
 * practical task. Also manages images, stored in mongo db. Images are simple pictures as addition
 * to practical task description.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@RequiredArgsConstructor
@Service
public class PracticalTaskServiceImpl implements PracticalTaskService {
  private final PracticalTaskRepository practicalTaskRepository;
  private final PracticalTaskMapper practicalTaskMapper;
  private final PracticalTaskImageMapper practicalTaskImageMapper;
  private final PracticalTaskImageService imageService;
  private final TaskService taskService;
  private final TaskRepository taskRepository;
  private final TaskMapper taskMapper;

  @Override
  public Page<PracticalTaskDto> findAll(Pageable pageable) {
    return practicalTaskRepository.findAll(pageable).map(this::toDtoWithImages);
  }

  @Transactional
  @Override
  public PracticalTaskDto save(PracticalTaskDto practicalTaskDto) {
    final List<PracticalTaskImage> savedImages =
        imageService.savePracticalTaskImages(practicalTaskDto);
    // set saved images to practical task and save it
    final PracticalTask practicalTask =
        practicalTaskMapper.toEntityWithImageIds(practicalTaskDto, savedImages);
    final PracticalTask saved = practicalTaskRepository.save(practicalTask);

    return toDtoWithImages(saved);
  }

  @Transactional
  @Override
  public PracticalTaskDto update(UUID practicalTaskId, PracticalTaskDto practicalTaskDto) {
    practicalTaskDto.setId(practicalTaskId);
    final PracticalTask practicalTask = findEntityById(practicalTaskId);

    imageService.deleteImagesByIds(practicalTask);

    imageService.saveAllImagesByIds(practicalTaskDto);

    final PracticalTask entity = practicalTaskMapper.toEntity(practicalTaskDto);
    return toDtoWithImages(practicalTaskRepository.save(entity));
  }

  @Override
  public void deleteById(UUID practicalTaskId) {
    final PracticalTask practicalTask = findEntityById(practicalTaskId);
    imageService.deleteImagesByIds(practicalTask);
    practicalTaskRepository.delete(practicalTask);
  }

  @Override
  public PracticalTaskDto findById(UUID practicalTaskId) {
    final PracticalTask practicalTask = findEntityById(practicalTaskId);
    return toDtoWithImages(practicalTask);
  }

  @Override
  @Transactional
  public TaskDto addPracticalTaskToTask(UUID taskId, UUID practicalTaskId) {
    final PracticalTask practicalTask = findEntityById(practicalTaskId);
    final Task task = taskService.findTaskById(taskId);

    throwIfTaskIsAlreadyPublished(task);

    final Task updatedTask;
    if (task.getType().equals(TaskType.PRACTICAL_TASK)) {
      task.setPracticalTask(practicalTask);
      updatedTask = taskRepository.save(task);
    } else {
      throw new CannotAddPracticalTaskToQuizException(taskId, practicalTaskId);
    }
    final TaskDto taskDto = taskMapper.toDto(updatedTask);
    final PracticalTaskDto practicalTaskDto = toDtoWithImages(practicalTask);
    taskDto.setPracticalTaskDto(practicalTaskDto);
    return taskDto;
  }

  @Override
  public TaskDto removePracticalTaskFromTask(UUID taskId) {
    final Task task = taskService.findTaskById(taskId);

    throwIfTaskIsAlreadyPublished(task);

    final Task updatedTask;
    if (task.getType().equals(TaskType.PRACTICAL_TASK)) {
      task.setPracticalTask(null);
      updatedTask = taskRepository.save(task);
    } else {
      throw new CannotRemovePracticalTaskFromQuizException(taskId);
    }
    return taskMapper.toDto(updatedTask);
  }

  private PracticalTask findEntityById(UUID practicalTaskId) {
    return practicalTaskRepository
        .findById(practicalTaskId)
        .orElseThrow(() -> new PracticalTaskNotFoundException(practicalTaskId));
  }

  private PracticalTaskDto toDtoWithImages(PracticalTask entity) {
    final List<PracticalTaskImageDto> imageDtos =
        imageService.findAllImagesByIds(entity).stream()
            .map(practicalTaskImageMapper::toDto)
            .toList();
    return practicalTaskMapper.toDto(entity, imageDtos);
  }
}
