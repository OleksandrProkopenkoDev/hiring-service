package ua.com.hiringservice.model.content;

/**
 * Interface providing a method to check and retrieve the correct answer for questions with
 * predefined correct knowledge. Implementing classes should offer a method to obtain the correct
 * answer and may support a visitor pattern.
 *
 * @implNote This interface is designed for questions where the correct answer is predefined and can
 *     be checked programmatically. Implementing classes should provide the correct answer through
 *     the {@link AutoAssessable#getCorrectAnswer()} method. The interface also mentions support for
 *     a visitor pattern, although this feature is currently disabled.
 * @author Zakhar Kuropiatnyk
 */
public interface AutoAssessable {

  Integer MAX_GRADE = 100;
  Integer MIN_GRADE = 0;

  String EXPLANATION_MESSAGE =
      "Your provided answer is %s. Correct answer is %s, so for this question you get %s points.";

  /**
   * Retrieves the correct answer for questions with predefined correct knowledge.
   *
   * @return The correct answer for the question.
   */
  Object getCorrectAnswer();

  void hideCorrectAnswer();

  void setMaxGrade(Integer maxScore);

  Integer calculateGrade();

  default String generateComment(Integer grade) {
    return String.format(EXPLANATION_MESSAGE, getProvidedAnswer(), getCorrectAnswer(), grade);
  }

  Object getProvidedAnswer();

  void imagesToTextProvidedAnswer();
}
