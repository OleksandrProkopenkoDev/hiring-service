ALTER TABLE questions
    RENAME COLUMN description TO comment;

ALTER TABLE quizzes_questions
    RENAME COLUMN sequence TO index_in_quiz;
