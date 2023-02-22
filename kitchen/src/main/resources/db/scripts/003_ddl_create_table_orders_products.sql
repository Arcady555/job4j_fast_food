create table if not exists orders_products (
    PRIMARY KEY (order_id, product_id),
    order_id int not null REFERENCES orders(id),
    product_id int not null REFERENCES product(id)
);