package ru.job4j.notification.service;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import ru.job4j.notification.model.Notification;
import ru.job4j.notification.repository.NotificationRepository;

@Data
@Service
public class NotificationService {
    private final NotificationRepository repository;

    public void save(Notification note) {
        repository.save(note);
    }

    public void msgListener(ConsumerRecord<Integer, String> record) {
        Notification note = new Notification();
        note.setId(record.key());
        String massage = "Заказ № " + record.key() + " " + record.value();
        note.setMassage(massage);
        save(note);
    }
}