package ua.com.hiringservice.model.content.questioncontent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.content.AnswerOptionsProvidable;
import ua.com.hiringservice.model.content.AutoAssessable;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.content.QuestionContentType;
import ua.com.hiringservice.model.content.TextImageWrapper;
import ua.com.hiringservice.model.enums.exam.QuestionType;
import ua.com.hiringservice.util.ContentAssistanceUtil;
import ua.com.hiringservice.util.TextImageWrapperContentAssistanceUtil;

/**
 * Question in which user should select the correct matches between the fields from the first column
 * with the fields from the second. Checks automatically
 *
 * @author Zakhar Kuropiatnyk, gexter
 */
@QuestionContentType(QuestionType.MATCHING_TO_APP)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("PMD.UseConcurrentHashMap")
public class MatchingTestQuestionContent
    implements Content, AnswerOptionsProvidable, AutoAssessable {

  private static final Integer SIZE_ONE = 1;
  private TextImageWrapper questionDescription;
  private Map<TextImageWrapper, TextImageWrapper> answerOptions;
  private Map<String, String> correctAnswer;
  private Map<String, String> providedAnswer;
  private static final Map<String, String> EMPTY_ANSWER = new HashMap<>();
  private static final Map<String, String> NOT_ANSWERED =
      new HashMap<>(Map.of("Not answered", "Not answered"));
  private Integer maxGrade = MAX_GRADE;

  @Override
  public void setMaxGrade(Integer maxScore) {
    this.maxGrade = maxScore;
  }

  @Override
  public void excludeHtmlFromTextImageWrapper() {
    TextImageWrapperContentAssistanceUtil.excludeTextFromTextImageWrapper(
        this.questionDescription, this.answerOptions);
  }

  @Override
  public List<TextImageWrapper> collectAllTextImageWrappersWithImageNull() {
    return TextImageWrapperContentAssistanceUtil.getListWithTextImageWrappersWithNullImage(
        questionDescription, answerOptions);
  }

  @Override
  public void hideCorrectAnswer() {
    correctAnswer = new HashMap<>();
  }

  @Override
  public Integer calculateGrade() {
    areAnswersNull();

    final float gradePerAnswer = (float) maxGrade / correctAnswer.size();
    float grade;

    if (correctAnswer.size() == SIZE_ONE) {
      final String correctKey = correctAnswer.keySet().iterator().next();
      grade =
          correctAnswer.get(correctKey).equals(providedAnswer.get(correctKey))
              ? maxGrade
              : MIN_GRADE;
    } else {
      grade =
          (float)
              correctAnswer.keySet().stream()
                  .filter(
                      correctKey ->
                          correctAnswer.get(correctKey).equals(providedAnswer.get(correctKey)))
                  .mapToDouble(correctKey -> gradePerAnswer)
                  .sum();
    }

    return Math.round(grade);
  }

  @Override
  public void imagesToTextProvidedAnswer() {
    if (this.providedAnswer == null || this.providedAnswer.isEmpty()) {
      return;
    }
    final Set<TextImageWrapper> wrappers =
        TextImageWrapperContentAssistanceUtil.getListOfTextImageWrappersFromMap(answerOptions);
    ContentAssistanceUtil.imagesToTextProvidedAnswer(providedAnswer, wrappers);
  }

  @Override
  public Content getSafeUpdatedContent(final Content sourceContent) {
    final MatchingTestQuestionContent source = (MatchingTestQuestionContent) sourceContent;
    this.setProvidedAnswer(
        ContentAssistanceUtil.getSafeUpdatedMap(this.providedAnswer, source.getProvidedAnswer()));

    this.setCorrectAnswer(
        ContentAssistanceUtil.getSafeUpdatedMap(this.providedAnswer, source.getCorrectAnswer()));

    TextImageWrapperContentAssistanceUtil.safeUpdateWrapper(
        this.questionDescription, source.getQuestionDescription());

    TextImageWrapperContentAssistanceUtil.safeUpdateWrapperMap(
        this.answerOptions, source.getAnswerOptions());

    return this;
  }

  private void areAnswersNull() {
    if (providedAnswer == null) {
      providedAnswer = NOT_ANSWERED;
    }

    if (correctAnswer == null) {
      correctAnswer = EMPTY_ANSWER;
    }
  }
}
