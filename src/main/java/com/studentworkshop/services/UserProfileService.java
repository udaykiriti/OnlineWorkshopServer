package com.studentworkshop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.studentworkshop.models.UserProfile;
import com.studentworkshop.repositories.UserProfileRepository;

import java.util.Optional;

@Service
public class UserProfileService {

    @Autowired
    private UserProfileRepository userProfileRepository;

    public Optional<UserProfile> getUserProfileByUsername(String username) {
        return Optional.ofNullable(userProfileRepository.findByUsername(username));
    }

    @Transactional
    public UserProfile updateUserProfile(String username, UserProfile updatedProfile) {
        UserProfile existingProfile = userProfileRepository.findByUsername(username);
        if (existingProfile != null) {
            existingProfile.setFirstName(updatedProfile.getFirstName());
            existingProfile.setLastName(updatedProfile.getLastName());
            existingProfile.setSchoolName(updatedProfile.getSchoolName());
            existingProfile.setCourse(updatedProfile.getCourse());
            existingProfile.setMarks(updatedProfile.getMarks());
            return userProfileRepository.save(existingProfile);
        }
        throw new RuntimeException("UserProfile not found with username: " + username);
    }
}
