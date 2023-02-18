package ru.job4j.notification.service;

import lombok.Data;
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
}