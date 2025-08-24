package com.zidio.controller;

import com.zidio.Entities.StudentProfile;
import com.zidio.Entities.User;
import com.zidio.repository.UserRepository;
import com.zidio.service.StudentProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/digilocker")
public class DigiLockerController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentProfileService studentProfileService;

    // Dummy verification endpoint
    @GetMapping("/start")
    public ResponseEntity<String> startDigiLockerFlow(@RequestParam String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        StudentProfile profile = studentProfileService.getEntityByUserId(user.getUserId());

        // Mark as verified if fresher
        if ("FRESHER".equalsIgnoreCase(profile.getExperienceLevel())) {
            profile.setDigilockerVerified(true);
            studentProfileService.save(profile); 
            return ResponseEntity.ok("✅ DigiLocker Verified successfully for Fresher: " + email);
        } else {
            return ResponseEntity.ok("✅ Already considered verified for non-Fresher: " + email);
        }
    }
}


