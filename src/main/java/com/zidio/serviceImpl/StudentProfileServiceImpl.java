package com.zidio.serviceImpl;

import com.zidio.Entities.StudentProfile;
import com.zidio.Entities.User;
import com.zidio.exception.StudentProfileNotFound;
import com.zidio.exception.UserNotFound;
import com.zidio.payload.StudentProfileDto;
import com.zidio.repository.StudentProfileRepository;
import com.zidio.repository.UserRepository;
import com.zidio.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentProfileServiceImpl implements StudentProfileService {

    @Autowired 
    private StudentProfileRepository studentProfileRepository;
    @Autowired 
    private UserRepository userRepository;
    @Autowired 
    private ModelMapper modelMapper;

    @Override
    public StudentProfileDto createOrUpdateProfile(StudentProfileDto dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("User not found with id: " + userId));

        Optional<StudentProfile> existingProfile = studentProfileRepository.findByUserUserId(userId);
        StudentProfile profile = existingProfile.orElse(new StudentProfile());

        profile.setUser(user);
        profile.setResumeUrl(dto.getResumeUrl() != null ? dto.getResumeUrl() : "");
        profile.setEducation(dto.getEducation() != null ? dto.getEducation() : "");
        profile.setSkills(dto.getSkills() != null ? dto.getSkills() : "");
        profile.setExperienceLevel(dto.getExperienceLevel() != null ? dto.getExperienceLevel() : "FRESHER");

        if ("FRESHER".equalsIgnoreCase(profile.getExperienceLevel())) {
            profile.setDigilockerVerified(dto.isDigilockerVerified());
        } else {
            profile.setDigilockerVerified(true);
        }

        StudentProfile saved = studentProfileRepository.save(profile);
        StudentProfileDto responseDto = modelMapper.map(saved, StudentProfileDto.class);
        responseDto.setstudentId(saved.getStudentId());
        responseDto.setDigilockerVerified(saved.isDigilockerVerified());

        return responseDto;
    }

    @Override
    public StudentProfileDto getProfileByUserId(Long userId) {
        StudentProfile profile = studentProfileRepository.findByUserUserId(userId)
                .orElseThrow(() -> new StudentProfileNotFound("Profile not found for user id: " + userId));
        return modelMapper.map(profile, StudentProfileDto.class);
    }

    @Override
    public StudentProfile getEntityByUserId(Long userId) {
        return studentProfileRepository.findByUserUserId(userId)
                .orElseThrow(() -> new StudentProfileNotFound("Profile not found for user id: " + userId));
    }

    @Override
    public void updateResumeUrl(String userEmail, String resumeUrl) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFound("User not found with email: " + userEmail));

        StudentProfile profile = studentProfileRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new StudentProfileNotFound("Profile not found for user: " + userEmail));

        profile.setResumeUrl(resumeUrl);
        studentProfileRepository.save(profile);
    }
    
    @Override
    public StudentProfileDto getProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found with email: " + email));

        StudentProfile profile = studentProfileRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new StudentProfileNotFound("Profile not found for user: " + email));

        return modelMapper.map(profile, StudentProfileDto.class);
    }
    
    @Override
    public List<StudentProfileDto> getAllProfiles() {
        List<StudentProfile> profiles = studentProfileRepository.findAll();

        return profiles.stream()
                .map(profile -> {
                    StudentProfileDto dto = modelMapper.map(profile, StudentProfileDto.class);
                    dto.setEmail(profile.getUser().getEmail()); // Ensure email is set
                    dto.setDigilockerVerified(profile.isDigilockerVerified());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public void save(StudentProfile profile) {
        studentProfileRepository.save(profile);
    }


}

