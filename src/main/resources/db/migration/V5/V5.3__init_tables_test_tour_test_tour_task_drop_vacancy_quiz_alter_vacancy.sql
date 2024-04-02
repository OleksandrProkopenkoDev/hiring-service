create table if not exists test_tour
(
    id          bigserial primary key,
    title       varchar(255) not null,
    description text,
    is_deleted  boolean default false
);

create table if not exists test_tour_task
(
    id           bigserial primary key,
    test_tour_id bigint,
    task_type    varchar(255) check (task_type in ('QUIZ', 'PRACTICAL_TASK')),
    is_deleted   boolean default false,
    quiz_id      bigint,
    constraint test_tour_id_fk foreign key (test_tour_id) references test_tour on delete cascade,
    constraint quiz_id_fk foreign key (quiz_id) references quizzes on delete cascade
);