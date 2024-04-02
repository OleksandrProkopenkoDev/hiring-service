-- trigger for saving or updating practical task
-- checks if task type equals QUIZ then deny insert
CREATE OR REPLACE FUNCTION validate_practical_task_id()
    RETURNS TRIGGER AS
$$
BEGIN
    IF NEW.type = 'QUIZ' AND NEW.practical_task_id IS NOT NULL THEN
        RAISE EXCEPTION 'For QUIZ tasks, practical_task_id should be null';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER enforce_practical_task_id
    BEFORE INSERT OR UPDATE
    ON tasks
    FOR EACH ROW
EXECUTE FUNCTION validate_practical_task_id();


-- trigger for saving or updating task questions
-- checks if task type equals QUIZ then allow insert
CREATE OR REPLACE FUNCTION validate_task_type_for_questions()
    RETURNS TRIGGER AS
$$
BEGIN
    IF (SELECT type FROM tasks WHERE id = NEW.task_id) = 'PRACTICAL_TASK' THEN
        RAISE EXCEPTION 'Cannot add questions to a PRACTICAL_TASK';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER enforce_task_type
    BEFORE INSERT OR UPDATE
    ON task_questions
    FOR EACH ROW
EXECUTE FUNCTION validate_task_type_for_questions();

