package com.example.fresh_fruit_shop_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.fresh_fruit_shop_website.model.Fruit;
import com.example.fresh_fruit_shop_website.model.User;
import com.example.fresh_fruit_shop_website.service.CartService;
import com.example.fresh_fruit_shop_website.service.FavoriteService;
import com.example.fresh_fruit_shop_website.service.FruitService;
import com.example.fresh_fruit_shop_website.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final FruitService fruitService;
    private final FavoriteService favoriteService;
    private final CartService cartService;
    private final UserService userService;

    public DashboardController(FruitService fruitService, FavoriteService favoriteService,
                               CartService cartService, UserService userService) {
        this.fruitService = fruitService;
        this.favoriteService = favoriteService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public String dashboard(Model model) {
        User user = userService.getCurrentUser();
        List<Fruit> fruits = fruitService.getAllFruits();

        model.addAttribute("fruits", fruits);
        model.addAttribute("userFavorites", favoriteService.getUserFavorites(user));
        model.addAttribute("cartItemCount", cartService.getCartItemCount()); // For navbar badge/count
        return "dashboard";
    }

    @PostMapping("/favorites/{fruitId}")
    public String toggleFavorite(@PathVariable Integer fruitId) {  // changed Long to Integer
        Fruit fruit = fruitService.getFruitById(fruitId);
        favoriteService.toggleFavorite(userService.getCurrentUser(), fruit);
        return "redirect:/dashboard";
    }

    @PostMapping("/cart/add/{fruitId}")
    public String addToCart(@PathVariable Integer fruitId) {  // changed Long to Integer
        cartService.addToCart(fruitId);
        return "redirect:/dashboard";
    }
}
