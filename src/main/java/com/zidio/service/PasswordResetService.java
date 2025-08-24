package com.zidio.service;

import com.zidio.Entities.User;
import com.zidio.payload.EMailRequest;
import com.zidio.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class PasswordResetService {

    // Stores email -> OTP details (in-memory)
    private final Map<String, OtpDetails> otpStore = new HashMap<>();

    @Autowired private UserRepository userRepository;
    @Autowired private EMailService emailService;
    @Autowired private PasswordEncoder passwordEncoder;

    // === Step 1: Generate OTP and send to email ===
    public void generateOtpAndSend(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = String.valueOf(100000 + new Random().nextInt(900000)); // 6-digit OTP
        otpStore.put(email, new OtpDetails(otp, LocalDateTime.now()));

        EMailRequest mail = new EMailRequest();
        mail.setTo(email);
        mail.setSubject("Password Reset OTP");
        mail.setMessage("Your OTP to reset your password is: " + otp + "\n\n" +
                        "It is valid for 10 minutes only.");
        emailService.sendEmail(mail);
    }

    // === Step 2: Verify OTP and update password ===
    public void verifyOtpAndReset(String email, String otp, String newPassword) {
        OtpDetails details = otpStore.get(email);
        if (details == null) {
            throw new RuntimeException("No OTP found for this email.");
        }

        if (!details.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        if (details.getGeneratedTime().plusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otpStore.remove(email); // cleanup used OTP
    }

    // === Inner class to hold OTP and timestamp ===
    private static class OtpDetails {
        private final String otp;
        private final LocalDateTime generatedTime;

        public OtpDetails(String otp, LocalDateTime generatedTime) {
            this.otp = otp;
            this.generatedTime = generatedTime;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getGeneratedTime() {
            return generatedTime;
        }
    }
}



