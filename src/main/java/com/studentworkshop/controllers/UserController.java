package com.studentworkshop.controllers;

import com.studentworkshop.models.User;
import com.studentworkshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users") // Base URL for all user-related endpoints
public class UserController {

    @Autowired
    private UserService userService; // Injecting the UserService to handle business logic

    // GET /api/users - Retrieve all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users); // Return 200 OK with list of users
    }

    // POST /api/users - Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser); // Return 201 Created with user
    }

    // GET /api/users/{username} - Retrieve user by username
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build(); // Return 200 or 404
    }

    // PUT /api/users/{id} - Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build(); // Return 404 if user not found
        }

        // Update only non-null and non-empty fields
        if (userDetails.getUsername() != null && !userDetails.getUsername().isEmpty()) {
            user.setUsername(userDetails.getUsername());
        }
        if (userDetails.getEmail() != null && !userDetails.getEmail().isEmpty()) {
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getDob() != null) {
            user.setDob(userDetails.getDob());
        }
        if (userDetails.getGender() != null && !userDetails.getGender().isEmpty()) {
            user.setGender(userDetails.getGender());
        }
        if (userDetails.getPhoneNumber() != null && !userDetails.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(userDetails.getPhoneNumber());
        }
        if (userDetails.getRole() != null && !userDetails.getRole().isEmpty()) {
            user.setRole(userDetails.getRole());
        }

        User updatedUser = userService.saveUser(user);
        return ResponseEntity.ok(updatedUser); // Return 200 OK with updated user
    }

    // DELETE /api/users/{id} - Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // Return 204 No Content
    }

    // GET /api/users/summary/gender - Get count of users by gender
    @GetMapping("/summary/gender")
    public ResponseEntity<Map<String, Long>> getGenderSummary() {
        List<User> users = userService.getAllUsers();
        Map<String, Long> genderSummary = users.stream()
            .collect(Collectors.groupingBy(
                user -> user.getGender() == null ? "Unknown" : user.getGender(), // Handle null gender
                Collectors.counting()
            ));
        return ResponseEntity.ok(genderSummary); // Return map of gender counts
    }
}
