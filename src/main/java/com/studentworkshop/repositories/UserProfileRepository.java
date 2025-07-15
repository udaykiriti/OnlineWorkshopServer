package com.studentworkshop.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.studentworkshop.models.UserProfile;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    @Query("SELECT up FROM UserProfile up JOIN up.user u WHERE u.username = :username")
    UserProfile findByUsername(@Param("username") String username);
}
