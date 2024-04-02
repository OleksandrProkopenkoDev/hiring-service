DELETE
FROM task_questions;

-- Quiz1 Question 1
INSERT INTO task_questions (id, index_in_task, weight, question_id, task_id)
VALUES ('550e8400-e29b-41d4-a716-446655440001', 1, 40, 'baa8bb7d-9533-4483-bdb4-204efba55e7a',
        '550e8400-1111-41d4-a716-446655440001');

-- Quiz1 Question 2
INSERT INTO task_questions (id, index_in_task, weight, question_id, task_id)
VALUES ('550e8400-e29b-41d4-a716-446655440002', 2, 40, '2bc5530e-0eb3-4ed7-8f74-cbc4cbf7b5bb',
        '550e8400-1111-41d4-a716-446655440001');

-- Quiz1 Question 3
INSERT INTO task_questions (id, index_in_task, weight, question_id, task_id)
VALUES ('550e8400-e29b-41d4-a716-446655440003', 3, 40, '570f93b5-485c-42ea-82f9-8c866ac0e4c4',
        '550e8400-1111-41d4-a716-446655440001');

-- Quiz2 Question 1
INSERT INTO task_questions (id, index_in_task, weight, question_id, task_id)
VALUES ('550e8400-e29b-41d4-a716-446655440011', 1, 40, '570f93b5-485c-42ea-82f9-8c866ac0e4c4',
        '550e8400-1111-41d4-a716-446655440002');

-- Quiz2 Question 2
INSERT INTO task_questions (id, index_in_task, weight, question_id, task_id)
VALUES ('550e8400-e29b-41d4-a716-446655440012', 2, 40, '024890e3-c883-4049-947b-8af2f9a6bfdf',
        '550e8400-1111-41d4-a716-446655440002');

-- Quiz2 Question 3
INSERT INTO task_questions (id, index_in_task, weight, question_id, task_id)
VALUES ('550e8400-e29b-41d4-a716-446655440013', 3, 40, 'e4f282d8-4308-44e8-a47c-687e556ead24',
        '550e8400-1111-41d4-a716-446655440002');

-- Quiz3 Question 1
INSERT INTO task_questions (id, index_in_task, weight, question_id, task_id)
VALUES ('550e8400-e29b-41d4-a716-446655440021', 1, 40, '570f93b5-485c-42ea-82f9-8c866ac0e4c4',
        '550e8400-1111-41d4-a716-446655440003');

-- Quiz3 Question 2
INSERT INTO task_questions (id, index_in_task, weight, question_id, task_id)
VALUES ('550e8400-e29b-41d4-a716-446655440022', 2, 40, '024890e3-c883-4049-947b-8af2f9a6bfdf',
        '550e8400-1111-41d4-a716-446655440003');

-- Quiz3 Question 3
INSERT INTO task_questions (id, index_in_task, weight, question_id, task_id)
VALUES ('550e8400-e29b-41d4-a716-446655440023', 3, 40, 'e4f282d8-4308-44e8-a47c-687e556ead24',
        '550e8400-1111-41d4-a716-446655440003');
