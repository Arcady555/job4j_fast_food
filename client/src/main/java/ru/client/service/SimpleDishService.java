package ru.client.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.domain.model.Dish;
import ru.client.repository.DishRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleDishService implements DishService {
    private DishRepository dishRepository;

    @Override
    public Dish findById(int id) {
        Optional<Dish> optionalProduct = dishRepository.findById(id);
        Dish product = new Dish();
        if (optionalProduct.isPresent()) {
            product = optionalProduct.get();
        } else {
            product.setName("Продукт не найден!");
        }
        return product;
    }

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }
}