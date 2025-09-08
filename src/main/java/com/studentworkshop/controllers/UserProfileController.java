package com.studentworkshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.studentworkshop.models.UserProfile;
import com.studentworkshop.services.UserProfileService;

@RestController
@RequestMapping("/api/user-profile") // Base URL for user profile-related endpoints
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService; // Injecting the UserProfileService

    // GET /api/user-profile/{username} - Retrieve user profile by username
    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable String username) {
        // Return 200 OK with profile if found, else 404 Not Found
        return userProfileService.getUserProfileByUsername(username)
                .map(profile -> ResponseEntity.ok(profile))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // PUT /api/user-profile/{username} - Update user profile by username
    @PutMapping("/{username}")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable String username, @RequestBody UserProfile updatedProfile) {
        UserProfile profile = userProfileService.updateUserProfile(username, updatedProfile);
        if (profile != null) {
            return ResponseEntity.ok(profile); // Return 200 OK with updated profile
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Return 404 if user not found
    }
}
