package ru.job4j.kitchen.service;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;
import ru.job4j.kitchen.model.Order;
import ru.job4j.kitchen.model.Status;
import ru.job4j.kitchen.repository.OrderRepository;

import java.util.Optional;

@Data
@Service
public class OrderService {
    private final OrderRepository orders;
    private final ProductService foodStock;
    private final StatusService statuses;

    public void save(Order order) {
        orders.save(order);
    }

    public Order findById(int id) {
        Optional<Order> optionalOrder = orders.findById(id);
        Order order = new Order();
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            Status status = new Status();
            status.setName("Статус не определён!");
            order.setStatus(status);
        }
        return order;
    }

    public void msgFromOrder(ConsumerRecord<Integer, String> record) {
        Order order = new Order();
        order.setId(record.key());
        Status status = statuses.findByName(getStatusFromJson(record.value()));
        order.setStatus(status);
        save(order);
    }

    private String getStatusFromJson(String str) {
        String[] array = str.split(":");
        int i = findI(array);
        String[] statusArray = array[i + 3].split(",");
        return statusArray[0].substring(1, statusArray[0].length() - 1);
    }

    private int findI(String[] array) {
        int rsl = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i].contains("status")) {
                rsl = i;
            }
        }
        return rsl;
    }
}