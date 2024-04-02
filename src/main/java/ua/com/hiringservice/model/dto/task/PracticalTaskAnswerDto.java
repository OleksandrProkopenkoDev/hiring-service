package ua.com.hiringservice.model.dto.task;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.entity.task.TaskPassing;

/**
 * DTO representing a practical task answer full fields.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PracticalTaskAnswerDto {

  private UUID id;

  private TaskPassing taskPassing;

  private String gitAnswerLink;

  private Integer maxScore;

  private String comment;
}
