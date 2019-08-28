create TABLE customers (
    customer_id serial primary key,
    first_name varchar(100),
    last_name varchar(100),
    email varchar(100) unique,
    active boolean
)
