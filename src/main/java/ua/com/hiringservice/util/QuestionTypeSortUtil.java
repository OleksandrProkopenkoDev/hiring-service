package ua.com.hiringservice.util;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import ua.com.hiringservice.model.enums.exam.QuestionType;

/**
 * Utility class for solvation problems with sorting by {@link QuestionType}
 *
 * @author Zakhar Kuropiatnyk
 */
@UtilityClass
public class QuestionTypeSortUtil {

  private static final String QUESTION_TYPE = "questionType";
  private static final String CONTENT = "content";
  public static final String JSONB_EXTRACT_PATH_TEXT = "jsonb_extract_path_text";

  public static boolean isSortByQuestionType(Pageable pageable) {

    return pageable.getSort().isSorted()
        && pageable.getSort().get().anyMatch(order -> QUESTION_TYPE.equals(order.getProperty()));
  }

  public static Pageable getPageableSortByQuestionType(Pageable pageable) {
    if (!pageable.getSort().isSorted()) {
      return pageable;
    }
    final List<Sort.Order> contentOrders =
        pageable
            .getSort()
            .get()
            .filter(order -> !QUESTION_TYPE.equals(order.getProperty()))
            .toList();

    return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(contentOrders));
  }

  public static <T> Specification<T> getQuestionTypeSpecificationSort(Pageable pageable) {
    return getContentSpecificationSortByQuestionType(getContentOrder(pageable));
  }

  public static <T> Page<T> getRequestPageSortedByQuestionType(Page<T> page) {

    return new PageImpl<>(
        page.getContent(),
        PageRequest.of(
            page.getPageable().getPageNumber(),
            page.getPageable().getPageSize(),
            Sort.by(getContentOrder(page.getPageable()))),
        page.getTotalElements());
  }

  public static <T> Predicate getFilterFunction(
      Root<T> root, CriteriaBuilder criteriaBuilder, List<QuestionType> questionType) {
    final List<String> questionTypes = questionType.stream().map(QuestionType::toString).toList();
    return criteriaBuilder
        .function(
            JSONB_EXTRACT_PATH_TEXT,
            String.class,
            root.get(CONTENT),
            criteriaBuilder.literal(QUESTION_TYPE))
        .in(questionTypes);
  }

  private static <T> Specification<T> getContentSpecificationSortByQuestionType(Sort.Order order) {
    if (order == null) {
      return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
    }

    final Sort.Direction direction = order.getDirection();
    if (direction == Sort.Direction.ASC) {
      return (root, query, criteriaBuilder) ->
          query
              .orderBy(criteriaBuilder.asc(getSortFunction(criteriaBuilder, root)))
              .getRestriction();
    } else {
      return (root, query, criteriaBuilder) ->
          query
              .orderBy(criteriaBuilder.desc(getSortFunction(criteriaBuilder, root)))
              .getRestriction();
    }
  }

  private static <T> Expression<?> getSortFunction(CriteriaBuilder criteriaBuilder, Root<T> root) {
    return criteriaBuilder.function(
        JSONB_EXTRACT_PATH_TEXT,
        String.class,
        root.get(CONTENT),
        criteriaBuilder.literal(QUESTION_TYPE));
  }

  private static Sort.Order getContentOrder(Pageable pageable) {
    return pageable.getSort().stream()
        .filter(order -> order.getProperty().contains(QUESTION_TYPE))
        .findAny()
        .orElse(null);
  }
}
