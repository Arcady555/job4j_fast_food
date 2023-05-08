package ru.order.controller;

import lombok.AllArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.domain.model.Order;
import ru.domain.model.Status;
import ru.order.service.DishService;
import ru.order.service.OrderService;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableKafka
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    private DishService dishService;
/*
    @PostMapping("/kitchen")
    public void sendToKitchen(@RequestBody Order order, HttpServletRequest req) {
        orderService.saveOut(order, req);
    } */

    @KafkaListener(topics = "from_kitchen_to_order")
    public void msgFromKitchen(ConsumerRecord<Integer, String> record) {
        orderService.saveIn(record);
    }

    @GetMapping("/all")
    public String showOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "order/orders";
    }

    @GetMapping("/new-order")
    public String createOrder(Model model) {
        model.addAttribute("order", new Order(0, null, null, "Заполните поле",
                null));
        model.addAttribute("allDishes", dishService.findAll());
        return "order/createOrder";
    }

    @PostMapping("/new-order")
    public String addTaskPost(@ModelAttribute Order order, HttpServletRequest req) {
        order.setStatus(Status.ACCEPTED);
        orderService.saveOut(order, req);
        return "redirect:/orders/order/" + order.getId();
    }

    @GetMapping("/order/{id}")
    public String showOrder(Model model, @PathVariable("id") int id) {
        model.addAttribute("order", orderService.findById(id));
        return "order/order";
    }
}