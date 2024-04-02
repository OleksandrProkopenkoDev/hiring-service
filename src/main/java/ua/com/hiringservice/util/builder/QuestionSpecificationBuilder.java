package ua.com.hiringservice.util.builder;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import ua.com.hiringservice.model.entity.task.Question;
import ua.com.hiringservice.model.enums.exam.QuestionType;
import ua.com.hiringservice.repository.specification.QuestionSpecification;

/**
 * Builder class for constructing {@link Specification} instances for querying {@link Question}
 * entities based on filtering criteria.
 */
public class QuestionSpecificationBuilder {

  private Specification<Question> specification = Specification.where(null);

  /**
   * Filters by question types.
   *
   * @param type The list of question types to filter by.
   * @return The updated {@link QuestionSpecificationBuilder} instance.
   */
  public QuestionSpecificationBuilder questionType(List<QuestionType> type) {
    if (type != null) {
      specification = specification.and(QuestionSpecification.byQuestionType(type));
    }
    return this;
  }

  /**
   * Filters by maximum question duration.
   *
   * @param maxDuration The maximum duration to filter by.
   * @return The updated {@link QuestionSpecificationBuilder} instance.
   */
  public QuestionSpecificationBuilder maxDuration(Long maxDuration) {
    if (maxDuration != null) {
      specification = specification.and(QuestionSpecification.byMaxDuration(maxDuration));
    }
    return this;
  }

  /**
   * Filters by minimum question duration.
   *
   * @param minDuration The minimum duration to filter by.
   * @return The updated {@link QuestionSpecificationBuilder} instance.
   */
  public QuestionSpecificationBuilder minDuration(Long minDuration) {
    if (minDuration != null) {
      specification = specification.and(QuestionSpecification.byMinDuration(minDuration));
    }
    return this;
  }

  public Specification<Question> build() {
    return specification;
  }
}
