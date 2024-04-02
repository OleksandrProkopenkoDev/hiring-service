package ua.com.hiringservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateTaskPassingInProgress;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generateImageDtos;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generateImageIds;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generateImages;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generatePracticalTask;
import static ua.com.testsupport.datagenerator.task.PracticalTaskDataGenerator.generatePracticalTaskDto;
import static ua.com.testsupport.datagenerator.task.TaskDataGenerator.generateTask4WithPracticalTask;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.hiringservice.model.dto.task.PracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskImageDto;
import ua.com.hiringservice.model.entity.task.PracticalTask;
import ua.com.hiringservice.model.entity.task.PracticalTaskImage;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.repository.mongo.PracticalTaskImageRepository;
import ua.com.hiringservice.service.task.impl.PracticalTaskImageServiceImpl;
import ua.com.hiringservice.util.mapper.task.PracticalTaskImageMapper;

/**
 * This class contains unit tests for the PracticalTaskImageServiceImpl class.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PracticalTaskImageServiceImplTest {

  public static final String LIST_SIZE_NOT_EQUALS =
      "Expected images list size doesn't equal actual";
  @Mock private PracticalTaskImageRepository imageRepository;
  @Mock private PracticalTaskImageMapper practicalTaskImageMapper;

  @InjectMocks private PracticalTaskImageServiceImpl practicalTaskImageService;

  @Test
  void findAllImagesByIds_shouldReturnList_whenPracticalTask() {
    // Mock data
    final PracticalTask practicalTask = generatePracticalTask();
    final List<PracticalTaskImage> images = generateImages();

    when(imageRepository.findAllById(practicalTask.getImageIds())).thenReturn(images);

    // Test
    final List<PracticalTaskImage> result =
        practicalTaskImageService.findAllImagesByIds(practicalTask);

    // Verify
    assertEquals(images, result, "Found images don't match expected");
    verify(imageRepository, times(1)).findAllById(practicalTask.getImageIds());
  }

  @Test
  void findAllImagesByIds_shouldReturnList_whenTask() {
    // Arrange
    final Task task = generateTask4WithPracticalTask();
    final List<String> imageIds = generateImageIds();

    final List<PracticalTaskImage> images = generateImages();
    final List<PracticalTaskImageDto> expectedImageDtos = generateImageDtos();

    when(imageRepository.findAllById(imageIds)).thenReturn(images); // Mocking repository response
    when(practicalTaskImageMapper.toDto(any()))
        .thenReturn(expectedImageDtos.get(0)); // Mocking mapper response
    when(practicalTaskImageMapper.toDto(any()))
        .thenReturn(expectedImageDtos.get(1)); // Mocking mapper response
    when(practicalTaskImageMapper.toDto(any()))
        .thenReturn(expectedImageDtos.get(2)); // Mocking mapper response

    // Act
    final List<PracticalTaskImageDto> actualImages =
        practicalTaskImageService.findAllImagesByIds(task);

    // Assert
    assertEquals(expectedImageDtos.size(), actualImages.size(), LIST_SIZE_NOT_EQUALS);
  }

  @Test
  void findAllImagesByIds_shouldReturnList_whenTaskPassingNotNull() {
    // Arrange
    final TaskPassing taskPassing = generateTaskPassingInProgress();
    final Task task = generateTask4WithPracticalTask();
    final List<String> imageIds = generateImageIds();
    taskPassing.setTask(task);

    final List<PracticalTaskImageDto> expectedImages = generateImageDtos();
    final List<PracticalTaskImage> foundImages = generateImages();

    when(imageRepository.findAllById(imageIds))
        .thenReturn(foundImages); // Mocking repository response
    when(practicalTaskImageMapper.toDto(any()))
        .thenReturn(expectedImages.get(0)); // Mocking mapper response
    when(practicalTaskImageMapper.toDto(any()))
        .thenReturn(expectedImages.get(1)); // Mocking mapper response
    when(practicalTaskImageMapper.toDto(any()))
        .thenReturn(expectedImages.get(2)); // Mocking mapper response

    // Act
    final List<PracticalTaskImageDto> actualImages =
        practicalTaskImageService.findAllImagesByIds(taskPassing);

    // Assert
    assertEquals(expectedImages.size(), actualImages.size(), LIST_SIZE_NOT_EQUALS);
  }

  @Test
  void findAllImagesByIds_shouldReturnEmptyList_whenTaskPassingNull() {
    final TaskPassing taskPassing = null;

    final List<PracticalTaskImageDto> actualImages =
        practicalTaskImageService.findAllImagesByIds(taskPassing);

    assertEquals(0, actualImages.size(), LIST_SIZE_NOT_EQUALS);
  }

  @Test
  void findAllImagesByIds_shouldReturnEmptyList_whenPracticalTaskNull() {
    // Arrange
    final TaskPassing taskPassing = new TaskPassing();
    final Task task = new Task();
    taskPassing.setTask(task);

    // Act
    final List<PracticalTaskImageDto> actualImages =
        practicalTaskImageService.findAllImagesByIds(taskPassing);

    // Assert
    assertEquals(0, actualImages.size(), LIST_SIZE_NOT_EQUALS);
  }

  @Test
  void saveAllImagesByIds_shouldSave_whenPracticalTaskDto() {
    // Arrange
    final PracticalTaskDto practicalTaskDto = generatePracticalTaskDto();
    final List<PracticalTaskImage> savedImages = generateImages();
    final List<PracticalTaskImageDto> savedImageDtos = generateImageDtos();

    when(practicalTaskImageMapper.toEntity(any())).thenReturn(savedImages.get(0));
    when(practicalTaskImageMapper.toEntity(any())).thenReturn(savedImages.get(1));
    when(practicalTaskImageMapper.toEntity(any())).thenReturn(savedImages.get(2));
    when(imageRepository.saveAll(any())).thenReturn(savedImages); // Mocking repository response
    when(practicalTaskImageMapper.toDto(any())).thenReturn(savedImageDtos.get(0));
    when(practicalTaskImageMapper.toDto(any())).thenReturn(savedImageDtos.get(1));
    when(practicalTaskImageMapper.toDto(any())).thenReturn(savedImageDtos.get(2));

    // Act
    practicalTaskImageService.saveAllImagesByIds(practicalTaskDto);

    // Assert
    verify(imageRepository, times(1)).saveAll(any());
  }

  @Test
  void testDeleteImagesByIds() {
    // Arrange
    final PracticalTask practicalTask = generatePracticalTask();
    final List<String> imageIds = generateImageIds();

    // Act
    practicalTaskImageService.deleteImagesByIds(practicalTask);

    // Assert
    verify(imageRepository, times(1))
        .deleteAllById(imageIds); // Verify that deleteAllById is called with the correct image IDs
    // Verify that the imageIds list is cleared after deletion
    assertEquals(0, practicalTask.getImageIds().size(), LIST_SIZE_NOT_EQUALS);
  }

  @Test
  void savePracticalTaskDiagrams_shouldSave() {
    // Mock PracticalTaskDto and PracticalTaskImageMapper
    final PracticalTaskDto practicalTaskDto = generatePracticalTaskDto();
    final List<PracticalTaskImageDto> imageDtos = generateImageDtos();
    // Add any necessary setup for the practicalTaskDto and imageDtos

    // Mock PracticalTaskImage objects
    final List<PracticalTaskImage> practicalTaskImages = generateImages();
    // Add any necessary setup for the practicalTaskImages

    // Mock the behavior of PracticalTaskImageMapper
    when(practicalTaskImageMapper.toEntity(imageDtos.get(0)))
        .thenReturn(practicalTaskImages.get(0));
    when(practicalTaskImageMapper.toEntity(imageDtos.get(1)))
        .thenReturn(practicalTaskImages.get(1));
    when(practicalTaskImageMapper.toEntity(imageDtos.get(2)))
        .thenReturn(practicalTaskImages.get(2));

    // Mock the behavior of imageRepository.saveAll() method
    when(imageRepository.saveAll(any())).thenReturn(practicalTaskImages);

    // Call the method under test
    final List<PracticalTaskImage> savedImages =
        practicalTaskImageService.savePracticalTaskImages(practicalTaskDto);

    // Verify that the PracticalTaskImageMapper was called for each imageDto
    verify(practicalTaskImageMapper, times(imageDtos.size())).toEntity(any());

    // Verify that the imageRepository.saveAll() method was called with the correct list of images
    verify(imageRepository).saveAll(practicalTaskImages);

    // Assert that the returned list of images is equal to the list returned by
    // imageRepository.saveAll()
    assertEquals(practicalTaskImages, savedImages, "Actual images list not equals expected");
  }
}
