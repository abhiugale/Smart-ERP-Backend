package com.jspm.SmartErp.service;

import com.jspm.SmartErp.model.User;
import com.jspm.SmartErp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public User save(User user) {
        // Always encode password before saving if it's not already encoded
        if (user.getPassword() != null && !isPasswordEncoded(user.getPassword())) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            System.out.println("🔐 Password encoded for user: " + user.getEmail());
        }
        return userRepository.save(user);
    }

    // Add this helper method
    private boolean isPasswordEncoded(String password) {
        return password != null && password.startsWith("$2a$");
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByPhone(String phone) {
        return userRepository.existsByPhone(phone);
    }

    // Method to verify password
    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}