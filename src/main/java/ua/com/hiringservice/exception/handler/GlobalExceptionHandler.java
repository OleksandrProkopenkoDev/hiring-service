package ua.com.hiringservice.exception.handler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ua.com.hiringservice.exception.*;
import ua.com.hiringservice.exception.AnswerInTaskPassingNotFoundException;
import ua.com.hiringservice.exception.AnswerNotFoundException;
import ua.com.hiringservice.exception.ContentTypeNotSpecifiedException;
import ua.com.hiringservice.exception.QuestionNotFoundException;
import ua.com.hiringservice.exception.QuestionWithoutIdException;
import ua.com.hiringservice.exception.TaskAlreadyContainQuestionException;
import ua.com.hiringservice.exception.TaskAlreadyPublishedException;
import ua.com.hiringservice.exception.TaskNotFoundException;
import ua.com.hiringservice.exception.TaskNotPublishedException;
import ua.com.hiringservice.exception.TaskPassingNotFoundException;
import ua.com.hiringservice.exception.TaskQuestionIdCanNotBeNullException;
import ua.com.hiringservice.exception.TaskQuestionIndexInTaskDuplicateException;
import ua.com.hiringservice.exception.TaskQuestionNotFoundException;
import ua.com.hiringservice.exception.TaskQuestionsListSizeException;
import ua.com.hiringservice.exception.TextImageWrapperKeyDeserializeException;
import ua.com.hiringservice.exception.TextImageWrapperSerializeException;
import ua.com.hiringservice.exception.UserAlreadyPassingTaskException;
import ua.com.hiringservice.exception.UserNotFoundException;
import ua.com.hiringservice.exception.VacancyNotFoundException;
import ua.com.hiringservice.exception.model.ErrorResponse;
import ua.com.hiringservice.exception.tour.TaskAlreadyAddedToTourException;
import ua.com.hiringservice.exception.tour.TourNotFoundException;
import ua.com.hiringservice.exception.tour.TourTitleAlreadyExistsException;

/**
 * Global exception handler for handling specific exceptions and providing consistent error
 * responses.
 */
@ControllerAdvice
@SuppressWarnings({"PMD.ExcessiveImports", "PMD.CouplingBetweenObjects", "PMD.TooManyMethods"})
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(VacancyNotFoundException.class)
  public ResponseEntity<String> handleVacancyNotFoundException(VacancyNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(UnableToSaveVerificationException.class)
  public ResponseEntity<String> handleUnableToSendVerificationException(
      UnableToSaveVerificationException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler({
    VerificationNotFoundException.class,
  })
  public ResponseEntity<String> handleVerificationNotFoundException(
      VerificationNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler({
    UnableToSaveImagesException.class,
  })
  public ResponseEntity<String> handleUnableToSaveImagesException(UnableToSaveImagesException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(VerificationAlreadyExistsException.class)
  public ResponseEntity<String> handleVerificationAlreadyExistsException(
      VerificationAlreadyExistsException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler(NoRejectionReasonException.class)
  public ResponseEntity<String> handleNoRejectionReasonException(NoRejectionReasonException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  @ExceptionHandler({
    TourPassingNotFoundException.class,
    PracticalTaskAnswerIsNotFoundException.class,
    PracticalTaskNotFoundException.class,
    QuestionNotFoundException.class,
    TaskNotFoundException.class,
    TaskPassingNotFoundException.class,
    TaskQuestionNotFoundException.class,
    AnswerInTaskPassingNotFoundException.class,
    AnswerMediaNotFoundException.class,
    AnswerNotFoundException.class,
    TourNotFoundException.class
  })
  public ResponseEntity<Object> handleNotFoundException(
      final RuntimeException ex, final WebRequest request) {

    final HttpStatus status = HttpStatus.NOT_FOUND;

    final ErrorResponse errorResponse = buildErrorResponse(ex, (ServletWebRequest) request, status);

    return ResponseEntity.status(status).body(errorResponse);
  }

  @ExceptionHandler({
    UserHasActiveTourPassingException.class,
    InvalidTourTaskOrderException.class,
    TourNotContainTourTaskException.class,
    IllegalTaskTypeException.class,
    GradeMustBeLessThanMaxScoreException.class,
    PassingScoreMustBeLessThanMaxScoreException.class,
    CannotRemovePracticalTaskFromQuizException.class,
    CannotAddPracticalTaskToQuizException.class,
    TaskQuestionBelongsToOtherTaskException.class,
    TaskAlreadyPublishedException.class,
    TaskQuestionIndexInTaskDuplicateException.class,
    TaskAlreadyContainQuestionException.class,
    TaskQuestionsListSizeException.class,
    QuestionWithoutIdException.class,
    TaskQuestionIdCanNotBeNullException.class,
    UserAlreadyPassingTaskException.class,
    TaskNotPublishedException.class,
    AnswerAlreadyAssessedException.class,
    AnswerNotFinishedException.class,
    ContentTypeNotSpecifiedException.class,
    ContentConvertException.class,
    TextImageWrapperKeyDeserializeException.class,
    TextImageWrapperSerializeException.class,
    MarkdownIsNullException.class,
    ChangeTypeInQuestionException.class,
    QuestionAlreadyUsesInTaskQuestion.class,
    AnswerAlreadyProvidedException.class,
    AnswerProvideException.class,
    TaskAlreadyAddedToTourException.class,
    TourTitleAlreadyExistsException.class
  })
  public ResponseEntity<Object> handleBadRequestException(
      final RuntimeException ex, final WebRequest request) {

    final HttpStatus status = HttpStatus.BAD_REQUEST;

    final ErrorResponse errorResponse = buildErrorResponse(ex, (ServletWebRequest) request, status);

    return ResponseEntity.status(status).body(errorResponse);
  }

  private ErrorResponse buildErrorResponse(
      final Exception ex, final ServletWebRequest request, final HttpStatus status) {
    return new ErrorResponse(status.value(), ex.getMessage(), request.getRequest().getRequestURI());
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {
    final Map<String, Object> body = new ConcurrentHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("status", HttpStatus.BAD_REQUEST);
    final List<String> errors =
        ex.getBindingResult().getAllErrors().stream().map(this::getErrorMessage).toList();
    body.put("errors", errors);
    return new ResponseEntity<>(body, headers, status);
  }

  private String getErrorMessage(ObjectError e) {
    if (e instanceof FieldError) {
      final String field = ((FieldError) e).getField();
      final String message = e.getDefaultMessage();
      return field + " " + message;
    }
    return e.getDefaultMessage();
  }
}
