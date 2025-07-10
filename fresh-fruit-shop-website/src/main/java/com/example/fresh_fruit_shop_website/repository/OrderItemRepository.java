package com.example.fresh_fruit_shop_website.repository;

// OrderItemRepository.java

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fresh_fruit_shop_website.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
