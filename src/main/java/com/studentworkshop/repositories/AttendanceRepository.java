package com.studentworkshop.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.studentworkshop.models.Attendance;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    List<Attendance> findByWorkshopId(Long workshopId);
    List<Attendance> findByUsername(String username);
}
