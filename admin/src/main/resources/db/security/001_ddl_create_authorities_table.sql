CREATE TABLE if not exists authorities (
  id serial primary key,
  authority VARCHAR(50) NOT NULL unique
);