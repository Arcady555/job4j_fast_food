package ru.dish.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dish.repository.DishRepository;
import ru.domain.model.Dish;
import ru.domain.model.Order;
import ru.domain.model.Status;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleDishService implements DishService {
    private final DishRepository dishRepository;
    private final OrderService orderService;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void msgFromOrder(int id) {
        Order order = orderService.findById(id);
        List<Dish> dishList = order.getDishes();
        workWithDish(dishList);
        order.setDishes(dishList);
        order.setStatus(Status.WAIT_PAY);
        orderService.save(order);
        kafkaProducerService.sendToOrder(id, order.getStatus().toString());
    }

    private void workWithDish(List<Dish> dishList) {
        for (Dish dish : dishList) {
            if (dish.getAmount() == 0) {
                dish = new Dish();
                dish.setName("Блюдо закончилось!");
                dish.setPrice(0);
            } else {
                int amount = dish.getAmount();
                amount--;
                dish.setAmount(amount);
                dishRepository.save(dish);
            }
        }
    }
}