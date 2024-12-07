package com.studentworkshop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.studentworkshop.models.UserProfile;
import com.studentworkshop.services.UserProfileService;

@RestController
@RequestMapping("/api/user-profile")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @GetMapping("/{username}")
    public ResponseEntity<UserProfile> getUserProfile(@PathVariable String username) {
        return userProfileService.getUserProfileByUsername(username)
                .map(profile -> ResponseEntity.ok(profile))
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserProfile> updateUserProfile(@PathVariable String username, @RequestBody UserProfile updatedProfile) {
        UserProfile profile = userProfileService.updateUserProfile(username, updatedProfile);
        if (profile != null) {
            return ResponseEntity.ok(profile);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
