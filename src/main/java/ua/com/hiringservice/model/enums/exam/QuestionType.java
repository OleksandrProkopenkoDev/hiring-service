package ua.com.hiringservice.model.enums.exam;

/** Replace this stub by correct Javadoc. */
public enum QuestionType {

  /** Test question with one answer option. Checks automatically. */
  SINGLE_CHOICE_TO_APP,
  /** Test question with more than one answer option. Checks automatically. */
  MULTIPLE_CHOICE_TO_APP,
  /**
   * Question in which user should select the correct matches between the fields from the first
   * column with the fields from the second. Checks automatically.
   */
  MATCHING_TO_APP,
  /** Question with an answer field for answer. Checks automatically. */
  SIMPLE_TEXT_TO_APP,
  /** Question with an answer field for answer. Checks manually by Mentor or Manager. */
  COMPLEX_TEXT_TO_MENTOR,
  /** Question with voice for answer. Checks manually by Mentor or Manager */
  VOICE_TO_MENTOR
}
