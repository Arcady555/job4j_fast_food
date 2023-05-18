create table if not exists orders_dishes (
    PRIMARY KEY (order_id, dish_id),
    order_id int not null REFERENCES orders(id),
    dish_id int not null REFERENCES dishes(id)
);