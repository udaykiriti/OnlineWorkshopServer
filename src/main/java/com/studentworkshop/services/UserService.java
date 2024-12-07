package com.studentworkshop.services;

import com.studentworkshop.models.User;
import com.studentworkshop.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private TokenService tokenService; 
    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public User addUser(User user) {
        
        return userRepository.save(user);
    }
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void sendPasswordResetEmail(String email) {
        User user = findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        String token = tokenService.generateTokenForUser(user);
String resetLink = "https://onlineworkshop-server-production.up.railway.app/reset-password?token=" + token;
        emailService.sendPasswordResetEmail(email, resetLink);
    }

    public void resetPassword(String token, String newPassword) {
        User user = tokenService.validateTokenAndGetUser(token); 
        if (user == null) {
            throw new RuntimeException("Invalid or expired token.");
        }
        user.setPassword(newPassword); 
        saveUser(user); 
        tokenService.invalidateToken(token); 
    }
    
}
