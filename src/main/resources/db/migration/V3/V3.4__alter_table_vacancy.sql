DELETE
FROM vacancies
WHERE id = 4;
ALTER TABLE vacancies
    DROP CONSTRAINT vacancies_employment_type_check;

ALTER TABLE vacancies
    ADD CONSTRAINT vacancies_employment_type_check
        CHECK (employment_type IN
               ('FULL_TIME', 'PART_TIME', 'INTERNSHIP', 'REMOTE_WORKING', 'FLEXIBLE_SCHEDULE',
                'SHIFT_SCHEDULE'));
