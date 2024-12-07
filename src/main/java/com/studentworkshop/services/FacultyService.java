package com.studentworkshop.services;

import com.studentworkshop.models.Faculty;
import com.studentworkshop.repositories.FacultyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    @Autowired
    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public List<Faculty> getAllFaculty() {
        return facultyRepository.findAll();
    }

    public Optional<Faculty> getFacultyById(Long id) {
        return facultyRepository.findById(id);
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Long id, Faculty facultyDetails) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Faculty not found"));
        
        faculty.setUsername(facultyDetails.getUsername());
        faculty.setEmail(facultyDetails.getEmail());
        faculty.setRole(facultyDetails.getRole());
        
        if (facultyDetails.getPassword() != null && !facultyDetails.getPassword().isEmpty()) {
            faculty.setPassword(facultyDetails.getPassword());
        }

        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        if (!facultyRepository.existsById(id)) {
            throw new RuntimeException("Faculty not found for deletion");
        }
        facultyRepository.deleteById(id);
    }
}
