package ru.job4j.order.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.order.model.Status;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class StatusService {
    private final Map<Integer, Status> statuses = new HashMap<>();

    public StatusService() {
        statuses.put(1, new Status(1, "принят"));
        statuses.put(2, new Status(2, "готовится"));
        statuses.put(3, new Status(3, "доставка"));
        statuses.put(4, new Status(4, "получен"));
    }

    public List<Status> findAll() {
        return new ArrayList<>(statuses.values());
    }

    public Status findById(int id) {
        return  statuses.get(id);
    }
}