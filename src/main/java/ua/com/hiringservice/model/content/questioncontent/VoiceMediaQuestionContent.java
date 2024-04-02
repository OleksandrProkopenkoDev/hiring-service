package ua.com.hiringservice.model.content.questioncontent;

import static ua.com.hiringservice.model.content.AutoAssessable.MAX_GRADE;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.content.QuestionContentType;
import ua.com.hiringservice.model.content.TextImageWrapper;
import ua.com.hiringservice.model.enums.exam.QuestionType;
import ua.com.hiringservice.util.TextImageWrapperContentAssistanceUtil;

/**
 * Question with media for answer. Checks manually by Exam Reviewer
 *
 * @author Zakhar Kuropiatnyk, gexter
 */
@QuestionContentType(QuestionType.VOICE_TO_MENTOR)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoiceMediaQuestionContent implements Content {

  private TextImageWrapper questionDescription;

  private byte[] providedAnswer;
  private Integer maxGrade = MAX_GRADE;

  @Override
  public void setMaxGrade(Integer maxScore) {
    this.maxGrade = maxScore;
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
    final VoiceMediaQuestionContent source = (VoiceMediaQuestionContent) sourceContent;
    if (source.providedAnswer != null) {
      this.setProvidedAnswer(source.getProvidedAnswer());
    }
    if (source.questionDescription != null) {
      TextImageWrapperContentAssistanceUtil.safeUpdateWrapper(
          this.questionDescription, source.getQuestionDescription());
    }
    return this;
  }
}
