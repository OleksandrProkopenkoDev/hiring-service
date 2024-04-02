-- Step 1: Remove the columns
ALTER TABLE practical_task_passing_history
    DROP COLUMN status,
    DROP COLUMN practical_task_id,
    DROP COLUMN user_id,
    DROP COLUMN score;

-- Step 2: Add the new column as a foreign key
ALTER TABLE practical_task_passing_history
    ADD COLUMN task_passing_id UUID,
    ADD CONSTRAINT fk_task_passing_id FOREIGN KEY (task_passing_id) REFERENCES task_passings (id);

-- Step 3: Rename the table
ALTER TABLE practical_task_passing_history
    RENAME TO practical_task_answer;