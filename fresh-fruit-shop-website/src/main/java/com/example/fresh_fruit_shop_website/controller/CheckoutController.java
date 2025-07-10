package com.example.fresh_fruit_shop_website.controller;

import com.example.fresh_fruit_shop_website.model.CartItem;
import com.example.fresh_fruit_shop_website.model.Order;
import com.example.fresh_fruit_shop_website.model.OrderItem;
import com.example.fresh_fruit_shop_website.model.User;
import com.example.fresh_fruit_shop_website.service.CartService;
import com.example.fresh_fruit_shop_website.service.OrderService;
import com.example.fresh_fruit_shop_website.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class CheckoutController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UserService userService;

    public CheckoutController(CartService cartService, OrderService orderService, UserService userService) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.userService = userService;
    }

    // Show checkout page
    @GetMapping("/checkout")
    public String showCheckout(Model model) {
        List<CartItem> cartItems = cartService.getUserCart();
        model.addAttribute("cartItems", cartItems);

        double total = cartItems.stream()
                .mapToDouble(item -> item.getFruit().getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("totalPrice", total);
        return "checkout";  // checkout.html
    }

    // Handle form submission with shipping details
    @PostMapping("/checkout")
    public String processCheckout(@RequestParam String shippingName,
                                  @RequestParam String shippingAddress,
                                  @RequestParam String shippingCity,
                                  @RequestParam String shippingPhone,
                                  @RequestParam String shippingZipcode,
                                  Principal principal,
                                  HttpSession session) {

        User user = userService.findByEmail(principal.getName());
        List<CartItem> cartItems = cartService.getUserCart();

        if (cartItems == null || cartItems.isEmpty()) {
            return "redirect:/cart"; // no items to process
        }

        Order order = new Order();
        order.setUser(user);
        order.setShippingName(shippingName);
        order.setShippingAddress(shippingAddress);
        order.setShippingCity(shippingCity);
        order.setShippingPhone(shippingPhone);
        order.setShippingZipcode(shippingZipcode);
        order.setStatus(Order.Status.PENDING);

        double total = cartItems.stream()
                .mapToDouble(item -> item.getFruit().getPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(total);

        List<OrderItem> orderItems = cartItems.stream().map(item -> {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setFruit(item.getFruit());
            oi.setPrice(item.getFruit().getPrice());
            oi.setQuantity(item.getQuantity());
            return oi;
        }).toList();

        order.setOrderItems(orderItems);

        orderService.saveOrder(order);
        cartService.clearCart(); // Clear cart after saving

        return "redirect:/order-success";
    }

    // Success page
    @GetMapping("/order-success")
    public String orderSuccess() {
        return "order-success";
    }
}
