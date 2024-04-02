-- Drop the 'not null' constraint for the 'tour_id' field in the 'tour_task' table
ALTER TABLE tour_task
    ALTER COLUMN tour_id DROP NOT NULL;