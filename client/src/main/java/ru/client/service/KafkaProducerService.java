package ru.client.service;

import ru.domain.model.Order;

public interface KafkaProducerService {
    void sendToOrder(Order order);

    void sendToNotification(Order order);
}