package ru.order.service;

public interface OrderService {
    void msgFromClient(int id, String str);

    void msgFromDish(int id, String str);

    void msgFromPayment(int id, String str);
}
