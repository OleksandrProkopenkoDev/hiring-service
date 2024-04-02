CREATE TABLE exam_tour_passing
(
    id               uuid PRIMARY KEY,
    keycloak_user_id UUID NOT NULL,
    status           varchar,
    exam_tour_id     BIGINT NOT NULL,
    FOREIGN KEY (exam_tour_id) REFERENCES exam_tour(id)
);