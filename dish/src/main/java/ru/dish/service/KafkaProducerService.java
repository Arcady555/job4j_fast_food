package ru.dish.service;

public interface KafkaProducerService {
    void sendToOrder(int id, String str);
}