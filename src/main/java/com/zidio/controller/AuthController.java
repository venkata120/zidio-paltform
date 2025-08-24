package com.zidio.controller;

import com.zidio.Entities.User;
import com.zidio.Entities.User.Role;
import com.zidio.Security.JwtTokenProvider;
import com.zidio.payload.LoginDto;
import com.zidio.payload.UserDto;
import com.zidio.service.PasswordResetService;
import com.zidio.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordResetService passwordResetService;

    // === Register ===
    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.createUser(userDto), HttpStatus.CREATED);
    }

    // === Login ===
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginDto loginDto, HttpServletRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getemail(), loginDto.getpassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        User user = userService.findByEmail(loginDto.getemail());

        request.getSession().setAttribute("role", user.getRole().toString());
        request.getSession().setAttribute("email", user.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole().toString());
        response.put("email", user.getEmail());

        return ResponseEntity.ok(response);
    }

    // === Admin Deletes User ===
    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully.");
    }

    // === Admin Views All Users ===
    @GetMapping("/users/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // === Forgot Password ===
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        passwordResetService.generateOtpAndSend(request.get("email"));
        return ResponseEntity.ok("OTP sent to email.");
    }

    // === Reset Password ===
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> request) {
        passwordResetService.verifyOtpAndReset(
                request.get("email"),
                request.get("otp"),
                request.get("newPassword")
        );
        return ResponseEntity.ok("Password updated successfully.");
    }

    // === Admin Updates User Role ===
    @PatchMapping("/users/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateUserRole(@PathVariable Long userId, @RequestBody Map<String, String> request) {
        String roleName = request.get("role");
        try {
            Role newRole = Role.valueOf(roleName.toUpperCase());
            userService.updateUserRole(userId, newRole);
            return ResponseEntity.ok("User role updated to: " + newRole);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid role: " + roleName);
        }
    }

    // === DigiLocker Callback (Mock Only) ===
    @GetMapping("/digilocker/callback")
    public ResponseEntity<String> mockDigiLockerCallback(
            @RequestParam String code,
            @RequestHeader("Authorization") String authHeader) {

        String token = authHeader.replace("Bearer ", "");
        String email = jwtTokenProvider.getEmailFromToken(token);
        userService.verifyDigiLockerForUser(email); // This should set digilockerVerified = true in DB

        return ResponseEntity.ok("DigiLocker verification successful for: " + email);
    }
    
    

    // === Health Check ===
    @GetMapping("/ping")
    public String ping() {
        return "Login endpoint reachable";
    }
}




