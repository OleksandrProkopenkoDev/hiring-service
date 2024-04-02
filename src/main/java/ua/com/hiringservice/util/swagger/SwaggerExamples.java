package ua.com.hiringservice.util.swagger;

/** Replace this stub by correct Javadoc. */
public final class SwaggerExamples {

  public static final String RESPONSE_CODE_200 = "200";
  public static final String RESPONSE_CODE_201 = "201";
  public static final String RESPONSE_CODE_204 = "204";
  public static final String RESPONSE_CODE_400 = "400";
  public static final String RESPONSE_CODE_401 = "401";
  public static final String RESPONSE_CODE_403 = "403";
  public static final String RESPONSE_CODE_404 = "404";
  public static final String RESPONSE_CODE_409 = "409";
  public static final String UNAUTHORIZED = "Unauthorized";

  public static final String ERROR_400 =
      """
          {
              "status": 400,
              "timestamp": "01-01-2023 00:00:00",
              "message": "Such resource already exist",
              "path": "/api/*"
          }""";

  public static final String ERROR_401 =
      """
          {
             "status": 401,
             "timestamp": "01-01-2023 00:00:00",
             "message": "Full authentication is required to access this resource",
             "path": "/api/*"
          }""";
  public static final String ERROR_403 =
      """
          {
              "status": 403,
              "timestamp": "01-01-2023 00:00:00",
              "message": "Not access allowed to resource",
              "path": "/api/*"
          }""";

  public static final String ERROR_404 =
      """
          {
              "status": 404,
              "timestamp": "01-01-2023 00:00:00",
              "message": "Entity not found.",
              "path": "/api/*"
          }""";

  public static final String ERROR_500 =
      """
          {
              "status": 500,
              "timestamp": "01-01-2023 00:00:00",
              "message": "The file cannot be accessed.",
              "path": "/api/*"
          }""";

  public static final String TOKEN = """
      {
          "token": "Very big value"
      }""";

  public static final String TOKEN_ERROR_401 =
      """
          {
              "status": 401,
              "timestamp": "01-01-2023 00:00:00",
              "message": "User with email: badmail@mail.com doesn't exist.",
              "path": "/login"
          }""";

  public static final String FEEDBACKOPTIONS_EXAMPLE =
      """
          {
             "VERY_HARD: "Too difficult",
             "HARD": "Hard, but I don't look for easy ways",
             "MEDIUM": "Not difficult, but not very easy",
             "EASY": Easy, is it supposed to be difficult?",
             "VERY_EASY": "Too easy"
          }""";

  public static final String ERROR_409 =
      """
            {
            "status": 409,
            "timestamp": "01-01-2023 00:00:00",
            "message": "The requested resource is already in use.",
            "path": /api/*"
          }""";
  public static final String ANSWER_FOR_USER =
      """
      {
        "id": "{$AnswerId}",
        "duration": 120,
        "description": "description about",
        "sequence": 3,
        "type": "SINGLE_CHOICE_TO_APP",
        "content": {
          "questionType":"SINGLE_CHOICE_TO_APP",
          "questionDescription": {
            "markdown": null,
            "image": "{$image_ID}"
          },
          "answerOptions": [
            {
              "markdown": null,
              "image": "{$image_ID}"
            },
            {
              "markdown": null,
              "image": "{$image_ID}"
            }
          ],
          "correctAnswer": "",
          "providedAnswer": "Some answer"
        }
      }
      """;
  public static final String QUIZ_FOR_QUIZ_PASSING =
      """
      {
        "id": "5ce43472-30c4-4722-b743-02503b6d2535",
        "title": "NewQuiz14",
        "description": "description new quiz14",
        "passingScore": 0.68,
        "totalQuestion": 1,
        "totalDuration": 50,
        "createdAt": "2024-01-25T21:41:25.021306Z",
        "updatedAt": "2024-01-25T21:41:25.021306Z",
        "published": false,
        "taskQuestions": null
      }
      """;
  public static final String PAGEABLE =
      """
      {
        "page": 0,
        "size": 10,
        "sort": [
          "duration"
        ]
      }""";

  private SwaggerExamples() {}
}
