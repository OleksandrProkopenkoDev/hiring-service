ALTER TABLE vacancy_locations
  DROP CONSTRAINT vacancy_locations_pkey;

ALTER TABLE vacancy_locations
  ALTER COLUMN city DROP NOT NULL;
