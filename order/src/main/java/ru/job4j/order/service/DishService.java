package ru.job4j.order.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import ru.job4j.order.model.Dish;
import ru.job4j.order.repository.DishRepository;

import java.util.List;
import java.util.Optional;

@Data
@Service
@AllArgsConstructor
public class DishService {
    private DishRepository products;

    public Dish findById(int id) {
        Optional<Dish> optionalProduct = products.findById(id);
        Dish product = new Dish();
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            product.setName("Продукт не найден!");
        }
        return product;
    }

    public List<Dish> findAll() {
        return products.findAll();
    }

    public Dish findByName(String name) {
        Optional<Dish> optionalDish = products.findByName(name);
        return optionalDish.get();
    }
}
