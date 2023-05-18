package ru.client.controller;

import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.client.service.DishService;
import ru.client.service.OrderService;
import ru.domain.model.Order;
import ru.domain.model.Status;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableKafka
@AllArgsConstructor
@RequestMapping("/client/orders")
public class OrderController {
    private OrderService orderService;
    private DishService dishService;

    @GetMapping("/all")
    public String showOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "order/orders";
    }

    @GetMapping("/new-order")
    public String createOrder(Model model) {
        model.addAttribute("order",
                new Order(0, null, null, "Заполните поле", null));
        model.addAttribute("allDishes", dishService.findAll());
        return "order/createOrder";
    }

    @PostMapping("/new-order")
    public String addTaskPost(@ModelAttribute Order order, HttpServletRequest req) {
        order.setStatus(Status.ACCEPTED);
        return orderService.save(order, req)
                ? "redirect:/client/orders/order/" + order.getId()
                : "redirect:/client/orders/incorrect/";
    }

    @GetMapping("/order/{id}")
    public String showOrder(Model model, @PathVariable("id") int id) {
        model.addAttribute("order", orderService.findById(id));
        return "order/order";
    }
}