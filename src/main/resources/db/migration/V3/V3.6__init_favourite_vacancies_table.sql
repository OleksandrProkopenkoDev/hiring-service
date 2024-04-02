create table if not exists favorite_vacancies
(
    id         bigserial primary key,
    user_id    bigint not null,
    vacancy_id bigint not null,
    foreign key (user_id) references users (id),
    foreign key (vacancy_id) references vacancies (id)
);
