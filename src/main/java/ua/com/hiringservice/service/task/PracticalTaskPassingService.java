package ua.com.hiringservice.service.task;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ua.com.hiringservice.model.dto.task.PracticalTaskPassingResponseDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskWithBytesDto;

/**
 * Service interface for managing practical task passing operations.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
public interface PracticalTaskPassingService {

  PracticalTaskWithBytesDto getPracticalTaskImaged(UUID taskId, Integer width);

  void startPracticalTaskPassing(UUID taskId);

  Page<PracticalTaskPassingResponseDto> getAssessedDonePracticalTaskPassings(Pageable pageable);

  Page<PracticalTaskPassingResponseDto> getAssessedDonePracticalTaskPassingsByTaskId(
      UUID userId, UUID taskId, Pageable pageable);
}
