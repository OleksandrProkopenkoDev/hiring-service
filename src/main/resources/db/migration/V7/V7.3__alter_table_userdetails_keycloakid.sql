alter table user_applications
  add constraint user_details_user_keycloak_id_fkey
  FOREIGN KEY (user_keycloak_id) REFERENCES user_details (user_keycloak_id),
  add constraint vacancies_id_fkey
  FOREIGN KEY (vacancy_id) REFERENCES vacancies (id);