package ua.com.hiringservice.model.content;

/**
 * Interface for providing answer options in test questions that include variants, such as
 * multiple-choice questions. Implementing classes should offer a method to retrieve the available
 * answer options.
 *
 * @implNote This interface is designed for test questions where respondents can choose from various
 *     answer options, such as options A, B, C, etc.
 * @author Zakhar Kuropiatnyk
 */
public interface AnswerOptionsProvidable {

  /**
   * Retrieves the available answer options for the test question.
   *
   * @return The answer options for the test question.
   */
  Object getAnswerOptions();
}
