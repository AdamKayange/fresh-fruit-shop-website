package com.example.fresh_fruit_shop_website.service;

import com.example.fresh_fruit_shop_website.model.*;
import com.example.fresh_fruit_shop_website.repository.OrderRepository;
import com.example.fresh_fruit_shop_website.repository.OrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final CartService cartService;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;

    public OrderService(CartService cartService,
                        OrderRepository orderRepository,
                        OrderItemRepository orderItemRepository,
                        UserService userService) {
        this.cartService = cartService;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userService = userService;
    }

    @Transactional
    public Order placeOrder(String shippingName, String shippingAddress, String city, String phone, String zipcode) {
        User user = userService.getCurrentUser();
        List<CartItem> cartItems = cartService.getUserCart();

        if (cartItems.isEmpty()) return null;

        Order order = new Order();
        order.setUser(user);
        order.setShippingName(shippingName);
        order.setShippingAddress(shippingAddress);
        order.setShippingCity(city);
        order.setShippingPhone(phone);
        order.setShippingZipcode(zipcode);
        order.setStatus(Order.Status.PENDING);
        order.setOrderItems(new ArrayList<>());

        double total = 0.0;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFruit(cartItem.getFruit());
            orderItem.setQuantity(cartItem.getQuantity());
            double itemPrice = cartItem.getFruit().getPrice() * cartItem.getQuantity();
            orderItem.setPrice(itemPrice);

            total += itemPrice;
            order.getOrderItems().add(orderItem);
        }

        order.setTotalAmount(total);
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());

        cartService.clearCart();
        return order;
    }

    @Transactional
    public Order placeOrder() {
        User user = userService.getCurrentUser();
        List<CartItem> cartItems = cartService.getUserCart();

        if (cartItems.isEmpty()) return null;

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);
        order.setOrderItems(new ArrayList<>());

        double total = 0.0;

        for (CartItem cartItem : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFruit(cartItem.getFruit());
            orderItem.setQuantity(cartItem.getQuantity());
            double itemPrice = cartItem.getFruit().getPrice() * cartItem.getQuantity();
            orderItem.setPrice(itemPrice);

            total += itemPrice;
            order.getOrderItems().add(orderItem);
        }

        order.setTotalAmount(total);
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());

        cartService.clearCart();
        return order;
    }

    public List<Order> getUserOrders() {
        return orderRepository.findByUser(userService.getCurrentUser());
    }

    // Count total orders in system
    public long countOrders() {
        return orderRepository.count();
    }

    // Get all orders in system
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // New: Confirm an order by setting its status to CONFIRMED
    @Transactional
    public void confirmOrder(Integer orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found with id: " + orderId));
        order.setStatus(Order.Status.CONFIRMED);  // make sure CONFIRMED exists in your Order.Status enum
        orderRepository.save(order);
    }

  public void saveOrder(Order order) {
    orderRepository.save(order);
}

}
