package com.studentworkshop.services;

// Importing required model classes
import com.studentworkshop.models.Attendance;
import com.studentworkshop.models.Registration;
import com.studentworkshop.models.Workshop;

// Importing repository interfaces for database operations
import com.studentworkshop.repositories.AttendanceRepository;
import com.studentworkshop.repositories.RegistrationRepository;
import com.studentworkshop.repositories.WorkshopRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service // Marks this class as a Spring service component
public class AttendanceService {

    // Logger for logging messages
    private static final Logger logger = LoggerFactory.getLogger(AttendanceService.class);

    // Injecting AttendanceRepository dependency
    @Autowired
    private AttendanceRepository attendanceRepository;

    // Injecting RegistrationRepository dependency
    @Autowired
    private RegistrationRepository registrationRepository;

    // Injecting WorkshopRepository dependency
    @Autowired
    private WorkshopRepository workshopRepository;

    // Method to mark attendance for a user in a workshop
    public Attendance markAttendance(Long workshopId, String username, int isPresent) { 
        // Find the registration record for the user and workshop
        Registration registration = registrationRepository
            .findByWorkshopIdAndUsername(workshopId, username)
            .stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Registration not found for user: " + username + " in workshop: " + workshopId));

        // Get email from registration
        String email = registration.getEmail();

        // Get current date and time
        LocalDate date = LocalDate.now(); 
        LocalTime time = LocalTime.now(); 

        // Find the workshop by ID
        Workshop workshop = workshopRepository.findById(workshopId)
            .orElseThrow(() -> new RuntimeException("Workshop not found for ID: " + workshopId));

        // Get workshop name
        String workshopName = workshop.getName();

        // Create Attendance object with collected data
        Attendance attendance = new Attendance(workshopId, workshopName, username, email, date, time, isPresent);

        // Log attendance marking action
        logger.info("Marking attendance with isPresent={} for user: {}, workshopId: {}, workshopName: {}", isPresent, username, workshopId, workshopName);

        // Save the attendance record to the database
        Attendance savedAttendance = attendanceRepository.save(attendance);

        // Log success message
        logger.info("Attendance marked successfully: {}", savedAttendance);
        
        // Return saved attendance record
        return savedAttendance;
    }

    // Method to get all attendance records for a given workshop
    public List<Attendance> getAttendanceByWorkshop(Long workshopId) {
        return attendanceRepository.findByWorkshopId(workshopId);
    }

    // Method to get all attendance records for a specific user
    public List<Attendance> getUserAttendance(String username) {
        return attendanceRepository.findByUsername(username);
    }
}
