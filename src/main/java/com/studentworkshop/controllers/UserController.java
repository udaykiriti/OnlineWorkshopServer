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
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.findByUsername(username);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userService.getUserById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

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
        return ResponseEntity.ok(updatedUser);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/summary/gender")
    public ResponseEntity<Map<String, Long>> getGenderSummary() {
        List<User> users = userService.getAllUsers();
        Map<String, Long> genderSummary = users.stream()
            .collect(Collectors.groupingBy(
                user -> user.getGender() == null ? "Unknown" : user.getGender(),
                Collectors.counting()
            ));
        return ResponseEntity.ok(genderSummary);
    }
}  
