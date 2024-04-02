package ua.com.hiringservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generateImageDtos;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generateImages;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generateListPracticalTasks;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generateListPracticalTasksDto;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generatePracticalTask;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generatePracticalTaskDto;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generatePracticalTaskId;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTask4DtoWithPracticalTask;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTask4Id;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTask4WithPracticalTask;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.getEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.hiringservice.exception.CannotRemovePracticalTaskFromQuizException;
import ua.com.hiringservice.exception.TaskAlreadyPublishedException;
import ua.com.hiringservice.model.dto.task.PracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskImageDto;
import ua.com.hiringservice.model.dto.task.TaskDto;
import ua.com.hiringservice.model.entity.task.PracticalTask;
import ua.com.hiringservice.model.entity.task.PracticalTaskImage;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.repository.PracticalTaskRepository;
import ua.com.hiringservice.repository.TaskRepository;
import ua.com.hiringservice.service.task.PracticalTaskImageService;
import ua.com.hiringservice.service.task.TaskService;
import ua.com.hiringservice.service.task.impl.PracticalTaskServiceImpl;
import ua.com.hiringservice.util.mapper.task.PracticalTaskImageMapper;
import ua.com.hiringservice.util.mapper.task.PracticalTaskMapper;
import ua.com.hiringservice.util.mapper.task.TaskMapper;

