package ru.client.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.domain.model.Dish;
import ru.domain.model.Order;
import ru.domain.model.Status;
import ru.client.repository.OrderRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SimpleOrderService implements OrderService {
    private final OrderRepository orderRepository;
    private final SimpleDishService dishService;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public boolean save(Order order, HttpServletRequest req) {
        boolean rsl = false;
        if (fullOrder(order, req)) {
            orderRepository.save(order);
            rsl = true;
            kafkaProducerService.sendToOrder(order);
            kafkaProducerService.sendToNotification(order);
        }
        return rsl;
    }

    @Override
    public Order findById(int id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = new Order();
        if (optionalOrder.isPresent()) {
            order = optionalOrder.get();
        } else {
            order.setStatus(Status.NOT_FOUND);
        }
        return order;
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
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
}