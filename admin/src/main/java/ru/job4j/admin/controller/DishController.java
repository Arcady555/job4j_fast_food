package ru.job4j.admin.controller;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.admin.model.Dish;
import ru.job4j.admin.service.DishService;

@EnableKafka
@Controller
@RequestMapping("/dishes")
public class DishController {
    private final DishService dishService;

    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("dishes", dishService.findAll());
        return "dish/dishes";
    }

    @GetMapping("/{id}")
    public String showDish(Model model, @PathVariable("id") int id) {
        model.addAttribute("dish", dishService.findById(id));
        return "dish/dish";
    }

    @KafkaListener(topics = "from_dish_to_admin")
    public void msgFromDish(ConsumerRecord<Integer, Integer> record) {
        dishService.msgFromDish(record);
    }

    @GetMapping("/update/{id}")
    public String updateDishGet(Model model, @PathVariable("id") int id) {
        model.addAttribute("dish", dishService.findById(id));
        return "dish/updateDish";
    }

    @PostMapping("/update")
    public String updateDishPost(@ModelAttribute Dish dish) {
        dishService.save(dish);
        return "redirect:/dishes/all";
    }

    @GetMapping("/create")
    public String addDishGet(Model model) {
        model.addAttribute("dish", new Dish());
        return "dish/newDish";
    }

    @PostMapping("/create")
    public String addDishPost(@ModelAttribute Dish dish) {
        dishService.save(dish);
        return "redirect:/dishes/all";
    }

    @GetMapping("/delete/{id}")
    public String deleteDishGet(@PathVariable("id") int id) {
        Dish dish = dishService.findById(id);
        dishService.delete(dish);
        return "redirect:/dishes/all";
    }
}