package com.example.fresh_fruit_shop_website.controller;

import com.example.fresh_fruit_shop_website.model.Fruit;
import com.example.fresh_fruit_shop_website.model.Order;
import com.example.fresh_fruit_shop_website.model.User;
import com.example.fresh_fruit_shop_website.service.FruitService;
import com.example.fresh_fruit_shop_website.service.OrderService;
import com.example.fresh_fruit_shop_website.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final FruitService fruitService;
    private final UserService userService;
    private final OrderService orderService;

    public AdminController(FruitService fruitService,
                           UserService userService,
                           OrderService orderService) {
        this.fruitService = fruitService;
        this.userService = userService;
        this.orderService = orderService;
    }

    //
    // 1) ADMIN DASHBOARD
    //
    @GetMapping({ "", "/", "/dashboard" })
    public String dashboard(Model model, Principal principal) {
        model.addAttribute("fruitCount", fruitService.countFruits());
        model.addAttribute("userCount", userService.countUsers());
        model.addAttribute("orderCount", orderService.countOrders());
        model.addAttribute("adminName", principal.getName());
        return "admin/dashboard";
    }

    //
    // 2) FRUIT MANAGEMENT
    //
    @GetMapping("/fruits")
    public String listFruits(Model model) {
        model.addAttribute("fruits", fruitService.getAllFruits());
        return "admin/fruits";
    }

    @GetMapping("/fruits/add")
    public String showAddFruitForm(Model model) {
        model.addAttribute("fruit", new Fruit());
        return "admin/add-fruit";
    }

    @PostMapping("/fruits/add")
    public String addFruit(@ModelAttribute Fruit fruit) {
        fruitService.saveFruit(fruit);
        return "redirect:/admin/fruits";
    }

    @GetMapping("/fruits/edit/{id}")
    public String showEditFruitForm(@PathVariable Integer id, Model model) {
        Fruit fruit = fruitService.getFruitById(id);
        if (fruit == null) {
            return "redirect:/admin/fruits"; // fallback
        }
        model.addAttribute("fruit", fruit);
        return "admin/edit-fruit";
    }

    @PostMapping("/fruits/edit/{id}")
    public String updateFruit(@PathVariable Integer id, @ModelAttribute Fruit fruit) {
        fruit.setFruitId(id);
        fruitService.saveFruit(fruit);
        return "redirect:/admin/fruits";
    }

    @PostMapping("/fruits/delete/{id}")
    public String deleteFruit(@PathVariable Integer id) {
        fruitService.deleteFruit(id);
        return "redirect:/admin/fruits";
    }

    //
    // 3) USER MANAGEMENT
    //
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users";
    }

    @PostMapping("/users/toggle/{id}")
    public String toggleUserRole(@PathVariable Integer id) {
        userService.toggleAdminRole(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    //
    // 4) ORDER MANAGEMENT
    //
    @GetMapping("/orders")
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @PostMapping("/orders/confirm/{orderId}")
    public String confirmOrder(@PathVariable Integer orderId) {
        orderService.confirmOrder(orderId);
        return "redirect:/admin/orders";
    }
}
