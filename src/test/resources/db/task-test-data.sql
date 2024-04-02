DELETE
FROM tasks;
-- Quiz 1
INSERT INTO tasks (id, title, description, passing_score, max_score, created_at, updated_at,
                   total_question,
                   total_duration, total_weight, published, type)
VALUES ('550e8400-1111-41d4-a716-446655440001', 'Quiz1 Title', 'Test your skills', 50, 100,
        '2024-01-12 00:17:19.699000 +00:00', '2024-01-12 00:17:19.699892 +00:00', 3, 150, 120,
        false, 'QUIZ');

-- Quiz 2
INSERT INTO tasks (id, title, description, passing_score, max_score, created_at, updated_at,
                   total_question,
                   total_duration, total_weight, published, type)
VALUES ('550e8400-1111-41d4-a716-446655440002', 'Quiz2 Title', 'Test your geographical skills',
        60, 100, '2024-01-12 00:24:06.309000 +00:00', '2024-01-12 00:24:06.309765 +00:00', 3, 130,
        120,
        false, 'QUIZ');

-- Quiz 3
INSERT INTO tasks (id, title, description, passing_score, max_score, created_at, updated_at,
                   published_at,
                   total_question,
                   total_duration, total_weight, published, type)
VALUES ('550e8400-1111-41d4-a716-446655440003', 'Quiz3 Title', 'Test your programming skills',
        73, 100, '2024-01-12 00:24:06.309000 +00:00', '2024-01-12 00:24:06.309765 +00:00',
        '2024-01-13 00:24:06.309765 +00:00', 3, 130, 120,
        true, 'QUIZ');


