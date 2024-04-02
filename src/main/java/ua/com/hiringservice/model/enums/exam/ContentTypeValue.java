package ua.com.hiringservice.model.enums.exam;

/**
 * Enum class for Content to mark Different types for serialization
 *
 * @author Zakhar Kuropiatnyk
 */
public enum ContentTypeValue {
  /** Content with one answer option. Checks automatically. */
  SINGLE_TEST_CONTENT,
  /** Content with more than one answer option. Checks automatically. */
  MULTIPLE_TEST_CONTENT,
  /**
   * Content in which user should select the correct matches between the fields from the first
   * column with the fields from the second. Checks automatically.
   */
  MATCHING_TEST_CONTENT,
  /** Content with an answer field for answer. Checks automatically. */
  TEXT_TEST_CONTENT,
  /** Content with an answer field for answer. Checks manually by Mentor or Manager. */
  TEXT_TO_MENTOR_CONTENT,
  /** Content with voice for answer. Checks manually by Mentor or Manager */
  VOICE_TO_MENTOR_CONTENT
}
