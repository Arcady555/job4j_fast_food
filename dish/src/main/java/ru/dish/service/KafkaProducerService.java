package ru.dish.service;

public interface KafkaProducerService {
    void sendToOrder(int id, String str);

    void sendToAdmin(int id, int amount);
}