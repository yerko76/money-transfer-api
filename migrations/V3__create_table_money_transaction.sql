create TABLE money_transaction (
    id SERIAL PRIMARY KEY,
    from_account_id uuid,
    to_account_id uuid,
    amount numeric(15,6),
    currency varchar(3),
    created_at TIMESTAMP DEFAULT NOW(),
    FOREIGN KEY (from_account_id) REFERENCES account(account_id),
    FOREIGN KEY (to_account_id) REFERENCES account(account_id)
);