/**
 * This class contains unit tests for the PracticalTaskServiceImpl class.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PracticalTaskServiceImplTest {

  public static final String EXPECTED_NOT_MATCH_ACTUAL = "Expected doesn't match actual";
  @Mock private PracticalTaskRepository practicalTaskRepository;
  @Mock private PracticalTaskMapper practicalTaskMapper;
  @Mock private PracticalTaskImageMapper practicalTaskImageMapper;
  @Mock private PracticalTaskImageService imageService;
  @Mock private TaskService taskService;
  @Mock private TaskRepository taskRepository;
  @Mock private TaskMapper taskMapper;

  @InjectMocks private PracticalTaskServiceImpl underTest;

  @Test
  void findAll_shouldReturnsPageOfPracticalTaskDto() {
    // Arrange
    final Pageable pageable = PageRequest.of(0, 10); // Example pageable
    final List<PracticalTask> mockTasks = generateListPracticalTasks(); // Create mock tasks
    final List<PracticalTaskDto> mockTasksDto = generateListPracticalTasksDto();
    final List<PracticalTaskImage> images = generateImages();
    final List<PracticalTaskImageDto> imageDtos = generateImageDtos();
    // Mock the findAll method of the repository to return a page of mock tasks
    final Page<PracticalTask> mockPage = new PageImpl<>(mockTasks, pageable, mockTasks.size());
    when(practicalTaskRepository.findAll(pageable)).thenReturn(mockPage);

    when(imageService.findAllImagesByIds(mockTasks.get(0))).thenReturn(images);
    when(imageService.findAllImagesByIds(mockTasks.get(1))).thenReturn(images);
    when(imageService.findAllImagesByIds(mockTasks.get(2))).thenReturn(images);

    when(practicalTaskImageMapper.toDto(images.get(0))).thenReturn(imageDtos.get(0));
    when(practicalTaskImageMapper.toDto(images.get(1))).thenReturn(imageDtos.get(1));
    when(practicalTaskImageMapper.toDto(images.get(2))).thenReturn(imageDtos.get(2));

    when(practicalTaskMapper.toDto(mockTasks.get(0), imageDtos)).thenReturn(mockTasksDto.get(0));
    when(practicalTaskMapper.toDto(mockTasks.get(1), imageDtos)).thenReturn(mockTasksDto.get(1));
    when(practicalTaskMapper.toDto(mockTasks.get(2), imageDtos)).thenReturn(mockTasksDto.get(2));

    // Act
    final Page<PracticalTaskDto> result = underTest.findAll(pageable);

    // Assert
    assertEquals(
        mockPage.getTotalElements(),
        result.getTotalElements(),
        "Total elements on page dont match expected"); // Check if total elements match
    assertEquals(
        mockTasks.size(),
        result.getContent().size(),
        "Total page size dont match expected"); // Check if content size matches
    assertEquals(mockTasksDto, result.getContent(), EXPECTED_NOT_MATCH_ACTUAL);
  }

  @Test
  void save_shouldReturnPracticalTaskDto() {
    // Arrange
    final PracticalTaskDto practicalTaskDto =
        generatePracticalTaskDto(); // Create a mock PracticalTaskDto
    practicalTaskDto.setId(null);
    final PracticalTaskDto expected = generatePracticalTaskDto(); // Create a mock PracticalTaskDto
    final List<PracticalTaskImage> savedImages = generateImages(); // Create a list of saved images
    final List<PracticalTaskImageDto> imageDtos = generateImageDtos();
    final PracticalTask practicalTask = generatePracticalTask(); // Create a mock PracticalTask
    practicalTask.setId(null);
    final PracticalTask savedPracticalTask = generatePracticalTask(); // Create a mock PracticalTask

    when(imageService.savePracticalTaskImages(practicalTaskDto))
        .thenReturn(savedImages); // Mock the imageService call
    when(practicalTaskMapper.toEntityWithImageIds(practicalTaskDto, savedImages))
        .thenReturn(practicalTask); // Mock the practicalTaskMapper call
    when(practicalTaskRepository.save(practicalTask))
        .thenReturn(savedPracticalTask); // Mock the practicalTaskRepository call
    // Mock the toDtoWithImages method to return the expected result

    when(imageService.findAllImagesByIds(savedPracticalTask)).thenReturn(savedImages);
    when(practicalTaskImageMapper.toDto(savedImages.get(0))).thenReturn(imageDtos.get(0));
    when(practicalTaskImageMapper.toDto(savedImages.get(1))).thenReturn(imageDtos.get(1));
    when(practicalTaskImageMapper.toDto(savedImages.get(2))).thenReturn(imageDtos.get(2));

    when(practicalTaskMapper.toDto(savedPracticalTask, imageDtos)).thenReturn(expected);

    // Act
    final PracticalTaskDto result = underTest.save(practicalTaskDto);

    // Assert
    assertEquals(
        expected,
        result,
        EXPECTED_NOT_MATCH_ACTUAL); // Check if the returned PracticalTaskDto matches the expected
  }

  @Test
  void update_shouldReturnsUpdatedPracticalTaskDto() {
    // Arrange
    final UUID practicalTaskId = generatePracticalTaskId(); // Create a mock UUID
    final PracticalTaskDto practicalTaskDto =
        generatePracticalTaskDto(); // Create a mock PracticalTaskDto
    practicalTaskDto.setId(null);
    final PracticalTask practicalTask = generatePracticalTask(); // Create a mock PracticalTask
    practicalTask.setId(null);

    when(practicalTaskRepository.findById(practicalTaskId))
        .thenReturn(Optional.of(practicalTask)); // Mock the practicalTaskRepository call

    // Mock the imageService calls
    doNothing().when(imageService).deleteImagesByIds(practicalTask);
    doNothing().when(imageService).saveAllImagesByIds(practicalTaskDto);

    // Mock the toDtoWithImages method to return the expected result
    final PracticalTaskDto expected = generatePracticalTaskDto();
    final PracticalTask savedPracticalTask = generatePracticalTask(); // Create a mock PracticalTask

    when(practicalTaskMapper.toEntity(practicalTaskDto)).thenReturn(practicalTask);

    when(practicalTaskRepository.save(practicalTask)).thenReturn(savedPracticalTask);

    final List<PracticalTaskImage> savedImages = generateImages(); // Create a list of saved images
    final List<PracticalTaskImageDto> imageDtos = generateImageDtos();
    when(imageService.findAllImagesByIds(savedPracticalTask)).thenReturn(savedImages);
    when(practicalTaskImageMapper.toDto(savedImages.get(0))).thenReturn(imageDtos.get(0));
    when(practicalTaskImageMapper.toDto(savedImages.get(1))).thenReturn(imageDtos.get(1));
    when(practicalTaskImageMapper.toDto(savedImages.get(2))).thenReturn(imageDtos.get(2));

    when(practicalTaskMapper.toDto(savedPracticalTask, imageDtos)).thenReturn(expected);

    // Act
    final PracticalTaskDto result = underTest.update(practicalTaskId, practicalTaskDto);

    // Assert
    assertEquals(
        expected,
        result,
        EXPECTED_NOT_MATCH_ACTUAL); // Check if the returned PracticalTaskDto matches the expected
  }

  @Test
  void deleteById_shouldDeletesPracticalTaskAndImages() {
    // Arrange
    final UUID practicalTaskId = generatePracticalTaskId(); // Create a mock UUID
    final PracticalTask practicalTask = generatePracticalTask(); // Create a mock PracticalTask

    when(practicalTaskRepository.findById(practicalTaskId))
        .thenReturn(Optional.of(practicalTask)); // Mock the practicalTaskRepository call

    // Act
    assertDoesNotThrow(
        () -> underTest.deleteById(practicalTaskId), "Method throws unexpected exception");

    // Assert
    verify(imageService).deleteImagesByIds(practicalTask);
    // Verify that deleteImagesByIds was called with the correct PracticalTask
    verify(practicalTaskRepository)
        .delete(practicalTask); // Verify that delete was called with the correct PracticalTask
  }

  @Test
  void findById_shouldReturnPracticalTaskDto() {
    // Arrange
    final UUID practicalTaskId = generatePracticalTaskId(); // Create a mock UUID
    final PracticalTask practicalTask = generatePracticalTask(); // Create a mock PracticalTask
    final PracticalTaskDto expectedDto =
        generatePracticalTaskDto(); // Create a mock PracticalTaskDto

    when(practicalTaskRepository.findById(practicalTaskId))
        .thenReturn(Optional.of(practicalTask)); // Mock the practicalTaskRepository call

    // Mock the toDtoWithImages method to return the expected result
    final PracticalTaskDto expected = generatePracticalTaskDto();
    final PracticalTask savedPracticalTask = generatePracticalTask(); // Create a mock PracticalTask
    final List<PracticalTaskImage> savedImages = generateImages(); // Create a list of saved images
    final List<PracticalTaskImageDto> imageDtos = generateImageDtos();
    when(imageService.findAllImagesByIds(savedPracticalTask)).thenReturn(savedImages);
    when(practicalTaskImageMapper.toDto(savedImages.get(0))).thenReturn(imageDtos.get(0));
    when(practicalTaskImageMapper.toDto(savedImages.get(1))).thenReturn(imageDtos.get(1));
    when(practicalTaskImageMapper.toDto(savedImages.get(2))).thenReturn(imageDtos.get(2));

    when(practicalTaskMapper.toDto(savedPracticalTask, imageDtos)).thenReturn(expected);

    // Act
    final PracticalTaskDto actualDto = underTest.findById(practicalTaskId);

    // Assert
    assertEquals(
        expectedDto,
        actualDto,
        EXPECTED_NOT_MATCH_ACTUAL); // Verify that the returned PracticalTaskDto is the expected one
    verify(practicalTaskRepository)
        .findById(practicalTaskId); // Verify that findById was called with the correct UUID
  }

  @Test
  void addPracticalTaskToTask_shouldAddPracticalTask_whenTaskIsNotPublished() {
    // Arrange
    final UUID taskId = generateTask4Id();
    final UUID practicalTaskId = generatePracticalTaskId();
    final Task task = generateTask4WithPracticalTask();
    task.setPublished(false);
    task.setPracticalTask(null);
    final Task updatedTask = generateTask4WithPracticalTask();
    updatedTask.setPublished(false);
    final PracticalTask practicalTask = generatePracticalTask();
    final TaskDto expectedTaskDto = generateTask4DtoWithPracticalTask();
    final PracticalTaskDto expectedPracticalTaskDto = generatePracticalTaskDto();

    when(practicalTaskRepository.findById(practicalTaskId)).thenReturn(Optional.of(practicalTask));
    when(taskService.findTaskById(taskId)).thenReturn(task);

    when(taskRepository.save(task)).thenReturn(updatedTask);
    when(taskMapper.toDto(updatedTask)).thenReturn(expectedTaskDto);

    // Mock the toDtoWithImages method to return the expected result
    final PracticalTask savedPracticalTask = generatePracticalTask(); // Create a mock PracticalTask
    final List<PracticalTaskImage> savedImages = generateImages(); // Create a list of saved images
    final List<PracticalTaskImageDto> imageDtos = generateImageDtos();
    when(imageService.findAllImagesByIds(savedPracticalTask)).thenReturn(savedImages);
    when(practicalTaskImageMapper.toDto(savedImages.get(0))).thenReturn(imageDtos.get(0));
    when(practicalTaskImageMapper.toDto(savedImages.get(1))).thenReturn(imageDtos.get(1));
    when(practicalTaskImageMapper.toDto(savedImages.get(2))).thenReturn(imageDtos.get(2));

    when(practicalTaskMapper.toDto(savedPracticalTask, imageDtos))
        .thenReturn(expectedPracticalTaskDto);

    // Act
    final TaskDto actualTaskDto = underTest.addPracticalTaskToTask(taskId, practicalTaskId);

    // Assert
    assertEquals(expectedTaskDto, actualTaskDto, EXPECTED_NOT_MATCH_ACTUAL);
    assertEquals(
        expectedPracticalTaskDto, actualTaskDto.getPracticalTaskDto(), EXPECTED_NOT_MATCH_ACTUAL);
    verify(taskService).findTaskById(taskId);
    verify(practicalTaskRepository).findById(practicalTaskId);
    verify(taskRepository).save(task);
    verify(taskMapper).toDto(task);
  }

  @Test
  void removePracticalTaskFromTask_shouldRemovePracticalTask_whenTaskIsNotPublished() {
    // Arrange
    final UUID taskId = generateTask4Id();
    final Task task = generateTask4WithPracticalTask();
    task.setPublished(false);
    final Task updatedTask = generateTask4WithPracticalTask();
    updatedTask.setPublished(false);
    updatedTask.setPracticalTask(null);

    final TaskDto expectedTaskDto = generateTask4DtoWithPracticalTask();
    expectedTaskDto.setPublished(false);
    expectedTaskDto.setPracticalTaskDto(null);

    when(taskService.findTaskById(taskId)).thenReturn(task);
    when(taskRepository.save(task)).thenReturn(updatedTask);

    when(taskMapper.toDto(updatedTask)).thenReturn(expectedTaskDto);

    // Act
    final TaskDto actualTaskDto = underTest.removePracticalTaskFromTask(taskId);

    // Assert
    assertEquals(expectedTaskDto, actualTaskDto, EXPECTED_NOT_MATCH_ACTUAL);
    assertNull(actualTaskDto.getPracticalTaskDto(), "Practical task is not null after removal");
    verify(taskService).findTaskById(taskId);
    verify(taskRepository).save(task);
    verify(taskMapper).toDto(updatedTask);
  }

  @Test
  void removePracticalTaskFromTask_shouldThrowException_whenTaskIsPublished() {
    // Arrange
    final UUID taskId = generateTask4Id();
    final Task task = generateTask4WithPracticalTask();

    when(taskService.findTaskById(taskId)).thenReturn(task);

    // Act and Assert
    assertThrows(
        TaskAlreadyPublishedException.class,
        () -> {
          underTest.removePracticalTaskFromTask(taskId);
        });
    verify(taskService).findTaskById(taskId);
    verifyNoInteractions(taskRepository, taskMapper);
  }

  @Test
  void removePracticalTaskFromTask_shouldThrowException_whenTaskIsNotPracticalTask() {
    // Arrange
    final UUID taskId = generateTask4Id();
    final Task task = getEntity();
    task.setPublished(false);
    when(taskService.findTaskById(taskId)).thenReturn(task);

    // Act and Assert
    assertThrows(
        CannotRemovePracticalTaskFromQuizException.class,
        () -> {
          underTest.removePracticalTaskFromTask(taskId);
        },
        "CannotRemovePracticalTaskFromQuizException wasn't thrown");
    verify(taskService).findTaskById(taskId);
    verifyNoInteractions(taskRepository, taskMapper);
  }
}
