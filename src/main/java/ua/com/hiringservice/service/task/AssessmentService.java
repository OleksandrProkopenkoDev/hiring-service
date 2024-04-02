package ua.com.hiringservice.service.task;

import java.util.UUID;
import ua.com.hiringservice.model.dto.task.AnswerDto;
import ua.com.hiringservice.model.entity.task.Answer;

/**
 * Assessment Service.
 *
 * @author Vladislav Sauliak
 */
public interface AssessmentService {

  /**
   * Method allow manually assess {@link Answer}
   *
   * @param id Answer id
   * @param grade provided grade
   * @param comment provided comment
   * @return undated {@link AnswerDto}
   */
  AnswerDto assessAnswerManually(UUID id, Integer grade, String comment);

  void assessAnswerIfAutoAssessable(Answer answer);
}
