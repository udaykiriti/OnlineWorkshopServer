package com.studentworkshop.services;

import com.studentworkshop.models.Workshop;
import com.studentworkshop.repositories.WorkshopRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkshopService {

    @Autowired
    private WorkshopRepository workshopRepository;

    public Workshop saveWorkshop(Workshop workshop) {
        return workshopRepository.save(workshop);
    }

    public List<Workshop> getAllWorkshops() {
        return workshopRepository.findAll();
    }

    public Workshop getWorkshopById(Long id) {
        return workshopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Workshop not found with id " + id));
    }

    public Workshop updateWorkshop(Long id, Workshop workshopDetails) {
        Workshop workshop = getWorkshopById(id);
        workshop.setName(workshopDetails.getName());
        workshop.setDate(workshopDetails.getDate());
        workshop.setTime(workshopDetails.getTime());
        workshop.setMeetingLink(workshopDetails.getMeetingLink());
        workshop.setDescription(workshopDetails.getDescription());
        workshop.setInstructor(workshopDetails.getInstructor());
        workshop.setMaterial(workshopDetails.getMaterial());
        return workshopRepository.save(workshop);
    }

    public void deleteWorkshop(Long id) {
        Workshop workshop = getWorkshopById(id);
        workshopRepository.delete(workshop);
    }
}
