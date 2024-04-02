package ua.com.hiringservice.repository.specification;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import ua.com.hiringservice.model.entity.task.Question;
import ua.com.hiringservice.model.enums.exam.QuestionType;
import ua.com.hiringservice.util.QuestionTypeSortUtil;

/**
 * Specification utility for creating dynamic queries on {@link Question} entities. Provides static
 * methods for building specifications based on different criteria such as minimum duration, maximum
 * duration, and question types.
 */
public interface QuestionSpecification {

  static Specification<Question> byMinDuration(long minDuration) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get("duration"), minDuration);
  }

  static Specification<Question> byMaxDuration(final long maxDuration) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get("duration"), maxDuration);
  }

  static Specification<Question> byQuestionType(final List<QuestionType> questionType) {
    return (root, query, criteriaBuilder) ->
        QuestionTypeSortUtil.getFilterFunction(root, criteriaBuilder, questionType);
  }
}
