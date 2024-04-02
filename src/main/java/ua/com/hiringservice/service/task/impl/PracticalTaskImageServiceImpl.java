package ua.com.hiringservice.service.task.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import ua.com.hiringservice.model.dto.task.PracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskImageDto;
import ua.com.hiringservice.model.entity.task.PracticalTask;
import ua.com.hiringservice.model.entity.task.PracticalTaskImage;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.repository.mongo.PracticalTaskImageRepository;
import ua.com.hiringservice.service.task.PracticalTaskImageService;
import ua.com.hiringservice.util.mapper.task.PracticalTaskImageMapper;

/**
 * Implementation of the {@link PracticalTaskImageService} interface for managing practical task
 * diagrams.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@AllArgsConstructor
@Service
public class PracticalTaskImageServiceImpl implements PracticalTaskImageService {

  private final PracticalTaskImageRepository imageRepository;
  private final PracticalTaskImageMapper practicalTaskImageMapper;

  @Override
  public List<PracticalTaskImage> findAllImagesByIds(PracticalTask practicalTask) {
    return imageRepository.findAllById(practicalTask.getImageIds());
  }

  @Override
  public List<PracticalTaskImageDto> findAllImagesByIds(Task task) {
    List<String> imageIds = new ArrayList<>();
    if (task.getPracticalTask() != null) {
      imageIds = task.getPracticalTask().getImageIds();
    }
    return imageRepository.findAllById(imageIds).stream()
        .map(practicalTaskImageMapper::toDto)
        .toList();
  }

  @Override
  public List<PracticalTaskImageDto> findAllImagesByIds(TaskPassing taskPassing) {
    if (taskPassing == null
        || taskPassing.getTask() == null
        || taskPassing.getTask().getPracticalTask() == null) {
      return new ArrayList<>();
    }
    return imageRepository
        .findAllById(taskPassing.getTask().getPracticalTask().getImageIds())
        .stream()
        .map(practicalTaskImageMapper::toDto)
        .toList();
  }

  @Override
  public void saveAllImagesByIds(PracticalTaskDto practicalTaskDto) {
    final List<PracticalTaskImage> practicalTaskImages =
        practicalTaskDto.getImageDtos().stream().map(practicalTaskImageMapper::toEntity).toList();

    final List<PracticalTaskImageDto> diagramDtos =
        imageRepository.saveAll(practicalTaskImages).stream()
            .map(practicalTaskImageMapper::toDto)
            .toList();
    practicalTaskDto.setImageDtos(diagramDtos);
  }

  @Override
  public void deleteImagesByIds(PracticalTask practicalTask) {
    imageRepository.deleteAllById(practicalTask.getImageIds());
    practicalTask.setImageIds(new ArrayList<>());
  }

  @NotNull
  @Override
  public List<PracticalTaskImage> savePracticalTaskImages(PracticalTaskDto practicalTaskDto) {
    // get list of images
    final List<PracticalTaskImage> practicalTaskImages =
        practicalTaskDto.getImageDtos().stream().map(practicalTaskImageMapper::toEntity).toList();
    // save images to mongo
    return imageRepository.saveAll(practicalTaskImages);
  }
}
