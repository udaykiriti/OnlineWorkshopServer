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
@CrossOrigin(origins = {"http://localhost:3000", "https://online-workshop-nine.vercel.app"})
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        // Check if the username already exists
        User existingUserByUsername = userService.findByUsername(user.getUsername());
        if (existingUserByUsername != null) {
            response.put("message", "Username already taken");
            return ResponseEntity.badRequest().body(response);
        }

        // Check if the email already exists
        User existingUserByEmail = userService.findByEmail(user.getEmail());
        if (existingUserByEmail != null) {
            response.put("message", "Email already exists");
            return ResponseEntity.badRequest().body(response);
        }

        // Hash the password
        user.setPassword(passwordService.hashPassword(user.getPassword()));
        user.setRole("student"); // Default role can be set here
        userService.saveUser(user);

        response.put("message", "Signup successful");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        // Check if the user exists by username
        User existingUser = userService.findByUsername(user.getUsername());
        if (existingUser != null && passwordService.verifyPassword(user.getPassword(), existingUser.getPassword())) {
            response.put("message", "Login successful");
            response.put("role", existingUser.getRole());
            return ResponseEntity.ok(response);
        }

        response.put("message", "Invalid credentials");
        return ResponseEntity.status(401).body(response);  // Unauthorized status
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        String email = request.get("email");

        try {
            userService.sendPasswordResetEmail(email);
            response.put("message", "Password reset link sent to email.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        String token = request.get("token");
        String newPassword = request.get("newPassword");

        try {
            userService.resetPassword(token, newPassword);
            response.put("message", "Password reset successfully.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
