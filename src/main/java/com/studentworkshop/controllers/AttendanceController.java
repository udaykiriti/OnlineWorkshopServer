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

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private RegistrationService registrationService;

    private static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    @PostMapping("/mark")
    public ResponseEntity<Attendance> markAttendance(@RequestBody Attendance attendance) {
        try {
            if (attendance.getWorkshopId() == null || attendance.getUsername() == null) {
                logger.warn("Invalid input: Workshop ID or Username is null");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            Attendance savedAttendance = attendanceService.markAttendance(
                    attendance.getWorkshopId(),
                    attendance.getUsername(),
                    attendance.isPresent() ? 1 : 0 
            );
            logger.info("Successfully marked attendance: {}", savedAttendance);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedAttendance);
        } catch (RuntimeException e) {
            logger.error("Error marking attendance: {}", e.getMessage());
            if (e.getMessage().contains("Registration not found")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/workshop/{workshopId}")
    public ResponseEntity<List<Attendance>> getAttendanceByWorkshop(@PathVariable Long workshopId) {
        List<Attendance> attendanceList = attendanceService.getAttendanceByWorkshop(workshopId);
        if (attendanceList.isEmpty()) {
            logger.warn("No attendance records found for workshopId: {}", workshopId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(attendanceList);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Attendance>> getUserAttendance(@PathVariable String username) {
        List<Attendance> attendanceList = attendanceService.getUserAttendance(username);
        if (attendanceList.isEmpty()) {
            logger.warn("No attendance records found for username: {}", username);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(attendanceList);
    }

    @GetMapping("/workshop/{workshopId}/participants")
    public ResponseEntity<List<Registration>> getParticipantsByWorkshop(@PathVariable Long workshopId) {
        List<Registration> participants = registrationService.getRegisteredStudentsByWorkshop(workshopId);
        if (participants.isEmpty()) {
            logger.warn("No participants found for workshopId: {}", workshopId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(participants);
    }
}
