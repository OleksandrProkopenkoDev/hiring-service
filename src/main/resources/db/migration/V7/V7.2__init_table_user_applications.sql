create table if not exists user_applications
(
  id            bigserial primary key,
  user_keycloak_id   uuid,
  vacancy_id    uuid,
  cover_letter   varchar(255),
  user_application_cv_id  varchar(50),
  phone_number  varchar(50),
  user_application_process_id bigint
);