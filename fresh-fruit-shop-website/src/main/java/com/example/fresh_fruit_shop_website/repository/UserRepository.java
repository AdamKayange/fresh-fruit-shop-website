package com.example.fresh_fruit_shop_website.repository;

import com.example.fresh_fruit_shop_website.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    
    // Only custom method you need:
    Optional<User> findByEmail(String email);
    
   
}
