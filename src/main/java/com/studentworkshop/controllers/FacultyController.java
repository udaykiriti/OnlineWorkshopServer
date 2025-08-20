package com.studentworkshop.controllers;

import com.studentworkshop.models.Faculty;
import com.studentworkshop.services.FacultyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * FacultyController handles CRUD operations for Faculty entities.
 * 
 * Base URL: /api/faculty
 * 
 * Endpoints:
 * - GET /api/faculty        → Get all faculty
 * - GET /api/faculty/{id}   → Get faculty by ID
 * - POST /api/faculty       → Create a new faculty
 * - PUT /api/faculty/{id}   → Update existing faculty
 * - DELETE /api/faculty/{id} → Delete faculty by ID
 */
@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    // Service layer that contains business logic for Faculty operations
    private final FacultyService facultyService;

    /**
     * Constructor-based dependency injection for FacultyService.
     */
    @Autowired
    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    /**
     * Get all faculty records.
     *
     * @return List of Faculty objects.
     */
    @GetMapping
    public List<Faculty> getAllFaculty() {
        return facultyService.getAllFaculty();
    }

    /**
     * Get a faculty record by its ID.
     *
     * @param id Faculty ID from the request path.
     * @return ResponseEntity containing Faculty object if found, otherwise 404.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        return facultyService.getFacultyById(id)
                .map(ResponseEntity::ok) // Return 200 OK with faculty
                .orElse(ResponseEntity.notFound().build()); // Return 404 if not found
    }

    /**
     * Create a new faculty record.
     *
     * @param faculty Faculty object from the request body.
     * @return Created Faculty object.
     */
    @PostMapping
    public Faculty createFaculty(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    /**
     * Update an existing faculty record by ID.
     *
     * @param id Faculty ID from the request path.
     * @param facultyDetails Updated Faculty object data from the request body.
     * @return ResponseEntity with updated Faculty object.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(
            @PathVariable Long id,
            @RequestBody Faculty facultyDetails) {
        Faculty updatedFaculty = facultyService.updateFaculty(id, facultyDetails);
        return ResponseEntity.ok(updatedFaculty);
    }

    /**
     * Delete a faculty record by ID.
     *
     * @param id Faculty ID from the request path.
     * @return ResponseEntity with no content if deletion is successful.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.noContent().build();
    }
}
