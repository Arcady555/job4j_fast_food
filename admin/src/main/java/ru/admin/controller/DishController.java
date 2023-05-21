package ru.admin.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.domain.model.Dish;
import ru.admin.service.SimpleDishService;

@AllArgsConstructor
@Controller
@RequestMapping("/admin/dishes")
public class DishController {
    private final SimpleDishService dishService;

    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("dishes", dishService.findAll());
        return "dish/dishes";
    }

    @GetMapping("/allNotEnough")
    public String showNotEnough(Model model) {
        model.addAttribute("dishes", dishService.findAllNotEnough());
        return "dish/dishesNotEnough";
    }

    @GetMapping("/{id}")
    public String showDish(Model model, @PathVariable("id") int id) {
        model.addAttribute("dish", dishService.findById(id));
        return "dish/dish";
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