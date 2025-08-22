package com.studentworkshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.studentworkshop.models.Attendance;

import java.util.List;

/**
 * Repository interface for accessing and managing {@link Attendance} entities.
 *
 * <p>This interface extends {@link JpaRepository}, providing CRUD operations and
 * additional query methods for Attendance records stored in the database.</p>
 *
 * <p>Custom query methods included:</p>
 * <ul>
 *     <li>{@link #findByWorkshopId(Long)} - Finds all attendance records for a given workshop.</li>
 *     <li>{@link #findByUsername(String)} - Finds all attendance records associated with a specific username.</li>
 * </ul>
 *
 * <p>Spring Data JPA will automatically generate the implementations for these methods
 * based on the method names and parameters.</p>
 * 
 * @author 
 */
@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    /**
     * Retrieves a list of attendance records associated with a specific workshop.
     *
     * @param workshopId the ID of the workshop
     * @return a list of {@link Attendance} records for the specified workshop
     */
    List<Attendance> findByWorkshopId(Long workshopId);

    /**
     * Retrieves a list of attendance records associated with a specific username.
     *
     * @param username the username of the attendee
     * @return a list of {@link Attendance} records for the specified user
     */
    List<Attendance> findByUsername(String username);
}
