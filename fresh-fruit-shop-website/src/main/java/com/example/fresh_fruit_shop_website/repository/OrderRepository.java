package com.example.fresh_fruit_shop_website.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.fresh_fruit_shop_website.model.Order;
import com.example.fresh_fruit_shop_website.model.User;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUser(User user);
}
