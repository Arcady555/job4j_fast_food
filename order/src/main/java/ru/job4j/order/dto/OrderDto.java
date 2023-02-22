package ru.job4j.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.job4j.order.model.Product;
import ru.job4j.order.model.Status;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private int id;
    private List<Product> products = new ArrayList<>();
    private Status status;
}
