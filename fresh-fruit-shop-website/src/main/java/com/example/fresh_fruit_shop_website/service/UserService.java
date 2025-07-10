package com.example.fresh_fruit_shop_website.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.fresh_fruit_shop_website.model.User;
import com.example.fresh_fruit_shop_website.repository.UserRepository;

@Service
public class UserService {

    @Autowired 
    private UserRepository userRepository;
    
    @Autowired 
    private PasswordEncoder passwordEncoder;

    // Register user with encoded password
    public void register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    // Find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    // Get current logged-in user from Spring Security context
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || "anonymousUser".equals(auth.getPrincipal())) {
            return null;
        }
        String email = auth.getName();  // username is email
        return findByEmail(email);
    }

    // Count all users
    public long countUsers() {
        return userRepository.count();
    }

    // Return all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Toggle the isAdmin flag for a given user
    public void toggleAdminRole(Integer id) {
        User u = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No user with id " + id));
        u.setIsAdmin(!Boolean.TRUE.equals(u.getIsAdmin()));
        userRepository.save(u);
    }

    // Delete a user by id
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
