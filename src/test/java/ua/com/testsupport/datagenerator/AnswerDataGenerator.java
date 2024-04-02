package ua.com.testsupport.datagenerator;

import java.util.UUID;
import ua.com.hiringservice.model.content.Content;
import ua.com.hiringservice.model.dto.task.AnswerDto;
import ua.com.hiringservice.model.entity.task.Answer;
import ua.com.hiringservice.model.entity.task.TaskPassing;
import ua.com.hiringservice.model.enums.PassingStatus;
import ua.com.testsupport.datagenerator.contentdata.SingleChoiceTestQuestionContentData;

/**
 * Class generated data for test cases where used {@link Answer} and {@link AnswerDto}.
 *
 * @author Zakhar Kuropiatnyk.
 */
public class AnswerDataGenerator {

  private static final UUID DEFAULT_ID = UUID.randomUUID();
  private static final TaskPassing DEFAULT_QIUZ_PASSING = TaskPassingDataGenerator.getEntity();
  private static final Content DEFAULT = SingleChoiceTestQuestionContentData.getJsonMatchInstance();
  private static final Integer DEFAULT_SEQUENCE = 1;
  private static final Integer DEFAULT_WEIGHT = 5;
  private static final PassingStatus DEFAUL_STATUS = PassingStatus.ANSWERED;

  public static AnswerDto getDto() {
    return AnswerDto.builder()
        .id(DEFAULT_ID)
        .status(DEFAUL_STATUS)
        .weight(DEFAULT_WEIGHT)
        .content(DEFAULT)
        .indexInTask(DEFAULT_SEQUENCE)
        .build();
  }

  public static Answer getEntity() {
    return Answer.builder()
        .id(DEFAULT_ID)
        .status(DEFAUL_STATUS)
        .weight(DEFAULT_WEIGHT)
        .content(DEFAULT)
        .indexInTask(DEFAULT_SEQUENCE)
        .taskPassing(DEFAULT_QIUZ_PASSING)
        .build();
  }
}
