create table if not exists dishes (
    id serial primary key not null,
    image bytea,
    name varchar unique not null,
    price int,
    amount int
);