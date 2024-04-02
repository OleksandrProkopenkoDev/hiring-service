create table quizzes
(
    id            bigserial primary key,
    title         varchar(255) not null,
    description   text,
    status        varchar(255) check (status in
                                      ('UNDONE', 'CHECKING', 'PASSED', 'FAILED', 'AMNESTY_ONCE')),
    score         integer,
    passing_score integer,
    created_at    timestamptz  not null,
    updated_at    timestamptz,
    is_deleted    boolean default false,
    is_published  boolean,
    is_repeatable boolean
);

create table answers
(
    id         bigserial not null primary key,
    details    varchar,
    is_deleted boolean default false
);

create table question_answers
(
    id          bigserial not null primary key,
    answer_id   bigint,
    question_id bigint,
    quiz_id     bigint,
    foreign key (answer_id) references answers,
    foreign key (question_id) references questions,
    foreign key (quiz_id) references quizzes
);

create table vacancies_quiz
(
    quiz_id    bigint not null,
    vacancy_id uuid   not null,
    primary key (quiz_id, vacancy_id),
    foreign key (quiz_id) references quizzes (id),
    foreign key (vacancy_id) references vacancies (id)
);


