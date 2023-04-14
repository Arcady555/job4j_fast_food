package ru.job4j.order.service;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.job4j.order.model.Dish;
import ru.job4j.order.model.Order;
import ru.job4j.order.model.Status;
import ru.job4j.order.repository.OrderRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Data
@Service
public class OrderService {
    private final OrderRepository orders;
    private final StatusService statuses;
    private final DishService dishService;
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplateS;
  //  @Autowired
  //  private KafkaTemplate<Integer, Integer> kafkaTemplateO;

    public OrderService(OrderRepository orders, StatusService statuses, DishService dishService) {
        this.orders = orders;
        this.statuses = statuses;
        this.dishService = dishService;
    }

    public boolean saveOut(Order order, HttpServletRequest req) {
        boolean rsl = false;
        if (fullOrder(order, req)) {
            orders.save(order);
            rsl = true;
            kafkaTemplateS.send("preorder", order.getId(), order.getDishes().toString());
            kafkaTemplateS.send("messengers", order.getId(), order.getStatus().getName());
        }
        return rsl;
    }

    public void saveIn(ConsumerRecord<Integer, String> record) {
        Order order = msgFromKitchen(record);
        orders.save(order);
        kafkaTemplateS.send("messengers", order.getId(), order.getStatus().getName());
    }

    public Order findById(int id) {
        Optional<Order> optionalOrder = orders.findById(id);
        Order order = new Order();
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            Status status = new Status();
            status.setName("Заказ не найден!");
            order.setStatus(status);
        }
        return order;
    }

    public List<Order> findAll() {
        return orders.findAll();
    }

    private Order msgFromKitchen(ConsumerRecord<Integer, String> record) {
        int id = record.key();
        Order order = findById(id);
        Status status = statuses.findByName(record.value());
        order.setStatus(status);
        return order;
    }

    private boolean fullOrder(Order order, HttpServletRequest req) {
        boolean rsl = false;
        List<Dish> dishes = new ArrayList<>();
        String[] dishIds = req.getParameterValues("dishIds");
        if (dishIds != null) {
            for (String str : dishIds) {
                int dishId = Integer.parseInt(str);
                Dish dish = dishService.findById(dishId);
                if (dish.getName().equals("Продукт не найден!")) {
                    System.out.println("Продукт не найден!");
                    return false;
                }
                dishes.add(dish);
                rsl = true;
            }
        }
        order.setDishes(dishes);
        return rsl;
    }
}