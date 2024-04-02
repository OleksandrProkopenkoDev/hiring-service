ALTER TABLE vacancies DROP COLUMN IF EXISTS description;
ALTER TABLE vacancies ADD COLUMN description_id varchar(50);