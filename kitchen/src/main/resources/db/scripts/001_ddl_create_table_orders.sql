create table if not exists orders (
    id serial primary key not null,
    status_id int
);