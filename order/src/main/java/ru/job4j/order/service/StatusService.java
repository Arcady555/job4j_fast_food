package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.order.model.Status;
import ru.job4j.order.repository.StatusRepository;

import java.util.Optional;

@Data
@Service
@AllArgsConstructor
public class StatusService {
    private StatusRepository statuses;

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
}