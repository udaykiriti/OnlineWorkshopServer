package com.studentworkshop.config;

import com.studentworkshop.models.Faculty;
import com.studentworkshop.models.User;
import com.studentworkshop.repositories.FacultyRepository;
import com.studentworkshop.repositories.UserRepository;
import com.studentworkshop.services.PasswordService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedData(UserRepository userRepository, 
                                     FacultyRepository facultyRepository, 
                                     PasswordService passwordService) {
        return args -> {
            // Seed Admin User
            if (userRepository.findByUsername("admin") == null) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setEmail("admin@workshop.com");
                admin.setPassword(passwordService.hashPassword("admin123"));
                admin.setRole("admin");
                admin.setPhoneNumber("1234567890");
                admin.setGender("Other");
                admin.setDob("1990-01-01");
                userRepository.save(admin);
                System.out.println("Admin user seeded.");
            }

            // Seed Faculty User in Users table
            if (userRepository.findByUsername("faculty") == null) {
                User facultyUser = new User();
                facultyUser.setUsername("faculty");
                facultyUser.setEmail("faculty@workshop.com");
                facultyUser.setPassword(passwordService.hashPassword("faculty123"));
                facultyUser.setRole("faculty");
                facultyUser.setPhoneNumber("0987654321");
                facultyUser.setGender("Male");
                facultyUser.setDob("1985-05-15");
                userRepository.save(facultyUser);
                System.out.println("Faculty user seeded in users table.");
            }

            // Seed Faculty User in Faculty table (if needed by specific faculty features)
            if (facultyRepository.findAll().stream().noneMatch(f -> f.getUsername().equals("faculty"))) {
                Faculty faculty = new Faculty();
                faculty.setUsername("faculty");
                faculty.setEmail("faculty@workshop.com");
                faculty.setPassword(passwordService.hashPassword("faculty123"));
                faculty.setRole("faculty");
                facultyRepository.save(faculty);
                System.out.println("Faculty user seeded in faculty table.");
            }
        };
    }
}
