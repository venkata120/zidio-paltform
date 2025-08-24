package com.zidio.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.zidio.Entities.StudentProfile;
import com.zidio.Entities.User;
import com.zidio.Entities.User.Role;
import com.zidio.exception.UserNotFound;
import com.zidio.payload.UserDto;
import com.zidio.repository.StudentProfileRepository;
import com.zidio.repository.UserRepository;
import com.zidio.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        User user = userDtoToEntity(userDto);
        User savedUser = userRepository.save(user);
        return entityToUserDto(savedUser);
    }

    private User userDtoToEntity(UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        //  Convert single role string to enum safely
        String roleStr = dto.getRole().toUpperCase();
        switch (roleStr) {
            case "STUDENT" -> user.setRole(Role.STUDENT);
            case "RECRUITER" -> user.setRole(Role.RECRUITER);
            case "ADMIN" -> user.setRole(Role.ADMIN);
            default -> throw new IllegalArgumentException("Invalid role: " + roleStr);
        }

        return user;
    }

    private UserDto entityToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setUserId(user.getUserId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());

        //  Updated to single string
        dto.setRole(user.getRole().name());

        return dto;
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFound("User not found with ID: " + userId));

        studentProfileRepository.findByUser(user)
            .ifPresent(studentProfileRepository::delete);

        userRepository.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void updateUserRole(Long userId, Role role) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFound("User not found"));
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void verifyDigiLockerForUser(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        StudentProfile student = studentProfileRepository.findByUser(user)
            .orElseThrow(() -> new RuntimeException("Student profile not found"));

        student.setDigilockerVerified(true);
        studentProfileRepository.save(student);
    }
    
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }
}










