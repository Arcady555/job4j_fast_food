package ru.notification.repository;

import org.springframework.data.repository.CrudRepository;
import ru.domain.model.Notification;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {
}