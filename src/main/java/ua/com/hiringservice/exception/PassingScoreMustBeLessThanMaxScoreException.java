package ua.com.hiringservice.exception;

import ua.com.hiringservice.model.dto.task.TaskDto;

/**
 * Exception thrown when the passing score provided in a TaskDto object is greater than or equal to
 * the maximum score.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
public class PassingScoreMustBeLessThanMaxScoreException extends RuntimeException {
  private static final String MESSAGE = "Passing score [%s] must be less than max score [%s]";

  public PassingScoreMustBeLessThanMaxScoreException(TaskDto taskDto) {
    super(MESSAGE.formatted(taskDto.getPassingScore(), taskDto.getMaxScore()));
  }
}
