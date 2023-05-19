package ru.payment.service;

public interface KafkaProducerService {
    void sendToOrder(int id, String str);
}