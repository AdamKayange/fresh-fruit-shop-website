package com.example.fresh_fruit_shop_website.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.fresh_fruit_shop_website.model.Fruit;

import java.util.List;

@Repository
public interface FruitRepository extends JpaRepository<Fruit, Integer> {

    // Find fruits by category
    List<Fruit> findByCategory(String category);

    // Get distinct categories (as list of Strings)
    @Query("SELECT DISTINCT f.category FROM Fruit f")
    List<String> findDistinctCategories();

    // Count total fruits (optional, JpaRepository already has count())
    @Query("SELECT COUNT(f) FROM Fruit f")
    long countFruits();
}
