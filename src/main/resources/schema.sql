CREATE TABLE IF NOT EXISTS account
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    balance NUMERIC(15, 2) NOT NULL DEFAULT 10000,
    CONSTRAINT account_balance_non_negative CHECK (balance >= 0)
);

CREATE TABLE IF NOT EXISTS notification
(
    id SERIAL PRIMARY KEY,
    message VARCHAR(255) NOT NULL
);