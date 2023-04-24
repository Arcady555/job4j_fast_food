create table if not exists dish (
    id serial primary key not null,
    name varchar,
    amount int
);