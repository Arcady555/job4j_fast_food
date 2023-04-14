package ru.job4j.kitchen.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.kitchen.model.Status;
import ru.job4j.kitchen.repository.StatusRepository;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StatusService {
    private final StatusRepository statuses;

    public void save(Status status) {
        statuses.save(status);
    }

    public Status findById(int id) {
        Optional<Status> optionalStatus = statuses.findById(id);
        Status status = new Status();
        if (optionalStatus.isPresent()) {
            status = optionalStatus.get();
        } else {
            status.setName("Статус не определён!");
        }
        return status;
    }

    public Status findByName(String name) {
        Optional<Status> optionalStatus = statuses.findByName(name);
        Status status = new Status();
        if (optionalStatus.isPresent()) {
            status = optionalStatus.get();
        } else {
            status.setName("Статус не определён!");
        }
        return status;
    }
}
