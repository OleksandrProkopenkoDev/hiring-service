package ua.com.testsupport.constant;

import ua.com.hiringservice.model.entity.task.Task;
import ua.com.hiringservice.model.entity.vacancy.Vacancy;

/**
 * Class for keeping Paths to sql scripts.
 *
 * @author Artyom Kondratenko
 * @implNote To add new Constant, follow the pattern: {SQL_PATH + "scriptName" + POSTFIX}
 * @since 12/16/23
 */
public final class TestDataConstant {

  private static final String SQL_PATH = "classpath:/db/";
  private static final String POSTFIX = ".sql";

  /** Sql script for testing TestTour. Included data: {@link Task}, {@link Vacancy}. */
  public static final String TEST_TOUR_SQL = SQL_PATH + "insertTestTourData" + POSTFIX;

  public static final String QUESTIONS_DATA_SQL = SQL_PATH + "question-test-data" + POSTFIX;
  public static final String TASK_DATA_SQL = SQL_PATH + "task-test-data" + POSTFIX;
  public static final String TASK_QUESTIONS_DATA_SQL =
      SQL_PATH + "task-question-test-data" + POSTFIX;
  public static final String TASK_PASSING_REPOSITORY_SQL =
      SQL_PATH + "task-passing-repository-test-data" + POSTFIX;

  public static final String ANSWER_DATA_SQL = SQL_PATH + "answer-test-data" + POSTFIX;
}
