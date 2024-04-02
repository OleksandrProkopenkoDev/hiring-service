CREATE TABLE locations
(
    id UUID PRIMARY KEY,
    country VARCHAR(255),
    city VARCHAR(255),
    UNIQUE (country, city)
);

CREATE TABLE languages
(
    id UUID PRIMARY KEY,
    name VARCHAR(255),
    level VARCHAR(255),
    UNIQUE (name, level)
);

CREATE TABLE vacancies_locations
(
    vacancy_id  UUID NOT NULL,
    location_id UUID NOT NULL,
    PRIMARY KEY (vacancy_id, location_id),
    FOREIGN KEY (vacancy_id) REFERENCES vacancies (id),
    FOREIGN KEY (location_id) REFERENCES locations (id)
);

CREATE TABLE vacancies_languages
(
    vacancy_id  UUID NOT NULL,
    language_id UUID NOT NULL,
    PRIMARY KEY (vacancy_id, language_id),
    FOREIGN KEY (vacancy_id)  REFERENCES vacancies (id),
    FOREIGN KEY (language_id) REFERENCES languages (id)
);