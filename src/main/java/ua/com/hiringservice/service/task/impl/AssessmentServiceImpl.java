package ua.com.hiringservice.service.task.impl;

import jakarta.transaction.Transactional;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ua.com.hiringservice.exception.AnswerAlreadyAssessedException;
import ua.com.hiringservice.exception.AnswerNotFinishedException;
import ua.com.hiringservice.exception.AnswerNotFoundException;
import ua.com.hiringservice.exception.GradeMustBeLessThanMaxScoreException;
import ua.com.hiringservice.model.content.AutoAssessable;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.dto.task.AnswerDto;
import ua.com.hiringservice.model.entity.task.Answer;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.repository.AnswerRepository;
import ua.com.hiringservice.service.task.AssessmentService;
import ua.com.hiringservice.util.mapper.task.AnswerMapper;

/**
 * Implementation of Assessment Service.
 *
 * @author Vladislav Sauliak
 */
@Service
@AllArgsConstructor
public class AssessmentServiceImpl implements AssessmentService {

  private final AnswerRepository answerRepository;
  private final AnswerMapper answerMapper;

  @Override
  @Transactional
  public void assessAnswerIfAutoAssessable(Answer answer) {
    final Content content = answer.getContent();

    if (content instanceof AutoAssessable) {
      assessAnswerAutomatically(answer, (AutoAssessable) content);
      answer.getTaskPassing().setScore(answer.getTaskPassing().getScore() + answer.getScore());
    }

    answerRepository.save(answer);
  }

  @Override
  @Transactional
  public AnswerDto assessAnswerManually(final UUID id, Integer grade, final String comment) {
    Integer validGrade = grade;
    if (grade < 0) {
      validGrade = 0;
    }
    final Answer answer = findAnswerById(id);

    validateAnswer(id, comment, validGrade, answer);

    answer.setScore(getScore(answer, validGrade));
    answer.getTaskPassing().setScore(answer.getTaskPassing().getScore() + answer.getScore());
    answer.setStatus(PassingStatus.GRADED);

    final AnswerDto answerDto = answerMapper.toDto(answerRepository.save(answer));

    if (allQuestionsAreAnswered(answer)) {
      answer.getTaskPassing().setStatus(PassingStatus.GRADED);
      answerRepository.save(answer);
    }

    return answerDto;
  }

  /**
   * Grade - it's an assess without answer(question) weight. Accounting(0-100). Score =
   * (weight/weightSum) * Grade Example - Answer(question) weight = 5. - Weight sum of all quiz = 10
   * - Answer completely correct(100) Score = (5/10) * 100 = 50
   *
   * @param answer - Answer for which we need to calculate the score
   * @param grade Grade - it's an assess without answer(question) weight. Accounting(0-100).
   * @return answer's score
   */
  private Integer getScore(final Answer answer, final int grade) {
    final float weight = answer.getWeight();
    final float quizTotalWeight = getAnswersQuizTotalWeight(answer);

    return useAnswerScoreFormula(grade, weight, quizTotalWeight);
  }

  private Answer findAnswerById(final UUID id) {
    return answerRepository.findById(id).orElseThrow(() -> new AnswerNotFoundException(id));
  }

  private void assessAnswerAutomatically(Answer answer, AutoAssessable content) {

    final Integer grade = content.calculateGrade();
    final String explanation = content.generateComment(grade);
    final Integer score = getScore(answer, grade);

    answer.setScore(score);
    answer.setComment(explanation);
    answer.setStatus(PassingStatus.GRADED);
  }

  private int useAnswerScoreFormula(int grade, float weight, float quizTotalWeight) {
    return Math.round((weight / quizTotalWeight) * grade);
  }

  private int getAnswersQuizTotalWeight(final Answer answer) {
    return answer.getTaskPassing().getTask().getTotalWeight();
  }

  private void validateAnswer(UUID id, String comment, Integer validGrade, Answer answer) {
    if (validGrade > answer.getContent().getMaxGrade()) {
      throw new GradeMustBeLessThanMaxScoreException(validGrade, answer.getContent().getMaxGrade());
    }

    if (answer.getStatus().equals(PassingStatus.GRADED)) {
      throw new AnswerAlreadyAssessedException(id);
    }
    if (!answer.getStatus().equals(PassingStatus.ANSWERED)) {
      throw new AnswerNotFinishedException(id);
    }
    if (!comment.isEmpty()) {
      answer.setComment(comment);
    }
  }

  private boolean allQuestionsAreAnswered(Answer answer) {
    return answerRepository
        .findAllByTaskPassingIdAndStatus(answer.getTaskPassing().getId(), PassingStatus.ANSWERED)
        .isEmpty();
  }
}
