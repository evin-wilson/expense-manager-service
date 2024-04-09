CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS expense (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    transaction_type VARCHAR(10) NOT NULL CHECK (transaction_type IN ('expense', 'income', 'transfer')),
    amount FLOAT NOT NULL ,
    account VARCHAR(20) NOT NULL,
    category VARCHAR(20) NOT NULL,
    subCategory VARCHAR(20) NULL,
    description VARCHAR(100) NULL
);

CREATE TABLE IF NOT EXISTS account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    account VARCHAR(20) NOT NULL,
    category VARCHAR(20) NOT NULL,
    subCategory VARCHAR(20) NULL
);