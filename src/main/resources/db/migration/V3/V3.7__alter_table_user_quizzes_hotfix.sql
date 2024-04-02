ALTER TABLE user_quizzes
    ADD CONSTRAINT user_quizzes_user_id_fkey
        FOREIGN KEY (user_id) REFERENCES users (id);
