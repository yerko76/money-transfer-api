create TABLE customer (
    customer_id uuid PRIMARY KEY,
    first_name varchar(100),
    last_name varchar(100),
    email varchar(100) unique,
    active boolean
);

create TABLE account (
    account_id uuid PRIMARY KEY,
    balance numeric(15,6),
    currency varchar(3),
    customer_id uuid,
    created_at TIMESTAMP DEFAULT NOW(),
    active boolean,
    FOREIGN KEY (customer_id) REFERENCES customer(customer_id)
);

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
