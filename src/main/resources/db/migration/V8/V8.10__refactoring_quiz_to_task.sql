ALTER TABLE vacancies_quiz
DROP
CONSTRAINT IF EXISTS quiz_id,
  DROP
COLUMN IF EXISTS quiz_id;

DROP TABLE exam_tour_task;
DROP TABLE answers;
DROP TABLE quiz_passing;
DROP TABLE quizzes_questions;
DROP TABLE quizzes;
DROP TABLE questions;

CREATE TABLE tasks
(
    id             uuid PRIMARY KEY,
    title          VARCHAR(255)             NOT NULL,
    type         VARCHAR(32)              NOT NULL,
    description    VARCHAR(255),
    passing_score  INT,
    created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total_question INT,
    total_duration BIGINT,
    published      BOOLEAN,
    published_at   TIMESTAMP WITH TIME ZONE,
    comment        VARCHAR,
    total_weight   integer
);

CREATE TABLE task_passings
(
    id               UUID PRIMARY KEY,
    status           VARCHAR(32) NOT NULL,
    score            INT,
    keycloak_user_id UUID        NOT NULL,
    task_id          UUID        ,
    FOREIGN KEY (task_id) REFERENCES tasks (id)
);

CREATE TABLE answers
(
    id                uuid PRIMARY KEY,
    task_passing_id   UUID        NOT NULL,
    status            VARCHAR(32) NOT NULL,
    score             INT,
    comment           VARCHAR(255),
    content           JSONB       NOT NULL,
    description       VARCHAR,
    index_in_task     INTEGER     NOT NULL,
    response_duration INTEGER,
    duration          INTEGER     NOT NULL,
    weight            INT,
    FOREIGN KEY (task_passing_id) REFERENCES task_passings (id)
);

CREATE TABLE questions
(
    id       uuid PRIMARY KEY,
    duration BIGINT NOT NULL,
    comment  VARCHAR(255),
    content  JSONB  NOT NULL
);

CREATE TABLE task_questions
(
    id            uuid PRIMARY KEY,
    task_id       uuid,
    question_id   uuid NOT NULL,
    weight        int  NOT NULL,
    index_in_task int  NOT NULL,
    UNIQUE (task_id, question_id),
    FOREIGN KEY (task_id) REFERENCES tasks (id),
    FOREIGN KEY (question_id) REFERENCES questions (id)
);

ALTER TABLE vacancies_quiz
    ADD COLUMN task_id uuid,
  ADD CONSTRAINT task_id FOREIGN KEY (task_id) REFERENCES tasks (id);