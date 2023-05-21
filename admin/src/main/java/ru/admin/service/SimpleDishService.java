package ru.admin.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.admin.repository.DishRepository;
import ru.domain.model.Dish;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleDishService implements DishService {
    private final DishRepository dishRepository;

    @Override
    public List<Dish> findAll() {
        return dishRepository.findAll();
    }

    @Override
    public List<Dish> findAllNotEnough() {
        return dishRepository.findAllNotEnough();
    }

    @Override
    public Dish findById(int id) {
        Optional<Dish> optionalDish = dishRepository.findById(id);
        Dish dish = new Dish();
        if (optionalDish.isPresent()) {
            dish = optionalDish.get();
        } else {
            dish.setName("Блюдо не найдено!");
        }
        return dish;
    }

    @Override
    public void save(Dish dish) {
        dishRepository.save(dish);
    }

    @Override
    public void delete(Dish dish) {
        dishRepository.delete(dish);
    }
}