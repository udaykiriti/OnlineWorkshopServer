package com.studentworkshop.exceptions;

/**
 * Exception thrown when a requested resource is not found.
 * 
 * <p>This custom runtime exception can be used throughout the application
 * to indicate that a specific resource (such as a student, course, or file)
 * could not be found, typically in RESTful services to return 404 status codes.</p>
 * 
 * <p>Example usage:
 * <pre>
 *     if (student == null) {
 *         throw new ResourceNotFoundException("Student with ID " + id + " not found.");
 *     }
 * </pre>
 * </p>
 * 
 * @author 
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructs a new ResourceNotFoundException with the specified detail message.
     *
     * @param message the detail message, typically describing which resource was not found
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
