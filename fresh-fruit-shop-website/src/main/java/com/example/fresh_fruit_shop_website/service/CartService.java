package com.example.fresh_fruit_shop_website.service;

import org.springframework.stereotype.Service;

import com.example.fresh_fruit_shop_website.model.Cart;
import com.example.fresh_fruit_shop_website.model.CartItem;
import com.example.fresh_fruit_shop_website.model.Fruit;
import com.example.fresh_fruit_shop_website.model.User;
import com.example.fresh_fruit_shop_website.repository.CartItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final FruitService fruitService;

    public CartService(CartItemRepository cartItemRepository, UserService userService, FruitService fruitService) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.fruitService = fruitService;
    }

    public void addToCart(Integer fruitId) {
        User user = userService.getCurrentUser();
        Fruit fruit = fruitService.getFruitById(fruitId);

        cartItemRepository.findByUserAndFruit(user, fruit).ifPresentOrElse(
            item -> {
                item.setQuantity(item.getQuantity() + 1);
                cartItemRepository.save(item);
            },
            () -> {
                CartItem item = new CartItem();
                item.setUser(user);
                item.setFruit(fruit);
                item.setQuantity(1);
                cartItemRepository.save(item);
            }
        );
    }

    public void removeFromCart(Integer fruitId) {
        User user = userService.getCurrentUser();
        Fruit fruit = fruitService.getFruitById(fruitId);
        cartItemRepository.deleteByUserAndFruit(user, fruit);
    }

    public List<CartItem> getUserCart() {
        return cartItemRepository.findByUser(userService.getCurrentUser());
    }

    public int getCartItemCount() {
        return getUserCart().stream().mapToInt(CartItem::getQuantity).sum();
    }

    public void increaseQuantity(Integer fruitId) {
        User user = userService.getCurrentUser();
        Optional<CartItem> optionalItem = cartItemRepository.findByUserAndFruit(user, fruitService.getFruitById(fruitId));
        optionalItem.ifPresent(item -> {
            item.setQuantity(item.getQuantity() + 1);
            cartItemRepository.save(item);
        });
    }

    public void decreaseQuantity(Integer fruitId) {
        User user = userService.getCurrentUser();
        Optional<CartItem> optionalItem = cartItemRepository.findByUserAndFruit(user, fruitService.getFruitById(fruitId));
        optionalItem.ifPresent(item -> {
            int newQty = item.getQuantity() - 1;
            if (newQty > 0) {
                item.setQuantity(newQty);
                cartItemRepository.save(item);
            } else {
                cartItemRepository.delete(item);
            }
        });
    }

    public void setQuantity(Integer fruitId, int quantity) {
        if (quantity <= 0) {
            removeFromCart(fruitId);
            return;
        }
        User user = userService.getCurrentUser();
        Fruit fruit = fruitService.getFruitById(fruitId);
        Optional<CartItem> optionalItem = cartItemRepository.findByUserAndFruit(user, fruit);
        if (optionalItem.isPresent()) {
            CartItem item = optionalItem.get();
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        } else {
            CartItem item = new CartItem();
            item.setUser(user);
            item.setFruit(fruit);
            item.setQuantity(quantity);
            cartItemRepository.save(item);
        }
    }

    public void clearCart() {
        User user = userService.getCurrentUser();
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(cartItems);
    }

    public List<Cart> getUserCartItems() {
        // Ikiwa unamaanisha list ya CartItem badala ya Cart, badilisha return type na method hii kurudisha cart items badala ya Cart objects.
        // Kwa sasa, inaweza kurudisha null au throw exception kama huna Cart entity tofauti.
        return null; 
    }
}
