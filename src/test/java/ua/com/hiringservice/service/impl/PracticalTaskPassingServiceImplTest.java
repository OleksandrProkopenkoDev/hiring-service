package ua.com.hiringservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static ua.com.hiringservice.service.impl.PracticalTaskServiceImplTest.EXPECTED_NOT_MATCH_ACTUAL;
import static ua.com.testsupport.datagenerator.KeycloakDataGenerator.generateKeycloakUserDto;
import static ua.com.testsupport.datagenerator.KeycloakDataGenerator.generateUserKeycloakIdData;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generatePracticalTaskPassingResponseDto;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateTaskPassingForPracticalTask;
import static ua.com.testsupport.datagenerator.task.PracticalTaskAnswerDataGenerator.generatePracticalTaskAnswer;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generateImageDtos;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTask4Id;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTask4WithPracticalTask;

import java.util.Collections;
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
import ua.com.hiringservice.model.dto.KeycloakUserDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskImageDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskPassingResponseDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskWithBytesDto;
import ua.com.hiringservice.model.entity.task.PracticalTaskAnswer;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.repository.PracticalTaskRepository;
import ua.com.hiringservice.repository.TaskPassingRepository;
import ua.com.hiringservice.repository.TaskRepository;
import ua.com.hiringservice.service.KeycloakService;
import ua.com.hiringservice.service.task.ImageGeneratorService;
import ua.com.hiringservice.service.task.PracticalTaskImageService;
import ua.com.hiringservice.service.task.TaskService;
import ua.com.hiringservice.service.task.impl.PracticalTaskPassingServiceImpl;
import ua.com.hiringservice.util.mapper.task.PracticalTaskImageMapper;
import ua.com.hiringservice.util.mapper.task.TaskPassingMapper;

