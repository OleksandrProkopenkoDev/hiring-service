CREATE TABLE tour
(
    id          UUID PRIMARY KEY,
    title       VARCHAR(50)  NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE tour_task
(
    id            UUID PRIMARY KEY,
    tour_id       UUID NOT NULL,
    task_id       UUID NOT NULL,
    index_in_tour INT  NOT NULL,
    weight        INT  NOT NULL,

    CONSTRAINT fk_tour_task_tour FOREIGN KEY (tour_id) REFERENCES tour (id),
    CONSTRAINT fk_tour_task_task FOREIGN KEY (task_id) REFERENCES tasks (id)
);

CREATE TABLE tour_passing
(
    id               UUID PRIMARY KEY,
    keycloak_user_id UUID,
    status           VARCHAR(255),
    score            INTEGER DEFAULT 0,
    tour_id          UUID,
    CONSTRAINT fk_tour_passing_tours FOREIGN KEY (tour_id) REFERENCES tour (id)
);

-- First, drop the foreign key constraint referencing test_tour_id if it exists
ALTER TABLE vacancies
    DROP CONSTRAINT IF EXISTS FK_vacancies_test_tour_id;

-- Drop the old BIGINT 'test_tour_id' column
ALTER TABLE vacancies
    DROP COLUMN IF EXISTS test_tour_id;

-- Add a new UUID column named 'tour_id'
ALTER TABLE vacancies
    ADD COLUMN tour_id UUID;

-- Add a new foreign key constraint referencing the new 'tour_id' column
ALTER TABLE vacancies
    ADD CONSTRAINT FK_vacancies_tour_id FOREIGN KEY (tour_id) REFERENCES tour (id);