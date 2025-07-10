package com.example.fresh_fruit_shop_website.service;

import com.example.fresh_fruit_shop_website.model.User;
import com.example.fresh_fruit_shop_website.repository.UserRepository;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // ✅ 1. Check hardcoded admin
        if (email.equals("admin@fruitshop.com")) {
            String encodedAdminPassword = passwordEncoder.encode("admin123"); // hashed on each login
            return new org.springframework.security.core.userdetails.User(
                "admin@fruitshop.com",
                encodedAdminPassword,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        // ✅ 2. Otherwise, fetch from DB
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<SimpleGrantedAuthority> authorities = user.getIsAdmin() != null && user.getIsAdmin()
            ? Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"))
            : Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            authorities
        );
    }
}
