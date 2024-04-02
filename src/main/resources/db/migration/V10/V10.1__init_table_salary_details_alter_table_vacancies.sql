create table if not exists salary_details
(
    id          SERIAL PRIMARY KEY,
    currency    VARCHAR(255),
    time_period VARCHAR(255),
    lower_bound INT,
    upper_bound INT,
    vacancy_id  UUID,
    FOREIGN KEY (vacancy_id) REFERENCES vacancies (id)
);

alter table vacancies
    drop column currency,
    drop column time_period,
    drop column lower_bound,
    drop column upper_bound;

alter table vacancies
    add column salary_details_id BIGINT;

alter table vacancies
    add constraint salary_details_fk
        foreign key (salary_details_id) references salary_details (id)
        ON DELETE CASCADE;