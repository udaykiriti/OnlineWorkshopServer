package com.studentworkshop.services;

import com.studentworkshop.models.Registration;
import com.studentworkshop.models.User;
import com.studentworkshop.models.Workshop;
import com.studentworkshop.repositories.RegistrationRepository;
import com.studentworkshop.repositories.UserRepository;
import com.studentworkshop.repositories.WorkshopRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegistrationService {

	@Autowired
	private RegistrationRepository registrationRepository;

	@Autowired
	private WorkshopRepository workshopRepository;

	@Autowired
	private UserRepository userRepository; 

	private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

	public Registration registerWorkshop(Registration registration) {
		User user = userRepository.findByUsername(registration.getUsername());
		if (user == null) {
			logger.error("User not found for username: {}", registration.getUsername());
			throw new IllegalArgumentException("User not found.");
		}

		registration.setEmail(user.getEmail());

		Workshop workshop = workshopRepository.findById(registration.getWorkshopId()).orElse(null);
		if (workshop == null) {
			logger.error("Workshop not found for workshopId: {}", registration.getWorkshopId());
			throw new IllegalArgumentException("Workshop not found.");
		}

		registration.setWorkshopName(workshop.getName()); 

		return registrationRepository.save(registration);
	}

	public boolean unregisterWorkshop(Long workshopId, String username) {
		List<Registration> registrations = registrationRepository.findByWorkshopIdAndUsername(workshopId, username);
		if (registrations != null && !registrations.isEmpty()) {
			registrationRepository.deleteAll(registrations);
			return true;
		}
		return false;
	}

	public List<Workshop> getRegisteredWorkshops(String username) {
		List<Registration> registrations = registrationRepository.findByUsername(username);

		if (registrations.isEmpty()) {
			logger.warn("No registrations found for username: {}", username);
			return List.of(); 
		}

		return registrations.stream().map(registration -> {
			Workshop workshop = workshopRepository.findById(registration.getWorkshopId()).orElse(null);
			if (workshop == null) {
				logger.warn("No workshop found for registration with ID: {}", registration.getWorkshopId());
			}
			return workshop;
		}).filter(workshop -> workshop != null).toList();
	}

	public List<Registration> getRegisteredStudentsByWorkshop(Long workshopId) {
		return registrationRepository.findByWorkshopId(workshopId);
	}

}
