package com.zidio.controller;

import com.zidio.Entities.EmailLog;

import com.zidio.payload.EMailRequest;
import com.zidio.repository.EmailLogRepository;

import com.zidio.service.EMailService;


import java.util.List;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class EmailController {

	
	
	@Autowired
	private EmailLogRepository emailLogRepository;
	
    @Autowired
    private EMailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody EMailRequest request) {
        return ResponseEntity.ok(emailService.sendEmail(request));
    }
    
    @GetMapping
    public ResponseEntity<List<EmailLog>> getNotifications(Authentication auth) {
        String email = auth.getName();
        List<EmailLog> logs = emailLogRepository
            .findTop10ByRecipientOrderBySentAtDesc(email)
            .stream()
            .filter(log -> log.getSubject() != null && log.getMessage() != null && log.getSentAt() != null)
            .collect(Collectors.toList());
        return ResponseEntity.ok(logs);
    }


    }




