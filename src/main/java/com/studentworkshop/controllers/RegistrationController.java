package com.studentworkshop.controllers;

import com.studentworkshop.models.Registration;
import com.studentworkshop.models.User;
import com.studentworkshop.models.Workshop;
import com.studentworkshop.services.RegistrationService;
import com.studentworkshop.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/registration") // Base URL for registration APIs
public class RegistrationController {

    // Inject RegistrationService to handle workshop registration logic
    @Autowired
    private RegistrationService registrationService;

    // Inject UserService to fetch user details
    @Autowired
    private UserService userService; 

    // Logger for debugging and tracking events
    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    /**
     * Endpoint: POST /api/registration
     * Registers a user for a workshop.
     * 1. Check if user exists using username.
     * 2. If user exists, fetch email and attach to registration object.
     * 3. Call service to save registration.
     * 4. Return the saved registration object.
     */
    @PostMapping
    public ResponseEntity<Registration> registerWorkshop(@RequestBody Registration registration) {
        // Find user by username
        User user = userService.findByUsername(registration.getUsername()); 
        
        // If user is not found, return 400 Bad Request
        if (user == null) {
            logger.warn("User not found for username: {}", registration.getUsername());
            return ResponseEntity.badRequest().build(); 
        }

        // Set user's email in the registration object
        registration.setEmail(user.getEmail());
        
        // Save registration
        Registration registered = registrationService.registerWorkshop(registration);
        logger.info("Successfully registered workshop: {}", registered);
        
        // Return the registered object as response
        return ResponseEntity.ok(registered);
    }

    /**
     * Endpoint: DELETE /api/registration/{workshopId}?username=<username>
     * Unregisters a user from a given workshop.
     * 1. Uses workshopId and username to identify the registration.
     * 2. Calls service to delete/unregister.
     * 3. Returns 204 No Content on success or 404 Not Found if failed.
     */
    @DeleteMapping("/{workshopId}")
    public ResponseEntity<Void> unregisterWorkshop(@PathVariable Long workshopId, @RequestParam String username) {
        // Call service to unregister
        boolean success = registrationService.unregisterWorkshop(workshopId, username);
        
        if (success) {
            logger.info("Successfully unregistered username: {} from workshopId: {}", username, workshopId);
            return ResponseEntity.noContent().build(); // 204 success, no body
        } else {
            logger.warn("Failed to unregister username: {} from workshopId: {} (not found)", username, workshopId);
            return ResponseEntity.notFound().build(); // 404 not found
        }
    }

    /**
     * Endpoint: GET /api/registration/workshops/{username}
     * Fetch all workshops registered by a user.
     * 1. Retrieve workshops for given username.
     * 2. If no workshops found, return 204 No Content.
     * 3. Otherwise, return the list of workshops.
     */
    @GetMapping("/workshops/{username}")
    public ResponseEntity<List<Workshop>> getRegisteredWorkshops(@PathVariable String username) {
        logger.info("Fetching registered workshops for username: {}", username);
        
        // Fetch registered workshops
        List<Workshop> workshops = registrationService.getRegisteredWorkshops(username);
        
        if (workshops.isEmpty()) {
            logger.warn("No registered workshops found for username: {}", username);
            return ResponseEntity.noContent().build(); // 204 no workshops
        }
        
        // Return workshops list
        return ResponseEntity.ok(workshops); 
    }
}
