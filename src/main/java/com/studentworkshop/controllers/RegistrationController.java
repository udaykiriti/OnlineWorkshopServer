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
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserService userService; 

    private static final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @PostMapping
    public ResponseEntity<Registration> registerWorkshop(@RequestBody Registration registration) {
        User user = userService.findByUsername(registration.getUsername()); 
        if (user == null) {
            logger.warn("User not found for username: {}", registration.getUsername());
            return ResponseEntity.badRequest().build(); 
        }

        registration.setEmail(user.getEmail());
        Registration registered = registrationService.registerWorkshop(registration);
        logger.info("Successfully registered workshop: {}", registered);
        return ResponseEntity.ok(registered);
    }

    @DeleteMapping("/{workshopId}")
    public ResponseEntity<Void> unregisterWorkshop(@PathVariable Long workshopId, @RequestParam String username) {
        boolean success = registrationService.unregisterWorkshop(workshopId, username);
        if (success) {
            logger.info("Successfully unregistered username: {} from workshopId: {}", username, workshopId);
            return ResponseEntity.noContent().build(); 
        } else {
            logger.warn("Failed to unregister username: {} from workshopId: {} (not found)", username, workshopId);
            return ResponseEntity.notFound().build(); 
        }
    }

    @GetMapping("/workshops/{username}")
    public ResponseEntity<List<Workshop>> getRegisteredWorkshops(@PathVariable String username) {
        logger.info("Fetching registered workshops for username: {}", username);
        List<Workshop> workshops = registrationService.getRegisteredWorkshops(username);
        if (workshops.isEmpty()) {
            logger.warn("No registered workshops found for username: {}", username);
            return ResponseEntity.noContent().build(); 
        }
        return ResponseEntity.ok(workshops); 
    }
}
