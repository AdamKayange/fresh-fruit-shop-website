package com.example.fresh_fruit_shop_website.controller;

import com.example.fresh_fruit_shop_website.model.Order;
import com.example.fresh_fruit_shop_website.service.CartService;
import com.example.fresh_fruit_shop_website.service.OrderService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    /**
     * Endpoint to place an order from current user's cart.
     * Redirects to orders page after successful placement.
     */
    @PostMapping("/place")
    public String placeOrder(Model model) {
        Order order = orderService.placeOrder();

        if (order == null) {
            // Cart was empty, redirect user back to cart page with an error message
            model.addAttribute("error", "Your cart is empty. Add items before placing an order.");
            return "redirect:/cart";
        }

        // Order placed successfully - cart is cleared by service
        model.addAttribute("success", "Order placed successfully!");
        model.addAttribute("cartItemCount", 0);  // Cart should be empty now

        return "redirect:/orders/my-orders";
    }

    /**
     * Display list of orders for the currently logged-in user.
     */
    @GetMapping("/my-orders")
    public String viewMyOrders(Model model) {
        model.addAttribute("orders", orderService.getUserOrders());
        model.addAttribute("cartItemCount", cartService.getCartItemCount());  // for navbar badge, etc.
        return "orders"; // Thymeleaf or JSP view to display orders list
    }
}
