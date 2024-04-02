package ua.com.hiringservice.util.builder;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;
import ua.com.hiringservice.model.entity.task.Answer;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.model.enums.exam.QuestionType;
import ua.com.hiringservice.repository.specification.AnswerSpecification;

/**
 * Util class for create answer specifications
 *
 * @author Zakhar Kuropiatnyk
 */
public class AnswerSpecificationBuilder {

  private Specification<Answer> specification = Specification.where(null);

  /**
   * Filters by Question types.
   *
   * @param type The list of Question types to filter by.
   * @return The updated {@link AnswerSpecificationBuilder} instance.
   */
  public AnswerSpecificationBuilder questionType(final List<QuestionType> type) {
    if (type != null && !type.isEmpty()) {
      specification = specification.and(AnswerSpecification.byQuestionType(type));
    }
    return this;
  }

  /**
   * Filters by maximum Answer duration.
   *
   * @param maxDuration The maximum duration to filter by.
   * @return The updated {@link AnswerSpecificationBuilder} instance.
   */
  public AnswerSpecificationBuilder maxDuration(final Integer maxDuration) {
    if (maxDuration != null) {
      specification = specification.and(AnswerSpecification.byMaxDuration(maxDuration));
    }
    return this;
  }

  /**
   * Filters by minimum Answer duration.
   *
   * @param minDuration The minimum duration to filter by.
   * @return The updated {@link AnswerSpecificationBuilder} instance.
   */
  public AnswerSpecificationBuilder minDuration(final Integer minDuration) {
    if (minDuration != null) {
      specification = specification.and(AnswerSpecification.byMinDuration(minDuration));
    }
    return this;
  }

  /**
   * Filters by {@link PassingStatus}
   *
   * @param statuses The List of {@link PassingStatus} to filter by.
   * @return The updated {@link AnswerSpecificationBuilder} instance.
   */
  public AnswerSpecificationBuilder status(final List<PassingStatus> statuses) {
    if (statuses != null && !statuses.isEmpty()) {
      specification = specification.and(AnswerSpecification.byStatus(statuses));
    }
    return this;
  }

  /**
   * Filters by {@link TaskPassing}
   *
   * @param quizPassingIds id of expected {@link TaskPassing}
   * @return The updated {@link AnswerSpecificationBuilder} instance.
   */
  public AnswerSpecificationBuilder quizPassing(final List<UUID> quizPassingIds) {
    if (quizPassingIds != null) {
      specification = specification.and(AnswerSpecification.byQuizPassingId(quizPassingIds));
    }
    return this;
  }

  public Specification<Answer> build() {
    return specification;
  }
}
