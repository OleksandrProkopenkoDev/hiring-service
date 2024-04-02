alter table vacancies
    add column test_tour_id bigint;
alter table vacancies
    add constraint test_tour_id_fk foreign key (test_tour_id) references test_tour;
