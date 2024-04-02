package ua.com.hiringservice.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static ua.com.testsupport.datagenerator.AnswerDataGenerator.getDto;
import static ua.com.testsupport.datagenerator.AnswerDataGenerator.getEntity;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ua.com.hiringservice.exception.AnswerAlreadyAssessedException;
import ua.com.hiringservice.exception.AnswerNotFinishedException;
import ua.com.hiringservice.model.content.questioncontent.LongTextQuestionContent;
import ua.com.hiringservice.model.content.questioncontent.MatchingTestQuestionContent;
import ua.com.hiringservice.model.dto.task.AnswerDto;
import ua.com.hiringservice.model.entity.task.Answer;
import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.repository.AnswerRepository;
import ua.com.hiringservice.service.task.impl.AnswerServiceImpl;
import ua.com.hiringservice.service.task.impl.AssessmentServiceImpl;
import ua.com.hiringservice.util.mapper.task.AnswerMapper;

/**
 * Pmd! hope you like it!! Amazing test class for util test how exactly work one pure method from
 * {@link AnswerServiceImpl}.
 *
 * @author Zakhar Kuropiatnyk
 */
@SpringBootTest(classes = AssessmentServiceImpl.class)
class AnswerServiceTest {
  @Autowired private AssessmentServiceImpl assessmentService;
  @MockBean private AnswerRepository answerRepository;
  @MockBean private AnswerMapper answerMapper;

  @Test
  void assessAnswerManually_shouldReturnActualStatusEqualsToExpected_providedCorrectAnswer() {
    final Answer answer = getEntity();
    final AnswerDto answerDto = getDto();
    answerDto.setStatus(PassingStatus.GRADED);
    final UUID answerId = answer.getId();
    final int grade = 100;
    final String comment = "Some Comment";
    final PassingStatus expected = PassingStatus.GRADED;

    when(answerRepository.findById(answerId)).thenReturn(Optional.of(answer));
    when(answerMapper.toDto(any(Answer.class))).thenReturn(answerDto);
    when(answerRepository.save(any(Answer.class))).thenReturn(answer);

    final PassingStatus actual =
        assessmentService.assessAnswerManually(answerId, grade, comment).getStatus();

    verify(answerRepository).findById(answerId);
    verify(answerMapper).toDto(answer);
    verify(answerRepository, times(2)).save(any(Answer.class));

    assertEquals(expected, actual, "Ecpected Answer status must be equals to actual");
  }

  @Test
  void
      assessAnswerManually_shouldThrowAnswerAlreadyAssessedException_providedStatusAssessmentDone() {
    final Answer answer = getEntity();
    final AnswerDto answerDto = getDto();
    answer.setStatus(PassingStatus.GRADED);
    answerDto.setStatus(PassingStatus.GRADED);
    final UUID answerId = answer.getId();
    final int grade = 100;
    final String comment = "Some Comment";

    when(answerRepository.findById(answerId)).thenReturn(Optional.of(answer));

    assertThrows(
        AnswerAlreadyAssessedException.class,
        () -> assessmentService.assessAnswerManually(answerId, grade, comment),
        "Should Throw AnswerAlreadyAssessedException");

    verify(answerRepository).findById(answerId);
  }

  @Test
  void assessAnswerManually_shouldThrowAnswerNotFinishedException_providedStatusInProgress() {
    final Answer answer = getEntity();
    final AnswerDto answerDto = getDto();
    answerDto.setStatus(PassingStatus.IN_PROGRESS);
    answer.setStatus(PassingStatus.IN_PROGRESS);
    final UUID answerId = answer.getId();
    final int grade = 100;
    final String comment = "Some Comment";

    when(answerRepository.findById(answerId)).thenReturn(Optional.of(answer));

    assertThrows(
        AnswerNotFinishedException.class,
        () -> assessmentService.assessAnswerManually(answerId, grade, comment),
        "Should Throw AnswerNotFinishedException");

    verify(answerRepository).findById(answerId);
  }

  @Test
  void testAssessAnswerIfAutoAssessable_shouldSetScoreAndStatus() {

    final Integer quizTotalWeight = 100;
    final Integer answerWeight = 80;
    final Integer contentGrade = 50;

    final PassingStatus expectedManualStatus = PassingStatus.ANSWERED;
    final PassingStatus expectedAutoStatus = PassingStatus.GRADED;
    final Integer expectedAutoScore = 40; // weight / quizTotalWeight * grade

    final MatchingTestQuestionContent mockAutoContent = mock(MatchingTestQuestionContent.class);
    final TaskPassing mockTaskPassing = mock(TaskPassing.class);
    final Task mockTask = mock(Task.class);

    final Answer mockAutoAnswer = mock(Answer.class);

    when(mockTaskPassing.getTask()).thenReturn(mockTask);
    when(mockTask.getTotalWeight()).thenReturn(quizTotalWeight);
    when(mockAutoAnswer.getWeight()).thenReturn(answerWeight);
    when(mockAutoAnswer.getContent()).thenReturn(mockAutoContent);
    when(mockAutoAnswer.getTaskPassing()).thenReturn(mockTaskPassing);
    when(mockAutoContent.calculateGrade()).thenReturn(contentGrade);

    final Answer manualAnswer = new Answer();
    final LongTextQuestionContent manualContent = new LongTextQuestionContent();
    manualAnswer.setStatus(PassingStatus.ANSWERED);
    manualAnswer.setContent(manualContent);

    assessmentService.assessAnswerIfAutoAssessable(manualAnswer);
    assessmentService.assessAnswerIfAutoAssessable(mockAutoAnswer);

    assertEquals(expectedManualStatus, manualAnswer.getStatus(), "Manual status mismatch");
    verify(mockAutoAnswer).setStatus(expectedAutoStatus);
    verify(mockAutoAnswer).setScore(expectedAutoScore);
  }
}
