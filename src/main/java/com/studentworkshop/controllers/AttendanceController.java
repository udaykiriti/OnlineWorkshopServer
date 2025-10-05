package com.studentworkshop.controllers;

import com.studentworkshop.models.Attendance;
import com.studentworkshop.models.Registration;
import com.studentworkshop.services.AttendanceService;
import com.studentworkshop.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AttendanceController handles all REST API requests
 * It handles the rest apis
 * related to attendance management in workshops.
 */

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;  // Service to manage attendance records

    @Autowired
    private RegistrationService registrationService; // Service to fetch registered participants

    // Logger to record events, warnings, and errors for debugging
    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    /**
     * Endpoint to mark attendance for a user in a workshop.
     * Input: Attendance object (contains workshopId, username, present/absent).
     * Output: Returns the saved attendance record.
     */
    @PostMapping("/mark")
    public ResponseEntity<Attendance> markAttendance(@RequestBody Attendance attendance) {
        try {
            // Validate input: Workshop ID and username are required
            if (attendance.getWorkshopId() == null || attendance.getUsername() == null) {
                logger.warn("Invalid input: Workshop ID or Username is null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Call service to save attendance (1 = present, 0 = absent)
            Attendance savedAttendance = attendanceService.markAttendance(
                    attendance.getWorkshopId(),
                    attendance.getUsername(),
                    attendance.isPresent() ? 1 : 0 
            );

            logger.info("Successfully marked attendance: {}", savedAttendance);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAttendance);

        } catch (RuntimeException e) {
            // Log the error and return appropriate response
            logger.error("Error marking attendance: {}", e.getMessage());

            // If registration not found, return 404
            if (e.getMessage().contains("Registration not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // For all other unexpected errors, return 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * Endpoint to fetch all attendance records for a given workshop.
     * @param workshopId - ID of the workshop
     * @return List of attendance records for the workshop
     */
    @GetMapping("/workshop/{workshopId}")
    public ResponseEntity<List<Attendance>> getAttendanceByWorkshop(@PathVariable Long workshopId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByWorkshop(workshopId);

        // If no records , return 204 like no rec not found ...
        if (attendanceList.isEmpty()) {
            logger.warn("No attendance records found for workshopId: {}", workshopId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(attendanceList);
    }

    /**
     * Endpoint to fetch attendance records of a specific user.
     * @param username - The username of the participant
     * @return List of attendance records for the user
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Attendance>> getUserAttendance(@PathVariable String username) {
        List<Attendance> attendanceList = attendanceService.getUserAttendance(username);

        // If no records found for the user, return 204 (No Content)
        if (attendanceList.isEmpty()) {
            logger.warn("No attendance records found for username: {}", username);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(attendanceList);
    }

    /**
     * Endpoint to fetch all participants of a workshop.
     * @param workshopId - ID of the workshop
     * @return List of registered participants
     */
    @GetMapping("/workshop/{workshopId}/participants")
    public ResponseEntity<List<Registration>> getParticipantsByWorkshop(@PathVariable Long workshopId) {
        List<Registration> participants = registrationService.getRegisteredStudentsByWorkshop(workshopId);

        // If no participants then ->>, return 204 (No Content)
        if (participants.isEmpty()) {
            // If no records found then log the not found msg
            logger.warn("No participants found for workshopId: {}", workshopId);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(participants);
    }
}
