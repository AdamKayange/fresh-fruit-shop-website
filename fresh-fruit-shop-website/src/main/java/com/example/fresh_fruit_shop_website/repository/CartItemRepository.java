package com.example.fresh_fruit_shop_website.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fresh_fruit_shop_website.model.CartItem;
import com.example.fresh_fruit_shop_website.model.Fruit;
import com.example.fresh_fruit_shop_website.model.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing CartItem entities.
 * Extends JpaRepository to provide basic CRUD operations.
 */
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    /**
     * Find all cart items belonging to a specific user.
     * @param user the user whose cart items to retrieve
     * @return list of CartItem objects for the user
     */
    List<CartItem> findByUser(User user);

    /**
     * Find a cart item by user and fruit.
     * Useful to check if a fruit is already in user's cart.
     * @param user the user who owns the cart item
     * @param fruit the fruit to find in the cart
     * @return optional CartItem if found
     */
    Optional<CartItem> findByUserAndFruit(User user, Fruit fruit);

    /**
     * Delete a cart item by user and fruit.
     * @param user the user who owns the cart item
     * @param fruit the fruit to remove from the cart
     */
    void deleteByUserAndFruit(User user, Fruit fruit);
}
