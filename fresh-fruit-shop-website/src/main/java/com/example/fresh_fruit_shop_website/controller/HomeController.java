package com.example.fresh_fruit_shop_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.fresh_fruit_shop_website.model.Fruit;
import com.example.fresh_fruit_shop_website.service.FruitService;

import java.util.List;

@Controller
public class HomeController {

    private final FruitService fruitService;

    public HomeController(FruitService fruitService) {
        this.fruitService = fruitService;
    }

    // Route kwa "/" na "/index"
    @GetMapping({"/", "/index"})
    public String index(Model model, @RequestParam(required = false) String category) {
        List<Fruit> fruits;
        if (category != null && !category.isEmpty()) {
            fruits = fruitService.getFruitsByCategory(category);
        } else {
            fruits = fruitService.getAllFruits();
        }
        List<String> categories = fruitService.getAllCategories();

        model.addAttribute("fruits", fruits);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category == null ? "" : category);

        return "index";  // inataja index.html
    }

    // Route kwa "/home"
    @GetMapping("/home")
    public String home(Model model, @RequestParam(required = false) String category) {
        List<Fruit> fruits;
        if (category != null && !category.isEmpty()) {
            fruits = fruitService.getFruitsByCategory(category);
        } else {
            fruits = fruitService.getAllFruits();
        }
        List<String> categories = fruitService.getAllCategories();

        model.addAttribute("fruits", fruits);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category == null ? "" : category);

        return "home";  // inataja home.html
    }
}
