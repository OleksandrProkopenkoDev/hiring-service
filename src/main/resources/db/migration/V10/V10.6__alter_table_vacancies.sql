ALTER TABLE vacancies DROP COLUMN IF EXISTS department;
ALTER TABLE vacancies ADD COLUMN department_id bigserial;