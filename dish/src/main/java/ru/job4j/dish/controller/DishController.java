package ru.job4j.dish.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dish.service.DishService;

@EnableKafka
@Controller
@RequestMapping("/dish")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("dishes", dishService.findAll());
        return "dish/dishes";
    }

    @GetMapping("/{id}")
    public String showDish(Model model, @PathVariable("id") int id) {
        model.addAttribute("dish", dishService.findById(id));
        return "dish/dish";
    }

    @KafkaListener(topics = "from_kitchen_to_dish")
    public void msgFromKitchen(ConsumerRecord<Integer, String> record) {
        dishService.msgFromKitchen(record);
    }

    @KafkaListener(topics = "from_admin_to_dish")
    public void msgFromAdmin(ConsumerRecord<Integer, String> record) {
        dishService.msgFromAdmin(record);
    }

    @PostMapping("/kitchen")
    public void sendToKitchen(Integer orderId, String dishes) {
        dishService.sendToKitchen(orderId, dishes);
    }
}