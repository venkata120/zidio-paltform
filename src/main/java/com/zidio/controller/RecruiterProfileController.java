package com.zidio.controller;

import com.zidio.Entities.User;
import com.zidio.payload.RecruiterProfileDto;
import com.zidio.repository.UserRepository;
import com.zidio.service.RecruiterProfileService;

import jakarta.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiter")

public class RecruiterProfileController {

	@Autowired
    private RecruiterProfileService recruiterProfileService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/profile")
    public ResponseEntity<RecruiterProfileDto> createOrUpdateProfile(
            @Valid @RequestBody RecruiterProfileDto dto,
            Authentication authentication) {

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        RecruiterProfileDto saved = recruiterProfileService.saveRecruiterProfile(user.getUserId(), dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/profile")
    public ResponseEntity<RecruiterProfileDto> getProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        RecruiterProfileDto profile = recruiterProfileService.getProfileByUserId(user.getUserId());
        return ResponseEntity.ok(profile);
    }

    @DeleteMapping("/profile")
    public ResponseEntity<String> deleteProfile(Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        recruiterProfileService.deleteRecruiterAndUser(user.getUserId());
        return ResponseEntity.ok("Recruiter profile and user deleted successfully.");
    }
}


