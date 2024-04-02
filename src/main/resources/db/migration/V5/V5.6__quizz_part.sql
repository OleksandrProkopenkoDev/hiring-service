alter table if exists question_answers
    add column quiz_part_id bigint;

create table quizz_parts
(
    id         bigserial not null,
    quiz_id    bigint,
    is_deleted boolean,
    primary key (id)
);

alter table if exists question_answers
    add constraint FK_QUIZ_PART foreign key (quiz_part_id) references quizz_parts;
alter table if exists quizz_parts
    add constraint FK_QUIZ foreign key (quiz_id) references quizzes;

alter table quizzes
    add column start_at timestamptz;

alter table if exists questions
    add column image_id varchar(255);

alter table if exists answers
    add column question_type varchar(255) check (question_type in
                                                 ('SINGLE_CHOICE', 'MULTIPLE_CHOICE', 'MATCHING',
                                                  'AUTOCHECKABLE_TEXT', 'HUMANCHECKABLE_TEXT',
                                                  'MEDIA_TO_MENTOR'))