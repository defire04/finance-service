-- V1__Create_user_table.sql
CREATE TABLE service_user
(
    id                BIGSERIAL PRIMARY KEY,
    balance_id        BIGINT,
    username          VARCHAR(255) UNIQUE NOT NULL,
    created_timestamp BIGINT,
    first_name        VARCHAR(255),
    last_name         VARCHAR(255),
    email             VARCHAR(255),
    currency          VARCHAR(50),

    CONSTRAINT fk_balance
        FOREIGN KEY (balance_id)
            REFERENCES balance (id)
);
