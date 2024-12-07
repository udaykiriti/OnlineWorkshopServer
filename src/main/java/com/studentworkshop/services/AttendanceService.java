package com.studentworkshop.services;

import com.studentworkshop.models.Attendance;
import com.studentworkshop.models.Registration;
import com.studentworkshop.models.Workshop;
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

@Service
public class AttendanceService {

    private static final Logger logger = LoggerFactory.getLogger(AttendanceService.class);

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private WorkshopRepository workshopRepository;

    public Attendance markAttendance(Long workshopId, String username, int isPresent) { 
        Registration registration = registrationRepository
            .findByWorkshopIdAndUsername(workshopId, username)
            .stream()
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Registration not found for user: " + username + " in workshop: " + workshopId));

        String email = registration.getEmail();
        LocalDate date = LocalDate.now(); 
        LocalTime time = LocalTime.now(); 

        Workshop workshop = workshopRepository.findById(workshopId)
            .orElseThrow(() -> new RuntimeException("Workshop not found for ID: " + workshopId));

        String workshopName = workshop.getName();

        Attendance attendance = new Attendance(workshopId, workshopName, username, email, date, time, isPresent);

        logger.info("Marking attendance with isPresent={} for user: {}, workshopId: {}, workshopName: {}", isPresent, username, workshopId, workshopName);

        Attendance savedAttendance = attendanceRepository.save(attendance);

        logger.info("Attendance marked successfully: {}", savedAttendance);
        
        return savedAttendance;
    }

    public List<Attendance> getAttendanceByWorkshop(Long workshopId) {
        return attendanceRepository.findByWorkshopId(workshopId);
    }

    public List<Attendance> getUserAttendance(String username) {
        return attendanceRepository.findByUsername(username);
    }
}
