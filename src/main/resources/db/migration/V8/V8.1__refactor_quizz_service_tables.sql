ALTER TABLE vacancies_quiz
  DROP CONSTRAINT IF EXISTS quiz_id,
  DROP COLUMN IF EXISTS quiz_id;

ALTER TABLE exam_tour_task
  DROP CONSTRAINT IF EXISTS quiz_id,
  DROP COLUMN IF EXISTS quiz_id;

DROP TABLE IF EXISTS question_answers;
DROP TABLE IF EXISTS quizz_parts;
DROP TABLE IF EXISTS answers;
DROP TABLE IF EXISTS quiz_question;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS quizzes;


CREATE TABLE quizzes(
  id uuid PRIMARY KEY,
  title VARCHAR(255) NOT NULL,
  description VARCHAR(255),
  passing_score DECIMAL(3,2),
  created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
  total_question INT,
  total_duration INT,
  published      BOOLEAN
  );

ALTER TABLE vacancies_quiz
  ADD COLUMN quiz_id uuid,
  ADD CONSTRAINT quiz_id FOREIGN KEY (quiz_id) REFERENCES quizzes (id);

ALTER TABLE exam_tour_task
  ADD COLUMN quiz_id uuid,
  ADD CONSTRAINT quiz_id FOREIGN KEY (quiz_id) REFERENCES quizzes (id);


CREATE TABLE quiz_passing (
  id UUID PRIMARY KEY,
  status VARCHAR(32) NOT NULL,
  score DECIMAL(3,2),
  keycloak_user_id UUID NOT NULL,
  quiz_id UUID NOT NULL,
  FOREIGN KEY (quiz_id) REFERENCES quizzes(id)
  );

CREATE TABLE answers(
  id uuid PRIMARY KEY,
  quiz_passing_id UUID NOT NULL,
  type VARCHAR(32) NOT NULL,
  status VARCHAR(32) NOT NULL,
  score DECIMAL(3,2),
  comment VARCHAR(255),
  content VARCHAR NOT NULL,
  description VARCHAR,
  sequence INTEGER NOT NULL,
  response_duration INTEGER,
  duration INTEGER NOT NULL,
  FOREIGN KEY (quiz_passing_id) REFERENCES quiz_passing(id)
  );

CREATE TABLE questions(
  id uuid PRIMARY KEY,
  type VARCHAR(32),
  duration int NOT NULL,
  content varchar NOT NULL,
  description VARCHAR
  );

CREATE TABLE quizzes_questions (
  id uuid PRIMARY KEY,
  quiz_id uuid,
  question_id uuid NOT NULL,
  weight int NOT NULL,
  sequence int NOT NULL,
  UNIQUE (quiz_id, question_id),
  FOREIGN KEY (quiz_id) REFERENCES quizzes(id),
  FOREIGN KEY (question_id) REFERENCES questions(id)
  );

