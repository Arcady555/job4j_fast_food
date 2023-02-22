package ru.job4j.kitchen.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.kitchen.model.Product;

import java.util.List;
import java.util.Optional;

public interface FoodStock extends CrudRepository<Product, Integer> {
    @Override
    List<Product> findAll();

    Optional<Product> findByName(String name);
}