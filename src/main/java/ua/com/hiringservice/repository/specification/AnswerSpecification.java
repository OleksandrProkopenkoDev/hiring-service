package ua.com.hiringservice.repository.specification;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.domain.Specification;
import ua.com.hiringservice.model.entity.task.Answer;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.model.enums.exam.QuestionType;
import ua.com.hiringservice.util.QuestionTypeSortUtil;

/**
 * Create Specifications for {@link Answer}.
 *
 * @author Zakhar Kuropiatnyk
 */
public interface AnswerSpecification {
  static Specification<Answer> byMinDuration(int minDuration) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.greaterThanOrEqualTo(root.get("duration"), minDuration);
  }

  static Specification<Answer> byMaxDuration(final int maxDuration) {
    return (root, query, criteriaBuilder) ->
        criteriaBuilder.lessThanOrEqualTo(root.get("duration"), maxDuration);
  }

  static Specification<Answer> byQuestionType(final List<QuestionType> questionType) {

    return (root, query, criteriaBuilder) ->
        QuestionTypeSortUtil.getFilterFunction(root, criteriaBuilder, questionType);
  }

  static Specification<Answer> byStatus(final List<PassingStatus> statuses) {
    return (root, query, criteriaBuilder) -> root.get("status").in(statuses);
  }

  static Specification<Answer> byQuizPassingId(final List<UUID> quizPassingIds) {
    return (root, query, criteriaBuilder) -> root.get("quizPassing").get("id").in(quizPassingIds);
  }
}
