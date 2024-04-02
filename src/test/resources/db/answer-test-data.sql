DELETE
FROM answers;
INSERT INTO answers (id, task_passing_id, status, description, sequence,
                     response_duration, duration, weight, content)
VALUES ('2897659a-39f8-4729-ba6f-5404a6cad12b', '27bbec0e-098e-47fe-adc7-5d4b2924d41e',
       'FINISHED', 'answer 1', '1', '15', '20', '5', '{
  "questionDescription": {
    "html": "What is the capital of France?",
    "image": "imageId"
  },
  "answerOptions": [
    {
      "html": "Answer1",
      "image": "imageId"
    },
    {
      "html": "Answer2",
      "image": "imageId"
    }
  ],
  "correctAnswer": "A",
  "providedAnswer": "A",
  "questionType": "SINGLE_CHOICE_TO_APP"
}');
INSERT INTO answers (id, task_passing_id, status, description, sequence,
                     response_duration, duration, weight, content)
VALUES ('341d7c81-9b09-4fd3-9d9b-3cebd24222fa', '27bbec0e-098e-47fe-adc7-5d4b2924d41e', 'FINISHED', 'answer 2', '2', '15', '20', '3', '{
  "questionDescription": {
    "html": "What is the capital of France?",
    "image": "imageId"
  },
  "providedAnswer": "Paris",
  "questionType": "TEXT_TO_MENTOR"
}');

INSERT INTO answers (id, task_passing_id, status, description, sequence,
                     response_duration, duration, weight, content)
VALUES ('a6210ef4-2a78-4f85-8827-22463c6b4617', '27bbec0e-098e-47fe-adc7-5d4b2924d411',
        'FINISHED', 'answer 3', '1', '15', '20', '5', '{
  "questionDescription": {
    "html": "What is the capital of France?",
    "image": "imageId"
  },
  "answerOptions": [
    {
      "html": "Answer1",
      "image": "imageId"
    },
    {
      "html": "Answer2",
      "image": "imageId"
    }
  ],
  "correctAnswer": "A",
  "providedAnswer": "A",
  "questionType": "SINGLE_CHOICE_TO_APP"
}');
INSERT INTO answers (id, task_passing_id, status, description, sequence,
                     response_duration, duration, weight, content)
VALUES ('1b0b8338-991d-4645-ad26-546b8b504432', '27bbec0e-098e-47fe-adc7-5d4b2924d411','FINISHED', 'answer 4', '2', '15', '20', '3', '{
  "questionDescription": {
    "html": "What is the capital of France?",
    "image": "imageId"
  },
  "providedAnswer": "Paris",
  "questionType": "TEXT_TO_MENTOR"
}');

INSERT INTO answers (id, task_passing_id, status, description, sequence,
                     response_duration, duration, weight, content)
VALUES ('430f57e1-b9c4-4a98-890c-62ceccd3fbe1', '27bbec0e-098e-47fe-adc7-5d4b2924d412',
        'FINISHED', 'answer 5', '1', '15', '20', '5', '{
  "questionDescription": {
    "html": "What is the capital of France?",
    "image": "imageId"
  },
  "answerOptions": [
    {
      "html": "Answer1",
      "image": "imageId"
    },
    {
      "html": "Answer2",
      "image": "imageId"
    }
  ],
  "correctAnswer": "A",
  "providedAnswer": "A",
  "questionType": "SINGLE_CHOICE_TO_APP"
}');
INSERT INTO answers (id, task_passing_id, status, description, sequence,
                     response_duration, duration, weight, content)
VALUES ('a632f1fc-1082-407b-8a9d-4349329f081c', '27bbec0e-098e-47fe-adc7-5d4b2924d412', 'FINISHED', 'answer 6', '2', '15', '20', '3', '{
  "questionDescription": {
    "html": "What is the capital of France?",
    "image": "imageId"
  },
  "providedAnswer": "Paris",
  "questionType": "TEXT_TO_MENTOR"
}');