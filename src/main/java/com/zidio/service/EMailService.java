package com.zidio.service;

import com.zidio.Entities.EmailLog;
import com.zidio.Entities.User;
import com.zidio.payload.EMailRequest;
import com.zidio.repository.EmailLogRepository;
import com.zidio.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EMailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailLogRepository emailLogRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Sends an email and logs the email to the database
     */
    public String sendEmail(EMailRequest request) {
        if (request.getTo() == null || request.getTo().isBlank()) {
            throw new IllegalArgumentException("Recipient email (to) must not be null or blank");
        }

        if (request.getSubject() == null || request.getSubject().isBlank()) {
            throw new IllegalArgumentException("Subject must not be null or blank");
        }

        if (request.getMessage() == null || request.getMessage().isBlank()) {
            throw new IllegalArgumentException("Message must not be null or blank");
        }

        System.out.println("📧 Sending email to: " + request.getTo());
        System.out.println("📧 Subject: " + request.getSubject());
        System.out.println("📧 Message: " + request.getMessage());

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(request.getTo());
        message.setSubject(request.getSubject());
        message.setText(request.getMessage());
        message.setFrom("venkatthummaluru258@gmail.com");  // replace if needed
        mailSender.send(message);

        // Log to database
        EmailLog log = new EmailLog();
        log.setRecipient(request.getTo());
        log.setSubject(request.getSubject());
        log.setMessage(request.getMessage());
        log.setStatus("SENT");
        log.setSentAt(LocalDateTime.now());

        Optional<User> userOpt = userRepository.findByEmail(request.getTo());
        userOpt.ifPresent(log::setUser);

        emailLogRepository.save(log);

        return "Email sent and logged successfully";
    }

    /**
     * Wrapper method for sending quick emails
     */
    public void sendSimpleMessage(String to, String subject, String message) {
        if (to == null || subject == null || message == null) {
            throw new IllegalArgumentException("to, subject, and message must not be null");
        }

        EMailRequest request = new EMailRequest();
        request.setTo(to);
        request.setSubject(subject);
        request.setMessage(message);

        sendEmail(request);
    }
}


