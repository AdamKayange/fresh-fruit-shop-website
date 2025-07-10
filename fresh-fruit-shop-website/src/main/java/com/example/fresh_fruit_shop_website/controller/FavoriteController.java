package com.example.fresh_fruit_shop_website.controller;

import com.example.fresh_fruit_shop_website.model.Fruit;
import com.example.fresh_fruit_shop_website.service.FavoriteService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // Show list of user's favorite fruits
    @GetMapping
    public String viewFavorites(Model model) {
        List<Fruit> favorites = favoriteService.getUserFavorites();
        model.addAttribute("favorites", favorites);
        return "favorites"; // Maps to favorites.html or favorites.jsp
    }

    // Toggle favorite status (add or remove) via POST
    @PostMapping("/toggle/{fruitId}")
    public String toggleFavorite(@PathVariable Integer fruitId) {
        favoriteService.toggleFavorite(fruitId);
        return "redirect:/favorites";
    }

    // Optional: remove favorite explicitly (also handled by toggle)
    @PostMapping("/remove/{fruitId}")
    public String removeFavorite(@PathVariable Integer fruitId) {
        favoriteService.toggleFavorite(fruitId);
        return "redirect:/favorites";
    }
}
