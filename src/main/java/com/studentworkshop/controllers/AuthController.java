package com.studentworkshop.controllers;

import com.studentworkshop.models.User;
import com.studentworkshop.services.UserService;
import com.studentworkshop.services.PasswordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        User existingUserByUsername = userService.findByUsername(user.getUsername());
        if (existingUserByUsername != null) {
            return ResponseEntity.badRequest().body("Username already taken");
        }

        User existingUserByEmail = userService.findByEmail(user.getEmail());
        if (existingUserByEmail != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        user.setPassword(passwordService.hashPassword(user.getPassword()));
        user.setRole("student"); 
        userService.saveUser(user);
        return ResponseEntity.ok("Signup successful");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && passwordService.verifyPassword(user.getPassword(), existingUser.getPassword())) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("role", existingUser.getRole());
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        try {
            userService.sendPasswordResetEmail(email);
            return ResponseEntity.ok("Password reset link sent to email.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        try {
            userService.resetPassword(token, newPassword);
            return ResponseEntity.ok("Password reset successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
