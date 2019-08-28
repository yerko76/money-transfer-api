create TABLE customers (
    customer_id uuid PRIMARY KEY,
    first_name varchar(100),
    last_name varchar(100),
    email varchar(100) unique,
    active boolean
);

insert into customers (customer_id,first_name, last_name, email, active) values ('156f6516-33e3-41b6-9335-bbbff54d9094','john', 'doe', 'johndoe@test.com', true);
insert into customers (customer_id,first_name, last_name, email, active) values ('156f6516-33e3-41b6-9335-bbbff54d9095','lorem', 'ipsum', 'loremipsum@test.com', true);
insert into customers (customer_id,first_name, last_name, email, active) values ('156f6516-33e3-41b6-9335-bbbff54d9096','some', 'customer', 'somecustomer@test.com', true);
insert into customers (customer_id,first_name, last_name, email, active) values('156f6516-33e3-41b6-9335-bbbff54d9097','testing', 'user', 'test@test.com', false);
insert into customers (customer_id,first_name, last_name, email, active) values('156f6516-33e3-41b6-9335-bbbff54d9098','good', 'user', 'gooduser@test.com', true);
