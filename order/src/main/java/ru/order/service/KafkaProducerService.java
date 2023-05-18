package ru.order.service;

public interface KafkaProducerService {
    void sendToDish(int id, String str);

    void sendToNotification(int id, String str);

    void sendToPayment(int id, String str);

    void sendToKitchen(int id, String str);
}