package com.studentworkshop.controllers;

import com.studentworkshop.models.User;
import com.studentworkshop.services.UserService;
import com.studentworkshop.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * AuthController handles authentication-related requests such as
 * signup, login, forgot password, and reset password.
 * 
 * Base URL: /api/auth
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "https://online-workshop-nine.vercel.app"})
public class AuthController {

    // Service that handles user-related operations
    @Autowired
    private UserService userService;

    // Service that handles password hashing and verification
    @Autowired
    private PasswordService passwordService;

    /**
     * Signup endpoint for new user registration.
     * 
     * @param user User object from request body containing username, email, and password.
     * @return ResponseEntity with success or failure message.
     */
    @PostMapping("/signup")
    public ResponseEntity<Map<String, String>> signup(@RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        // Check if the username already exists in the database
        User existingUserByUsername = userService.findByUsername(user.getUsername());
        if (existingUserByUsername != null) {
            response.put("message", "Username already taken");
            return ResponseEntity.badRequest().body(response);
        }

        // Check if the email is already registered
        User existingUserByEmail = userService.findByEmail(user.getEmail());
        if (existingUserByEmail != null) {
            response.put("message", "Email already exists");
            return ResponseEntity.badRequest().body(response);
        }

        // Encrypt/hash the password before saving and set default role as "student"
        user.setPassword(passwordService.hashPassword(user.getPassword()));
        user.setRole("student");

        // Save the new user to the database
        userService.saveUser(user);

        response.put("message", "Signup successful");
        return ResponseEntity.ok(response);
    }

    /**
     * Login endpoint to authenticate an existing user.
     * 
     * @param user User object containing username and password.
     * @return ResponseEntity with login status and role if successful.
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();

        // Find the user by username
        User existingUser = userService.findByUsername(user.getUsername());

        // If user exists and password matches, return success
        if (existingUser != null && passwordService.verifyPassword(user.getPassword(), existingUser.getPassword())) {
            response.put("message", "Login successful");
            response.put("role", existingUser.getRole()); // Return role (e.g., student/admin)
            return ResponseEntity.ok(response);
        }

        // If invalid credentials, return 401 Unauthorized
        response.put("message", "Invalid credentials");
        return ResponseEntity.status(401).body(response);
    }

    /**
     * Forgot password endpoint.
     * Sends a password reset link to the user's email.
     * 
     * @param request JSON object containing the user's email.
     * @return ResponseEntity with success or error message.
     */
    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        String email = request.get("email"); // Extract email from request body

        try {
            // Generate and send password reset email
            userService.sendPasswordResetEmail(email);
            response.put("message", "Password reset link sent to email.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Handle invalid email or errors
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * Reset password endpoint.
     * Resets the user's password using the provided token.
     * 
     * @param token Reset token (sent via email).
     * @param newPassword New password entered by the user.
     * @return ResponseEntity with success or error message.
     */
    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Map<String, String> response = new HashMap<>();

        try {
            // Validate token and update the password
            userService.resetPassword(token, newPassword);
            response.put("message", "Password reset successfully.");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Handle invalid token or errors
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
