create table questions
(
    id                  bigserial primary key,
    type                varchar(30) not null,
    difficulty          int         not null,
    duration_in_seconds int         not null,
    weight              int         not null,
    details             varchar     not null,
    created_at          timestamptz not null,
    updated_at          timestamptz,
    is_deleted          boolean     not null
);

create table quiz_templates
(
    id            bigserial primary key,
    name          varchar(255) not null,
    is_repeatable boolean,
    passing_score integer
);

create table quiz_templates_questions
(
    quiz_template_id bigint not null references quiz_templates (id),
    question_id      bigint not null references questions (id),
    unique (quiz_template_id, question_id)
);

create table user_quizzes
(
    id               bigserial primary key,
    user_id          bigint null,
    quiz_template_id bigint not null references quiz_templates (id),
    result           integer,
    status           varchar(255) check (status in
                                         ('UNDONE', 'CHECKING', 'PASSED', 'FAILED', 'AMNESTY_ONCE'))
);

create table user_quiz_answers
(
    id                   bigserial primary key,
    type                 varchar(30) not null,
    is_remote_assessable boolean     not null,
    is_assessed          boolean     not null,
    grade                int,
    quiz_id              bigint      not null references user_quizzes (id),
    question_id          bigint      not null references questions (id),
    details              varchar
);

create table if not exists vacancies
(
    id              bigserial primary key,
    is_open         boolean,
    department      varchar(255),
    employment_type varchar(255) check (employment_type in
                                        ('FULL_TIME', 'PART_TIME', 'WORK_PLACEMENT',
                                         'REMOTE_WORKING',
                                         'FLEXIBLE_SCHEDULE', 'SHIFT_SCHEDULE')),
    title           varchar(255),
    is_deleted      boolean default false
);

create table if not exists users_vacancies
(
    user_id    integer not null,
    vacancy_id bigint  not null,
    primary key (user_id, vacancy_id),
    foreign key (vacancy_id) references vacancies (id),
    foreign key (user_id) references users (id)
);

create table if not exists vacancies_quiz_template
(
    quiz_template_id bigint not null,
    vacancy_id       bigint not null,
    primary key (quiz_template_id, vacancy_id),
    foreign key (quiz_template_id) references quiz_templates (id),
    foreign key (vacancy_id) references vacancies (id)
);






