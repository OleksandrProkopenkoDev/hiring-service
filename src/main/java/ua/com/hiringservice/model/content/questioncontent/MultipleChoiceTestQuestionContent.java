package ua.com.hiringservice.model.content.questioncontent;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
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
 * Test question with more than one answer option. Checks automatically
 *
 * @author Zakhar Kuropiatnyk, gexter
 */
@QuestionContentType(QuestionType.MULTIPLE_CHOICE_TO_APP)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultipleChoiceTestQuestionContent
    implements Content, AutoAssessable, AnswerOptionsProvidable {

  private TextImageWrapper questionDescription;
  private Set<TextImageWrapper> answerOptions;
  private Set<String> correctAnswer;
  private Set<String> providedAnswer;
  private static final Set<String> EMPTY_ANSWER = new HashSet<>();
  private static final Set<String> NOT_ANSWERED = new HashSet<>(Set.of("Not answered"));
  private Integer maxGrade = MAX_GRADE;

  @Override
  public void setMaxGrade(Integer maxScore) {
    this.maxGrade = maxScore;
  }

  @Override
  public void hideCorrectAnswer() {
    this.setCorrectAnswer(Collections.emptySet());
  }

  @Override
  public Integer calculateGrade() {
    areAnswersNull();

    if (providedAnswer.equals(correctAnswer)) {
      return maxGrade;
    }

    if (providedAnswer.size() < correctAnswer.size()) {
      final Set<String> correct = new HashSet<>(this.correctAnswer);
      for (final String answer : providedAnswer) {
        if (providedAnswer.contains(answer)) {
          correct.remove(answer);
        }
      }

      final float downGradePerAnswer = (float) maxGrade / correctAnswer.size();
      final float downGrade = downGradePerAnswer * correct.size();

      return Math.round(Math.max(MIN_GRADE, maxGrade - downGrade));
    }

    int incorrectAnswers = 0;
    for (final String answer : providedAnswer) {
      if (!correctAnswer.contains(answer)) {
        incorrectAnswers++;
      }
    }

    final float downGradePerAnswer = (float) maxGrade / correctAnswer.size();
    final float downGrade = downGradePerAnswer * incorrectAnswers;

    return Math.round(Math.max(MIN_GRADE, maxGrade - downGrade));
  }

  @Override
  public void imagesToTextProvidedAnswer() {
    if (this.providedAnswer == null || this.providedAnswer.isEmpty()) {
      return;
    }
    ContentAssistanceUtil.imagesToTextProvidedAnswer(providedAnswer, answerOptions);
  }

  @Override
  public List<TextImageWrapper> collectAllTextImageWrappersWithImageNull() {
    return TextImageWrapperContentAssistanceUtil.getListWithTextImageWrappersWithNullImage(
        questionDescription, answerOptions);
  }

  @Override
  public void excludeHtmlFromTextImageWrapper() {
    TextImageWrapperContentAssistanceUtil.excludeTextFromTextImageWrapper(
        this.questionDescription, this.answerOptions);
  }

  @Override
  public Content getSafeUpdatedContent(final Content sourceContent) {
    final MultipleChoiceTestQuestionContent source =
        (MultipleChoiceTestQuestionContent) sourceContent;
    TextImageWrapperContentAssistanceUtil.safeUpdateWrapper(
        this.questionDescription, source.getQuestionDescription());
    this.setCorrectAnswer(
        ContentAssistanceUtil.getSafeUpdatedStringSet(
            this.correctAnswer, source.getCorrectAnswer()));

    TextImageWrapperContentAssistanceUtil.safeUpdateWrappers(
        this.answerOptions, source.getAnswerOptions());
    this.setProvidedAnswer(
        ContentAssistanceUtil.getSafeUpdatedStringSet(
            this.providedAnswer, source.getProvidedAnswer()));
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