/**
 * This class contains unit tests for the PracticalTaskPassingServiceImpl class.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PracticalTaskPassingServiceImplTest {
  @Mock private KeycloakService keycloakService;
  @Mock private PracticalTaskRepository practicalTaskRepository;
  @Mock private TaskPassingRepository taskPassingRepository;
  @Mock private ImageGeneratorService imageGeneratorService;
  @Mock private PracticalTaskImageMapper practicalTaskImageMapper;
  @Mock private PracticalTaskImageService imageService;
  @Mock private TaskService taskService;
  @Mock private TaskRepository taskRepository;
  @Mock private TaskPassingMapper taskPassingMapper;
  @InjectMocks private PracticalTaskPassingServiceImpl underTest;

  @Test
  void startPracticalTaskPassing_shouldStartPassing_whenValidTask() {
    // Arrange
    final KeycloakUserDto keycloakUserDto = generateKeycloakUserDto();
    final UUID taskId = generateTask4Id();
    final Task task = generateTask4WithPracticalTask();

    when(keycloakService.getKeycloakUserInfoFromSecurityContext()).thenReturn(keycloakUserDto);
    when(taskService.findTaskById(taskId)).thenReturn(task);

    // Act
    underTest.startPracticalTaskPassing(taskId);

    // Assert
    verify(taskService).findTaskById(taskId);
    verify(taskPassingRepository).save(any());
  }

  @Test
  void methodGetPracticalTaskImaged_shouldReturnPracticalTaskWithBytesDto_whenValidInputs() {
    // Arrange
    final UUID taskId = generateTask4Id();
    final UUID userKeycloakId = generateUserKeycloakIdData();
    final KeycloakUserDto keycloakUserDto = generateKeycloakUserDto();
    final TaskPassing taskPassing = generateTaskPassingForPracticalTask();
    final List<PracticalTaskImageDto> imageDtos = generateImageDtos();
    final byte[] imageBytes = "Practical task text".getBytes();
    final PracticalTaskAnswer practicalTaskAnswer = generatePracticalTaskAnswer();
    taskPassing.setPracticalTaskAnswer(practicalTaskAnswer);

    when(keycloakService.getKeycloakUserInfoFromSecurityContext()).thenReturn(keycloakUserDto);

    when(taskPassingRepository.findTaskPassingByTaskIdAndUserKeycloakIdAndStatus(
            taskId, userKeycloakId, PassingStatus.IN_PROGRESS))
        .thenReturn(Optional.of(taskPassing));

    when(imageGeneratorService.convertHTMLContentToImageBytes(anyString(), anyInt()))
        .thenReturn(imageBytes);
    when(imageService.findAllImagesByIds(taskPassing)).thenReturn(imageDtos);

    // Act
    final PracticalTaskWithBytesDto result = underTest.getPracticalTaskImaged(taskId, 100);

    // Assert
    assertEquals(
        taskPassing.getPracticalTaskAnswer().getId(),
        result.getPracticalTaskAnswerId(),
        "Practical task answer id doesn't match");
    assertEquals(
        imageBytes.length, result.getTaskTextImage().length, "Length of byte arrays doesn't match");

    // Verify interactions
    verify(keycloakService).getKeycloakUserInfoFromSecurityContext();
    verify(taskPassingRepository)
        .findTaskPassingByTaskIdAndUserKeycloakIdAndStatus(
            taskId, userKeycloakId, PassingStatus.IN_PROGRESS);
    verify(imageGeneratorService).convertHTMLContentToImageBytes(anyString(), anyInt());
    verify(imageService).findAllImagesByIds(taskPassing);
    verifyNoMoreInteractions(imageGeneratorService, imageService);
  }

  @Test
  void
      methodGetAssessedDonePracticalTaskPassings_shouldReturnPracticalTaskPassingResponseDtoPage_whenValidInputs() {
    // Arrange
    final UUID userKeycloakId = generateUserKeycloakIdData();
    final KeycloakUserDto keycloakUserDto = generateKeycloakUserDto();

    final Pageable pageable = PageRequest.of(0, 10);
    final TaskPassing taskPassing = generateTaskPassingForPracticalTask();
    final List<TaskPassing> taskPassingList = Collections.singletonList(taskPassing);
    final Page<TaskPassing> taskPassingsPage =
        new PageImpl<>(taskPassingList, pageable, taskPassingList.size());

    final PracticalTaskPassingResponseDto practicalTaskPassingResponseDto =
        generatePracticalTaskPassingResponseDto();
    final List<PracticalTaskPassingResponseDto> taskPassingResponsesList =
        Collections.singletonList(practicalTaskPassingResponseDto);
    final Page<PracticalTaskPassingResponseDto> expectedPage =
        new PageImpl<>(taskPassingResponsesList, pageable, taskPassingResponsesList.size());

    when(keycloakService.getKeycloakUserInfoFromSecurityContext()).thenReturn(keycloakUserDto);
    when(taskPassingRepository.findAllByUserKeycloakIdAndStatus(
            userKeycloakId, PassingStatus.GRADED, pageable))
        .thenReturn(taskPassingsPage);

    when(taskPassingMapper.toPracticalTaskPassingResponseDto(taskPassing))
        .thenReturn(practicalTaskPassingResponseDto);

    // Act
    final Page<PracticalTaskPassingResponseDto> result =
        underTest.getAssessedDonePracticalTaskPassings(pageable);

    // Assert
    assertNotNull(result, "Result is Null");
    assertEquals(expectedPage, result, EXPECTED_NOT_MATCH_ACTUAL);

    // Verify interactions
    verify(keycloakService).getKeycloakUserInfoFromSecurityContext();
    verify(taskPassingRepository)
        .findAllByUserKeycloakIdAndStatus(userKeycloakId, PassingStatus.GRADED, pageable);
    verify(taskPassingMapper).toPracticalTaskPassingResponseDto(taskPassing);
    verifyNoMoreInteractions(taskPassingRepository);
  }

  @Test
  void
      methodGetAssessedDonePracticalTaskPassingsByTaskId_shouldReturnPracticalTaskPassingResponseDtoPage_whenValidInputs() {
    // Arrange
    final UUID userKeycloakId = generateUserKeycloakIdData();
    final KeycloakUserDto keycloakUserDto = generateKeycloakUserDto();
    final UUID taskId = generateTask4Id();

    final Pageable pageable = PageRequest.of(0, 10);
    final TaskPassing taskPassing = generateTaskPassingForPracticalTask();
    final List<TaskPassing> taskPassingList = Collections.singletonList(taskPassing);
    final Page<TaskPassing> taskPassingsPage =
        new PageImpl<>(taskPassingList, pageable, taskPassingList.size());

    final PracticalTaskPassingResponseDto practicalTaskPassingResponseDto =
        generatePracticalTaskPassingResponseDto();
    final List<PracticalTaskPassingResponseDto> taskPassingResponsesList =
        Collections.singletonList(practicalTaskPassingResponseDto);
    final Page<PracticalTaskPassingResponseDto> expectedPage =
        new PageImpl<>(taskPassingResponsesList, pageable, taskPassingResponsesList.size());

    when(keycloakService.getKeycloakUserInfoFromSecurityContext()).thenReturn(keycloakUserDto);
    when(taskPassingRepository.findByUserKeycloakIdAndStatusAndTaskId(
            userKeycloakId, PassingStatus.GRADED, taskId, pageable))
        .thenReturn(taskPassingsPage);

    when(taskPassingMapper.toPracticalTaskPassingResponseDto(taskPassing))
        .thenReturn(practicalTaskPassingResponseDto);

    // Act
    final Page<PracticalTaskPassingResponseDto> result =
        underTest.getAssessedDonePracticalTaskPassingsByTaskId(userKeycloakId, taskId, pageable);

    // Assert
    assertNotNull(result, "Result is Null");
    assertEquals(expectedPage, result, EXPECTED_NOT_MATCH_ACTUAL);

    // Verify interactions
    verifyNoInteractions(keycloakService);
    verify(taskPassingRepository)
        .findByUserKeycloakIdAndStatusAndTaskId(
            userKeycloakId, PassingStatus.GRADED, taskId, pageable);
    verify(taskPassingMapper).toPracticalTaskPassingResponseDto(taskPassing);
    verifyNoMoreInteractions(taskPassingRepository);
  }
}
