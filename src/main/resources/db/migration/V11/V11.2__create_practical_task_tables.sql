-- db/migration/V1__create_practical_task_tables.sql

-- Create the practical_task table
CREATE TABLE practical_task
(
    id        UUID PRIMARY KEY,
    task_text TEXT
);

-- Create the practical_task_image_ids table
CREATE TABLE practical_task_image_ids
(
    practical_task_id UUID,
    image_id          VARCHAR(255),
    FOREIGN KEY (practical_task_id) REFERENCES practical_task (id),
    PRIMARY KEY (practical_task_id, image_id)
);

CREATE TABLE practical_task_passing_history
(
    id                UUID PRIMARY KEY,
    user_id           UUID NOT NULL,
    practical_task_id UUID,
    status            VARCHAR(255),
    git_answer_link   VARCHAR(255),
    score             INTEGER,
    max_score         INTEGER,
    comment           VARCHAR(255),
    FOREIGN KEY (practical_task_id) REFERENCES practical_task (id)
);

-- Add a new column for practical_task_id
ALTER TABLE tasks
    ADD COLUMN practical_task_id UUID;

-- Add foreign key constraint
ALTER TABLE tasks
    ADD CONSTRAINT FK_tasks_practical_task
        FOREIGN KEY (practical_task_id)
            REFERENCES practical_task (id)
            ON DELETE CASCADE;
