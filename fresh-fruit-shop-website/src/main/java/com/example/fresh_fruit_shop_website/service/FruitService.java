package com.example.fresh_fruit_shop_website.service;

import org.springframework.stereotype.Service;

import com.example.fresh_fruit_shop_website.model.Fruit;
import com.example.fresh_fruit_shop_website.repository.FruitRepository;

import java.util.List;

@Service
public class FruitService {

    private final FruitRepository fruitRepository;

    public FruitService(FruitRepository fruitRepository) {
        this.fruitRepository = fruitRepository;
    }

    // Get all fruits
    public List<Fruit> getAllFruits() {
        return fruitRepository.findAll();
    }

    // Get fruits by category
    public List<Fruit> getFruitsByCategory(String category) {
        return fruitRepository.findByCategory(category);
    }

    // Get all distinct categories
    public List<String> getAllCategories() {
        return fruitRepository.findDistinctCategories();
    }

    // Get fruit by id
    public Fruit getFruitById(Integer id) {
        return fruitRepository.findById(id).orElse(null);
    }

    // Save or update fruit
    public void saveFruit(Fruit fruit) {
        fruitRepository.save(fruit);
    }

    // Delete fruit by id
    public void deleteFruit(Integer id) {
        fruitRepository.deleteById(id);
    }

    // Count fruits
    public long countFruits() {
        return fruitRepository.count();  // JpaRepository's default method
        // or return fruitRepository.countFruits(); if you want to use the custom query
    }
}
