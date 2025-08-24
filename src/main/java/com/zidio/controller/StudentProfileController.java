package com.zidio.controller;

import com.zidio.Entities.StudentProfile;
import com.zidio.Entities.User;
import com.zidio.payload.StudentProfileDto;
import com.zidio.repository.UserRepository;
import com.zidio.service.JobApplicationService;
import com.zidio.service.StudentProfileService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentProfileController {

    @Autowired
    private StudentProfileService studentProfileService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JobApplicationService jobApplicationService;

    /**
     * Create or update student profile.
     */
    @PostMapping("/profile")
    public ResponseEntity<StudentProfileDto> createOrUpdateProfile(
            @Valid @RequestBody StudentProfileDto dto,
            Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with email: " + email));

        StudentProfileDto saved = studentProfileService.createOrUpdateProfile(dto, user.getUserId());
        return ResponseEntity.ok(saved);
    }

    /**
     * Get student profile by authenticated user.
     */
    @GetMapping("/profile")
    public ResponseEntity<StudentProfileDto> getProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with email: " + email));

        StudentProfileDto profile = studentProfileService.getProfileByUserId(user.getUserId());
        return ResponseEntity.ok(profile);
    }

    /**
     * Check DigiLocker verification status for current student.
     */
    @GetMapping("/verification-status")
    public ResponseEntity<Map<String, Object>> checkDigiLockerStatus(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with email: " + email));

        StudentProfile profile = studentProfileService.getEntityByUserId(user.getUserId());

        return ResponseEntity.ok(Map.of(
                "studentId", profile.getStudentId(),
                "experienceLevel", profile.getExperienceLevel(),
                "digilockerVerified", profile.isDigilockerVerified()
        ));
    }
    
    @GetMapping("/applications")
    public ResponseEntity<?> getStudentApplications(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with email: " + email));

        try {
            return ResponseEntity.ok(jobApplicationService.getApplicationsForStudent(user.getUserId()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to fetch job applications"));
        }
    }
}




