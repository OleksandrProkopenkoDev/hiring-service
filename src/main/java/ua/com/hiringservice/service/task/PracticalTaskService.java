package ua.com.hiringservice.service.task;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.hiringservice.model.dto.task.PracticalTaskDto;
import ua.com.hiringservice.model.dto.task.TaskDto;

/**
 * Service interface for managing practical tasks.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
public interface PracticalTaskService {
  Page<PracticalTaskDto> findAll(Pageable pageable);

  PracticalTaskDto save(PracticalTaskDto practicalTaskDto);

  PracticalTaskDto update(UUID practicalTaskId, PracticalTaskDto practicalTaskDto);

  void deleteById(UUID practicalTaskId);

  PracticalTaskDto findById(UUID practicalTaskId);

  TaskDto addPracticalTaskToTask(UUID taskId, UUID practicalTaskId);

  TaskDto removePracticalTaskFromTask(UUID taskId);
}
