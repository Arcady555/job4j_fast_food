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
            kafkaTemplateS.send("from_order_to_kitchen", order.getId(), order.getDishes().toString());
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
        List<Dish> dishes = getDishFromKitchen(record.value());
        Status status = getStatusFromKitchen(record.value());
        System.out.println(status.toString());
        order.setDishes(dishes);
        order.setStatus(status);
        orders.save(order);
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
                if (dish.getName().equals("Блюдо не найдено!")) {
                    System.out.println("Блюдо не найдено!");
                    return false;
                }
                dishes.add(dish);
                rsl = true;
            }
        }
        order.setDishes(dishes);
        return rsl;
    }

    private List<Dish> getDishFromKitchen(String str) {
        List<Dish> rsl = new ArrayList<>();
        String[] array = str.split(",", 2);
        String[] dishes = array[1].split("=");
        String[] dishList = dishes[1].substring(1, dishes[1].length() - 2).split(",");
        for (String strDish : dishList) {
            Dish dish = dishService.findByName(strDish.trim());
            rsl.add(dish);
        }
        return rsl;
    }

    private Status getStatusFromKitchen(String str) {
        String[] array = str.split(",", 2);
        String[] strStatus = array[0].split("=");
        System.out.println(strStatus[1]);
        return statuses.findByName(strStatus[1]);
    }
}