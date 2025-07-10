package com.example.fresh_fruit_shop_website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.fresh_fruit_shop_website.model.User;
import com.example.fresh_fruit_shop_website.service.UserService;

@Controller
public class AuthController {

    @Autowired 
    private UserService userService;

    // Onyesha fomu ya register
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle registration POST
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") User user, Model model) {
        if (userService.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists");
            return "register";
        }

        userService.register(user);  // Hapa assume register inahandle password encoding

        // Badilisha hapa: tumia redirect kwenda login na message kama query param
        return "redirect:/login?registerSuccess";
    }

    // Onyesha login page
    @GetMapping("/login")
    public String login(@RequestParam(value = "registerSuccess", required = false) String registerSuccess, Model model) {
        if (registerSuccess != null) {
            model.addAttribute("success", "Registration successful! Please login.");
        }
        return "login";
    }
}
