package ru.job4j.dish.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.dish.service.DishService;
import ru.job4j.dish.model.Dish;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/dish")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Dish dish) {
        boolean status = dishService.save(dish);
        return ResponseEntity
                .status(status ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestParam int id, @RequestBody Dish dish) {
        dish.setId(id);
        boolean status = dishService.save(dish);
        return ResponseEntity
                .status(status ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam int id) {
        Optional<Dish> searchDish = getById(id);
        if (searchDish.isPresent()) {
            boolean status = dishService.delete(searchDish.get());
            return ResponseEntity
                    .status(status ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                    .build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/getById")
    public Optional<Dish> getById(@RequestParam int id) {
        return dishService.findById(id);
    }

    @GetMapping("/getAll")
    public List<Dish> getAll() {
        return dishService.findAll();
    }
}