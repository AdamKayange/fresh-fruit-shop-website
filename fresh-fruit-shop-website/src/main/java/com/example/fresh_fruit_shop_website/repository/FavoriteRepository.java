package com.example.fresh_fruit_shop_website.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fresh_fruit_shop_website.model.Favorite;
import com.example.fresh_fruit_shop_website.model.Fruit;
import com.example.fresh_fruit_shop_website.model.User;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUser(User user);
    Optional<Favorite> findByUserAndFruit(User user, Fruit fruit);
    void deleteByUserAndFruit(User user, Fruit fruit);
}
