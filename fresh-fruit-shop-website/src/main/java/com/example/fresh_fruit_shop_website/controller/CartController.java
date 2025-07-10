package com.example.fresh_fruit_shop_website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.fresh_fruit_shop_website.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // Onyesha orodha ya vitu vilivyo kwenye cart
    @GetMapping
    public String viewCart(Model model) {
        model.addAttribute("cartItems", cartService.getUserCart());
        model.addAttribute("cartItemCount", cartService.getCartItemCount());
        return "cart";  // Thymeleaf template: cart.html
    }

    // Ongeza fruit kwenye cart kwa fruitId
    @PostMapping("/add/{id}")
    public String addToCart(@PathVariable("id") Integer fruitId) {
        cartService.addToCart(fruitId);
        return "redirect:/cart";  // Rudisha kwenye cart page baada ya kuongeza
    }

    // Ondoa item kutoka cart kwa fruitId
    @PostMapping("/remove/{id}")
    public String removeItem(@PathVariable("id") Integer fruitId) {
        cartService.removeFromCart(fruitId);
        return "redirect:/cart";
    }

    // Sasisha quantity ya item kwa kuongeza, kupunguza, au kuweka quantity kamili
    @PostMapping("/update/{id}")
    public String updateQuantity(@PathVariable("id") Integer fruitId,
                                 @RequestParam(name = "change", required = false) Integer change,
                                 @RequestParam(name = "quantity", required = false) Integer quantity) {

        if (change != null) {
            if (change == 1) {
                cartService.increaseQuantity(fruitId);
            } else if (change == -1) {
                cartService.decreaseQuantity(fruitId);
            }
        } else if (quantity != null) {
            if (quantity > 0) {
                cartService.setQuantity(fruitId, quantity);
            } else {
                cartService.removeFromCart(fruitId);
            }
        }
        return "redirect:/cart";
    }
}
