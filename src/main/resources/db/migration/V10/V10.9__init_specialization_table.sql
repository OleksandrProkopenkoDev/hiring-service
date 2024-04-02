CREATE TABLE IF NOT EXISTS specializations
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    icon_id VARCHAR(255),
    UNIQUE (name)
);

