package ru.job4j.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dish.service.DishAPIService;
import ru.job4j.dish.model.Dish;

import java.util.List;

@RestController
@RequestMapping("/api/item")
public class DishController {
    private final DishAPIService dishService;

    public DishController(DishAPIService dishService) {
        this.dishService = dishService;
    }

    @PostMapping
    public Dish save(@RequestBody Dish dish) {
        return dishService.add(dish);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestParam int id, @RequestBody Dish dish) {
        boolean status = dishService.replace(id, dish);
        return ResponseEntity
                .status(status ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        boolean status = dishService.delete(id);
        return ResponseEntity
                .status(status ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .build();
    }

    @GetMapping("/getById")
    public Dish getById(@RequestParam int id) {
        return dishService.findById(id);
    }

    @GetMapping("/getByName")
    public List<Dish> getByName(@RequestParam String name) {
        return dishService.findByName(name);
    }

    @GetMapping("/getAll")
    public List<Dish> getAll() {
        return dishService.findAll();
    }
}