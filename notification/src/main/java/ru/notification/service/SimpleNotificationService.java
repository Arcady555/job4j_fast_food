package ru.notification.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.domain.model.Notification;
import ru.notification.repository.NotificationRepository;

@AllArgsConstructor
@Service
public class SimpleNotificationService implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public void save(int id, String str) {
        Notification note = new Notification();
        note.setId(id);
        String massage = "Заказ № " + id + " " + str;
        note.setMassage(massage);
        notificationRepository.save(note);
    }
}