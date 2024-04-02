package ua.com.hiringservice.model.content.questioncontent;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import ua.com.hiringservice.model.content.AnswerOptionsProvidable;
import ua.com.hiringservice.model.content.AutoAssessable;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.content.QuestionContentType;
import ua.com.hiringservice.model.content.TextImageWrapper;
import ua.com.hiringservice.model.enums.exam.QuestionType;
import ua.com.hiringservice.util.ContentAssistanceUtil;
import ua.com.hiringservice.util.TextImageWrapperContentAssistanceUtil;

/**
 * Test question with one answer option. Checks automatically.
 *
 * @author Zakhar Kuropiatnyk, gexter
 */
@QuestionContentType(QuestionType.SINGLE_CHOICE_TO_APP)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingleChoiceTestQuestionContent
    implements Content, AnswerOptionsProvidable, AutoAssessable {

  private TextImageWrapper questionDescription;
  private Set<TextImageWrapper> answerOptions;
  private String correctAnswer;
  private String providedAnswer;
  private static final String EMPTY_ANSWER = "";
  private static final String NOT_ANSWERED = "Not answered";
  private Integer maxGrade = MAX_GRADE;

  @Override
  public void hideCorrectAnswer() {
    correctAnswer = "";
  }

  @Override
  public Integer calculateGrade() {
    areAnswersNull();
    return Objects.equals(correctAnswer, providedAnswer) ? maxGrade : MIN_GRADE;
  }

  @Override
  public void imagesToTextProvidedAnswer() {
    if (Strings.isBlank(this.providedAnswer)) {
      return;
    }

    for (final TextImageWrapper imageWrapper : answerOptions) {
      if (imageWrapper.getImage().equals(providedAnswer)) {
        providedAnswer = imageWrapper.getHtml();
      }
    }
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
  public Content getSafeUpdatedContent(final Content sourceContent) {
    final SingleChoiceTestQuestionContent source = (SingleChoiceTestQuestionContent) sourceContent;

    this.providedAnswer =
        ContentAssistanceUtil.getSafeUpdatedString(this.providedAnswer, source.getProvidedAnswer());
    this.correctAnswer =
        ContentAssistanceUtil.getSafeUpdatedString(this.correctAnswer, source.getCorrectAnswer());

    TextImageWrapperContentAssistanceUtil.safeUpdateWrapper(
        this.questionDescription, source.getQuestionDescription());

    TextImageWrapperContentAssistanceUtil.safeUpdateWrappers(
        this.answerOptions, source.getAnswerOptions());

    return this;
  }

  @Override
  public void setMaxGrade(Integer maxScore) {
    this.maxGrade = maxScore;
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
