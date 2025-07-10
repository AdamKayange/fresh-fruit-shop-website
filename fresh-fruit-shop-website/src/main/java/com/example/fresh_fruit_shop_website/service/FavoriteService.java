package com.example.fresh_fruit_shop_website.service;

import com.example.fresh_fruit_shop_website.model.Favorite;
import com.example.fresh_fruit_shop_website.model.Fruit;
import com.example.fresh_fruit_shop_website.model.User;
import com.example.fresh_fruit_shop_website.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserService userService;

    public FavoriteService(FavoriteRepository favoriteRepository, UserService userService) {
        this.favoriteRepository = favoriteRepository;
        this.userService = userService;
    }

    /**
     * Toggle favorite for the current logged-in user and given fruit ID.
     * If already favorite, remove it; otherwise add it.
     */
    public void toggleFavorite(Integer fruitId) {
        User user = userService.getCurrentUser();
        Fruit fruit = new Fruit();
        fruit.setFruitId(fruitId);

        toggleFavorite(user, fruit);
    }

    /**
     * Toggle favorite for a given user and fruit.
     */
    public void toggleFavorite(User user, Fruit fruit) {
        favoriteRepository.findByUserAndFruit(user, fruit)
            .ifPresentOrElse(
                favoriteRepository::delete,
                () -> {
                    Favorite newFav = new Favorite();
                    newFav.setUser(user);
                    newFav.setFruit(fruit);
                    favoriteRepository.save(newFav);
                }
            );
    }

    /**
     * Get favorite fruits for the current user.
     */
    public List<Fruit> getUserFavorites() {
        User user = userService.getCurrentUser();
        return getUserFavorites(user);
    }

    /**
     * Get favorite fruits for a specific user.
     */
    public List<Fruit> getUserFavorites(User user) {
        return favoriteRepository.findByUser(user)
                .stream()
                .map(Favorite::getFruit)
                .toList();
    }

    /**
     * Check if a fruit is favorite for the current user.
     */
    public boolean isFavorite(Integer fruitId) {
        User user = userService.getCurrentUser();
        Fruit fruit = new Fruit();
        fruit.setFruitId(fruitId);
        return favoriteRepository.findByUserAndFruit(user, fruit).isPresent();
    }
}
