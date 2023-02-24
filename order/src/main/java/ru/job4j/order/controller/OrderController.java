package ru.job4j.order.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.job4j.order.model.Order;
import ru.job4j.order.service.OrderService;

@AllArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {
    private OrderService orderService;

    @PostMapping("/new-order")
    public ResponseEntity<String> createOrder(Order order) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("order_status")
    public ResponseEntity<String> checkStatus(int orderId) {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
