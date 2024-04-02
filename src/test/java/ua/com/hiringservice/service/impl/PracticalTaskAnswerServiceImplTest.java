package ua.com.hiringservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static ua.com.testsupport.datagenerator.TaskPassingDataGenerator.generateTaskPassingIdPractical;
import static ua.com.testsupport.datagenerator.task.PracticalTaskAnswerDataGenerator.generateAssessPracticalTaskDto;
import static ua.com.testsupport.datagenerator.task.PracticalTaskAnswerDataGenerator.generateEmptyPracticalTaskAnswer;
import static ua.com.testsupport.datagenerator.task.PracticalTaskAnswerDataGenerator.generatePracticalTaskAnswer;
import static ua.com.testsupport.datagenerator.task.PracticalTaskAnswerDataGenerator.generatePracticalTaskAnswerDto;
import static ua.com.testsupport.datagenerator.task.PracticalTaskAnswerDataGenerator.generatePracticalTaskAnswerId1;
import static ua.com.testsupport.datagenerator.task.PracticalTaskAnswerDataGenerator.generateProvidedPracticalTaskAnswerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.com.hiringservice.exception.GradeMustBeLessThanMaxScoreException;
import ua.com.hiringservice.model.dto.task.AssessPracticalTaskDto;
import ua.com.hiringservice.model.dto.task.PracticalTaskAnswerDto;
import ua.com.hiringservice.model.dto.task.ProvidePracticalTaskAnswerDto;
import ua.com.hiringservice.model.entity.task.PracticalTaskAnswer;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.hiringservice.repository.PracticalTaskAnswerRepository;
import ua.com.hiringservice.service.task.impl.PracticalTaskAnswerServiceImpl;
import ua.com.hiringservice.util.mapper.task.PracticalTaskAnswerMapper;

