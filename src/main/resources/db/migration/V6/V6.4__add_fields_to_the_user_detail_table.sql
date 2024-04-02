ALTER TABLE user_details
    ADD COLUMN ip_address VARCHAR(255),
    ADD COLUMN timestamp TIMESTAMP,
    ADD COLUMN is_terms_and_conditions_accepted BOOLEAN NOT NULL DEFAULT FALSE;