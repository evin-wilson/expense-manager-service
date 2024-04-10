INSERT INTO users (username, password, email) VALUES
    ('evin_wilson', '1234', 'evin.wilson@example.com'),
    ('ram_ramu', '1234', 'ram.ramu@example.com');

INSERT INTO transactions (username, transaction_type, account, category, sub_category, amount, date, description) VALUES
    ('evin_wilson', 'INCOME', 'Salary', 'salary', null, 10000.00, '2023-03-01 12:00:00', null),
    ('evin_wilson', 'INCOME', 'cash', 'food', null, 100.00, '2023-03-02 08:00:00', null),
    ('evin_wilson', 'EXPENSE', 'Salary', 'rent', null, 5000.00, '2023-03-05 01:30:00', null),
    ('evin_wilson', 'EXPENSE', 'Salary', 'food', null, 1000.00, '2023-03-10 10:00:00', null),
    ('evin_wilson', 'EXPENSE', 'cash', 'movie', null, 150.00, '2023-03-10 09:45:00', null),
    ('evin_wilson', 'EXPENSE', 'Salary', 'rent', null, 5000.00, '2023-04-05 01:30:00', null),
    ('ram_ramu', 'EXPENSE', 'Salary', 'rent', null, 5000.00, '2023-04-05 01:30:00', null);
