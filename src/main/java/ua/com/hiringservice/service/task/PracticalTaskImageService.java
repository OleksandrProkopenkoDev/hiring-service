package ua.com.hiringservice.service.task;

import java.util.List;
import ua.com.hiringservice.model.dto.task.PracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskImageDto;
import ua.com.hiringservice.model.entity.task.PracticalTask;
import ua.com.hiringservice.model.entity.task.PracticalTaskImage;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskPassing;

/**
 * Service interface for managing practical task diagrams - simple picture illustrations, stored in
 * mongo.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
public interface PracticalTaskImageService {

  List<PracticalTaskImageDto> findAllImagesByIds(Task task);

  List<PracticalTaskImage> findAllImagesByIds(PracticalTask practicalTask);

  List<PracticalTaskImageDto> findAllImagesByIds(TaskPassing taskPassing);

  void saveAllImagesByIds(PracticalTaskDto practicalTaskDto);

  void deleteImagesByIds(PracticalTask practicalTask);

  List<PracticalTaskImage> savePracticalTaskImages(PracticalTaskDto practicalTaskDto);
}
