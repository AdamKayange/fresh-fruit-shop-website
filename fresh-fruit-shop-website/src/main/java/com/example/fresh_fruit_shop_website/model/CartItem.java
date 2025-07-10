package com.example.fresh_fruit_shop_website.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Kila cart item ni ya user mmoja
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    // Kila cart item inahusiana na fruit moja
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fruit_id")
    private Fruit fruit;

    // Idadi ya units za fruit hii kwenye cart
    @Column(nullable = false)
    private int quantity;

    // Constructors
    public CartItem() {}

    public CartItem(User user, Fruit fruit, int quantity) {
        this.user = user;
        this.fruit = fruit;
        this.quantity = quantity;
    }

    // Getters na setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Fruit getFruit() {
        return fruit;
    }

    public void setFruit(Fruit fruit) {
        this.fruit = fruit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Override equals na hashCode kwa kutumia id (optional lakini recommended)

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(id, cartItem.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
