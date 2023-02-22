package ru.job4j.kitchen.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.kitchen.model.Product;
import ru.job4j.kitchen.repository.FoodStock;

import java.util.List;
import java.util.Optional;

@Data
@Service
public class ProductService {
    private FoodStock foodStock;

    public List<Product> findAll() {
        return foodStock.findAll();
    }

    public Product findByName(String name) {
        Optional<Product> optionalProduct = foodStock.findByName(name);
        Product product = new Product();
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            product.setName("Продукт не найден!");
        }
        return product;
    }
}
