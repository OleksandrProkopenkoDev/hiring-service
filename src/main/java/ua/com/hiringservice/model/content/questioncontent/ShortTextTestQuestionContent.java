package ua.com.hiringservice.model.content.questioncontent;

import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.content.AutoAssessable;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.content.QuestionContentType;
import ua.com.hiringservice.model.content.TextImageWrapper;
import ua.com.hiringservice.model.enums.exam.QuestionType;
import ua.com.hiringservice.util.ContentAssistanceUtil;
import ua.com.hiringservice.util.TextImageWrapperContentAssistanceUtil;

/**
 * Question with an answer filed for short answer which will assessment automatically
 *
 * @author Zakhar Kuropiatnyk, gexter
 */
@QuestionContentType(QuestionType.SIMPLE_TEXT_TO_APP)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortTextTestQuestionContent implements Content, AutoAssessable {

  private TextImageWrapper questionDescription;
  private String correctAnswer;
  private String providedAnswer;
  private static final String EMPTY_ANSWER = "";
  private static final String NOT_ANSWERED = "Not answered";
  private Integer maxGrade = MAX_GRADE;

  @Override
  public void setMaxGrade(Integer maxScore) {
    this.maxGrade = maxScore;
  }

  @Override
  public void hideCorrectAnswer() {
    correctAnswer = "";
  }

  @Override
  public void excludeHtmlFromTextImageWrapper() {
    TextImageWrapperContentAssistanceUtil.excludeTextFromTextImageWrapper(this.questionDescription);
  }

  @Override
  public List<TextImageWrapper> collectAllTextImageWrappersWithImageNull() {
    return TextImageWrapperContentAssistanceUtil.getListWithTextImageWrappersWithNullImage(
        questionDescription);
  }

  @Override
  public Content getSafeUpdatedContent(final Content sourceContent) {
    final ShortTextTestQuestionContent source = (ShortTextTestQuestionContent) sourceContent;
    this.setCorrectAnswer(
        ContentAssistanceUtil.getSafeUpdatedString(this.correctAnswer, source.getCorrectAnswer()));
    this.setProvidedAnswer(
        ContentAssistanceUtil.getSafeUpdatedString(
            this.providedAnswer, source.getProvidedAnswer()));

    TextImageWrapperContentAssistanceUtil.safeUpdateWrapper(
        this.questionDescription, source.getQuestionDescription());
    return this;
  }

  @Override
  public Integer calculateGrade() {
    areAnswersNull();
    return Objects.equals(correctAnswer, providedAnswer) ? MAX_GRADE : MIN_GRADE;
  }

  @Override
  public void imagesToTextProvidedAnswer() {
    if (Objects.isNull(providedAnswer)) {
      providedAnswer = "";
    }
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
