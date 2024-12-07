package com.studentworkshop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.studentworkshop.models.Workshop;

@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
}
