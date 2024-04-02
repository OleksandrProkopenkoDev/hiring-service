create TABLE user_application_processes (
    id SERIAL PRIMARY KEY,
    is_deleted BOOLEAN DEFAULT false
);

create TABLE user_application_process_steps (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    step_status_name VARCHAR(255) NOT NULL,
    application_process_id BIGINT,
    FOREIGN KEY (application_process_id) REFERENCES user_application_processes (id)
);

create TABLE user_application_process_child_steps (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    child_step_status_name VARCHAR(255) NOT NULL,
    parent_step_id BIGINT,
    FOREIGN KEY (parent_step_id) REFERENCES user_application_process_steps (id)
);

