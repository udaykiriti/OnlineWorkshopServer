package com.studentworkshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentworkshop.models.Faculty;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
