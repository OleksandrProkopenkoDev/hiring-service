DELETE
FROM questions;
INSERT INTO questions (id, comment, duration, content)
VALUES ('baa8bb7d-9533-4483-bdb4-204efba55e7a', 'comment', 50, '{
  "questionDescription": {
    "html": "What is the capital of France?",
    "image": "paris_image_url.jpg"
  },
  "answerOptions": [
    {
      "html": "Answer1",
      "image": "answer_image1_url.jpg"
    },
    {
      "html": "Answer2",
      "image": "answer_image1_url.jpg"
    }
  ],
  "correctAnswer": "A",
  "providedAnswer": "A",
  "questionType": "SINGLE_CHOICE_TO_APP"
}');

INSERT INTO questions (id, comment, duration, content)
VALUES ('2bc5530e-0eb3-4ed7-8f74-cbc4cbf7b5bb', 'comment', 60, '{
  "questionDescription": {
    "html": "What is the capital of France?",
    "image": "paris_image_url.jpg"
  },
  "answerOptions": [
    {
      "html": "Answer1",
      "image": "answer_image1_url.jpg"
    },
    {
      "html": "Answer2",
      "image": "answer_image1_url.jpg"
    }
  ],
  "correctAnswer": [
    "A",
    "B"
  ],
  "providedAnswer": [
    "A",
    "B"
  ],
  "questionType": "MULTIPLE_CHOICE_TO_APP"
}');

INSERT INTO questions (id, comment, duration, content)
VALUES ('570f93b5-485c-42ea-82f9-8c866ac0e4c4', 'comment', 40, '{
  "questionDescription": {
    "html": "What is the capital of France?",
    "image": "paris_image_url.jpg"
  },
  "answerOptions": [
    {
      "html": "Answer1",
      "image": "answer_image1_url.jpg"
    },
    {
      "html": "Answer2",
      "image": "answer_image1_url.jpg"
    }
  ],
  "correctAnswer": "A",
  "providedAnswer": "A",
  "questionType": "SINGLE_CHOICE_TO_APP"
}');

INSERT INTO questions (id, comment, duration, content)
VALUES ('024890e3-c883-4049-947b-8af2f9a6bfdf', 'comment', 70, '{
  "questionDescription": {
    "html": "What is the capital of France?",
    "image": "paris_image_url.jpg"
  },
  "answerOptions": {
    "htmlImageWrapper(html=Answer1, image=answer_image1_url.jpg)": {
      "html": "Maybe capital of France is Paris?",
      "image": "paris_image_url.jpg"
    },
    "htmlImageWrapper(html=Answer2, image=answer_image1_url.jpg)": {
      "html": "Maybe capital of France is?",
      "image": "image_url.jpg"
    }
  },
  "correctAnswer": {
    "A": "Paris",
    "B": "Pari"
  },
  "providedAnswer": {
    "A": "Paris",
    "B": "Pari"
  },
  "questionType": "MATCHING_TO_APP"
}');

INSERT INTO questions (id, comment, duration, content)
VALUES ('e4f282d8-4308-44e8-a47c-687e556ead24','comment', 20, '{
  "questionDescription": {
    "html": "What is the capital of France?",
    "image": "paris_image_url.jpg"
  },
  "answerOptions": {
    "htmlImageWrapper(html=Answer1, image=answer_image1_url.jpg)": {
      "html": "Maybe capital of France is Paris?",
      "image": "paris_image_url.jpg"
    },
    "htmlImageWrapper(html=Answer2, image=answer_image1_url.jpg)": {
      "html": "Maybe capital of France is?",
      "image": "image_url.jpg"
    }
  },
  "correctAnswer": {
    "A": "Paris",
    "B": "Pari"
  },
  "providedAnswer": {
    "A": "Paris",
    "B": "Pari"
  },
  "questionType": "MATCHING_TO_APP"
}');

