CREATE SCHEMA IF NOT EXISTS pay_app;

CREATE TABLE IF NOT EXISTS pay_app.users (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT users_unique_email UNIQUE(email)
);

CREATE TABLE IF NOT EXISTS pay_app.accounts (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES pay_app.users,
    balance NUMERIC(10,2) NOT NULL DEFAULT 0,
    CONSTRAINT accounts_balance_not_negative CHECK (balance >= 0)
);

CREATE TABLE IF NOT EXISTS pay_app.account_operations (
    id BIGSERIAL PRIMARY KEY,
    account_id INTEGER REFERENCES pay_app.accounts ON DELETE CASCADE,
    operation VARCHAR(20) NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    original_balance NUMERIC(10,2) NOT NULL,
    new_balance NUMERIC(10,2) NOT NULL,
    executed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT accounts_history_amount_positive CHECK (amount > 0)
);