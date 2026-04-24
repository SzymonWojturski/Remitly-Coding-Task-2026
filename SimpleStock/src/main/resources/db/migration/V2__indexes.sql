CREATE INDEX idx_wallet_stocks_wallet_id ON wallet_stocks(wallet_id);
CREATE INDEX idx_wallet_stocks_stock_name ON wallet_stocks(stock_name);

CREATE INDEX idx_audit_log_wallet_id ON audit_log(wallet_id);
CREATE INDEX idx_audit_log_created_at ON audit_log(created_at);