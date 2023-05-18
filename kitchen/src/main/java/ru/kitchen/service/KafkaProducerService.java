package ru.kitchen.service;

public interface KafkaProducerService {
    void sendToDelivery(int id, String str);

    void sendToNotification(int id, String str);
}
