CREATE TABLE wallets (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE stocks (
    name VARCHAR(255) PRIMARY KEY,
    quantity BIGINT NOT NULL CHECK (quantity >= 0)
);

CREATE TABLE wallet_stocks (
    wallet_id VARCHAR(255) NOT NULL,
    stock_name VARCHAR(255) NOT NULL,
    quantity BIGINT NOT NULL CHECK (quantity >= 0),
    PRIMARY KEY (wallet_id, stock_name),
    FOREIGN KEY (wallet_id) REFERENCES wallets(id) ON DELETE CASCADE,
    FOREIGN KEY (stock_name) REFERENCES stocks(name) ON DELETE CASCADE
);

CREATE TABLE audit_log (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(10) NOT NULL CHECK (type IN ('buy', 'sell')),
    wallet_id VARCHAR(255) NOT NULL,
    stock_name VARCHAR(255) NOT NULL
);