/**
 * This class contains unit tests for the PracticalTaskAnswerServiceImpl class.
 *
 * @author Oleksandr Prokopenko
 * @since 2024-03-17
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class PracticalTaskAnswerServiceImplTest {

  private static final String NOT_EQUALS_EXPECTED = "Actual response dto doesn't equals Expected";
  @Mock private PracticalTaskAnswerRepository practicalTaskAnswerRepository;
  @Mock private PracticalTaskAnswerMapper answerMapper;

  @InjectMocks private PracticalTaskAnswerServiceImpl underTest;

  @Test
  void provideAnswer_shouldReturnAnswerDto_whenOk() {
    final UUID practicalTaskAnswerId = generatePracticalTaskAnswerId1();
    final ProvidePracticalTaskAnswerDto answerDto = generateProvidedPracticalTaskAnswerDto();
    final PracticalTaskAnswer emptyAnswer = generateEmptyPracticalTaskAnswer();
    final PracticalTaskAnswer savedAnswer = generatePracticalTaskAnswer();
    final ProvidePracticalTaskAnswerDto expected = generateProvidedPracticalTaskAnswerDto();

    when(practicalTaskAnswerRepository.findById(practicalTaskAnswerId))
        .thenReturn(Optional.of(emptyAnswer));

    // Create a mock of the answerMapper
    final Answer<Void> updateAnswer =
        invocation -> {
          final PracticalTaskAnswer answer = invocation.getArgument(0);
          final ProvidePracticalTaskAnswerDto answerDto1 = invocation.getArgument(1);

          // Perform modifications to the answer object based on the DTO
          answer.setGitAnswerLink(answerDto1.getGitAnswerLink());
          answer.setComment(answerDto1.getComment());

          // Since it's a void method, return null
          return null;
        };

    // Mock the void method to perform necessary modifications
    doAnswer(updateAnswer).when(answerMapper).updateEntityFromDto(any(), any());

    when(practicalTaskAnswerRepository.save(emptyAnswer)).thenReturn(savedAnswer);

    // Mock the behavior of PracticalTaskAnswerMapper
    when(answerMapper.toDto(any())).thenReturn(expected);

    // Call the method under test
    final ProvidePracticalTaskAnswerDto actual =
        underTest.provideAnswer(practicalTaskAnswerId, answerDto);

    // Verify that PracticalTaskAnswerMapper was called to update the entity from the dto
    verify(answerMapper).updateEntityFromDto(emptyAnswer, answerDto);

    // Verify that the task passing status is set to ANSWERED
    assertEquals(
        PassingStatus.ANSWERED,
        emptyAnswer.getTaskPassing().getStatus(),
        "Answer task passing status doesn't equal 'ANSWERED'");

    // Verify that PracticalTaskAnswerRepository.save() was called
    verify(practicalTaskAnswerRepository).save(emptyAnswer);

    // Verify that the PracticalTaskAnswerMapper was called to convert the saved entity to dto
    verify(answerMapper).toDto(savedAnswer);

    // Assert that the returned ProvidePracticalTaskAnswerDto is not null
    assertNotNull(actual, "Actual value is null!");

    assertEquals(expected, actual, NOT_EQUALS_EXPECTED);
  }

  @Test
  void assessAnswerManually_validInput_assessmentSaved() {
    // Arrange
    final UUID practicalTaskPassingId = generateTaskPassingIdPractical();
    final AssessPracticalTaskDto assessDto = generateAssessPracticalTaskDto();

    final PracticalTaskAnswer answer = generatePracticalTaskAnswer(); // Create a mock answer
    answer.getTaskPassing().setStatus(PassingStatus.ANSWERED);

    when(practicalTaskAnswerRepository.findById(practicalTaskPassingId))
        .thenReturn(Optional.of(answer)); // Mock the findEntityById method
    when(practicalTaskAnswerRepository.save(answer)).thenReturn(answer); // Mock the save method

    // Act
    final AssessPracticalTaskDto actual =
        underTest.assessAnswerManually(practicalTaskPassingId, assessDto);

    // Assert
    verify(practicalTaskAnswerRepository, times(1)).save(answer); // Verify save method is called
    // Assert other conditions if needed

    assertEquals(assessDto, actual, NOT_EQUALS_EXPECTED);
  }

  @Test
  void assessAnswerManually_invalidScore_exceptionThrown() {
    // Arrange
    final UUID practicalTaskPassingId = generateTaskPassingIdPractical();
    final AssessPracticalTaskDto assessDto = generateAssessPracticalTaskDto();

    final PracticalTaskAnswer answer = generatePracticalTaskAnswer(); // Create a mock answer
    answer.getTaskPassing().setStatus(PassingStatus.ANSWERED);
    assessDto.setScore(110); // Assuming an invalid score

    when(practicalTaskAnswerRepository.findById(practicalTaskPassingId))
        .thenReturn(Optional.of(answer)); // Mock the findEntityById method
    // Act and Assert
    assertThrows(
        GradeMustBeLessThanMaxScoreException.class,
        () -> {
          underTest.assessAnswerManually(practicalTaskPassingId, assessDto);
        },
        "Expected GradeMustBeLessThanMaxScoreException.class to be thrown");
  }

  @Test
  void findAll_returnsPageOfPracticalTaskAnswerDto() {
    // Arrange
    final Pageable pageable = PageRequest.of(0, 10); // Example pageable
    final List<PracticalTaskAnswer> answers = new ArrayList<>();
    // Populate answers list with some data
    answers.add(new PracticalTaskAnswer());
    // Mock the repository to return a Page object containing the answers
    when(practicalTaskAnswerRepository.findAll(pageable)).thenReturn(new PageImpl<>(answers));

    // Mock answerMapper to return a dummy DTO
    final PracticalTaskAnswerDto dummyDto = new PracticalTaskAnswerDto(); // Create a dummy DTO
    when(answerMapper.toFullDtoShort(any())).thenReturn(dummyDto);

    // Act
    final Page<PracticalTaskAnswerDto> result = underTest.findAll(pageable);

    // Assert
    // Check the total elements in the page
    assertEquals(1, result.getTotalElements(), "Total elements on page is not equal 1 as expected");
    assertEquals(
        dummyDto,
        result.getContent().get(0),
        "Content of the page differs from expected"); // Check the content of the page
  }

  @Test
  void findById_shouldReturnPracticalTaskAnswerDto() {
    // Arrange
    final UUID practicalTaskAnswerId = generatePracticalTaskAnswerId1(); // Example ID
    final PracticalTaskAnswer mockAnswer = generatePracticalTaskAnswer(); // Create a mock answer
    // Mock the findEntityById method to return the mock answer
    when(practicalTaskAnswerRepository.findById(practicalTaskAnswerId))
        .thenReturn(Optional.of(mockAnswer));

    // Mock answerMapper to return a dummy DTO
    final PracticalTaskAnswerDto dummyDto = generatePracticalTaskAnswerDto(); // Create a dummy DTO
    when(answerMapper.toFullDtoShort(any())).thenReturn(dummyDto);

    // Act
    final PracticalTaskAnswerDto result = underTest.findById(practicalTaskAnswerId);

    // Assert
    assertEquals(
        dummyDto,
        result,
        NOT_EQUALS_EXPECTED); // Check if the returned DTO matches the expected dummy DTO
  }
}
