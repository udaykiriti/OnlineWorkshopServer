package com.studentworkshop.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.studentworkshop.models.Registration;
import java.util.List;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUsername(String username);
    List<Registration> findByWorkshopIdAndUsername(Long workshopId, String username);
    List<Registration> findByWorkshopId(Long workshopId); 
}
