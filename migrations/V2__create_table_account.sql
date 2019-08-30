create TABLE account (
    account_id uuid PRIMARY KEY,
    balance numeric(15,6),
    currency varchar(3),
    customer_id uuid,
    created_at TIMESTAMP DEFAULT NOW(),
    active boolean,
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id)
);

insert into account (account_id,customer_id,balance, currency, active) values ('156f6516-33e3-41b6-9335-abcff54d9000','156f6516-33e3-41b6-9335-bbbff54d9097',1000,'USD',true);
insert into account (account_id,customer_id,balance, currency, active) values ('156f6516-33e3-41b6-9335-abcff54d9001','156f6516-33e3-41b6-9335-bbbff54d9098',0,'USD',false);
