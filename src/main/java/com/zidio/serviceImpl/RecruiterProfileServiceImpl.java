package com.zidio.serviceImpl;

import com.zidio.Entities.RecruiterProfile;
import com.zidio.Entities.User;
import com.zidio.exception.UserNotFound;
import com.zidio.payload.RecruiterProfileDto;
import com.zidio.repository.RecruiterProfileRepository;
import com.zidio.repository.UserRepository;
import com.zidio.service.RecruiterProfileService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecruiterProfileServiceImpl implements RecruiterProfileService {

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public RecruiterProfileDto saveRecruiterProfile(Long userId, RecruiterProfileDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound("User not found with id: " + userId));

        RecruiterProfile profile = recruiterProfileRepository.findByUserUserId(userId)
                .orElse(new RecruiterProfile());

        profile.setUser(user);
        profile.setRecruiterName(dto.getRecruiterName() != null ? dto.getRecruiterName() : "");
        profile.setCompanyName(dto.getCompanyName() != null ? dto.getCompanyName() : "");
        profile.setDesignation(dto.getDesignation() != null ? dto.getDesignation() : "");

        RecruiterProfile saved = recruiterProfileRepository.save(profile);
        return modelMapper.map(saved, RecruiterProfileDto.class);
    }

    @Override
    public RecruiterProfileDto getRecruiterProfileById(Long recruiterId) {
        RecruiterProfile recruiter = recruiterProfileRepository.findById(recruiterId)
                .orElseThrow(() -> new RuntimeException("Recruiter not found with id: " + recruiterId));
        return modelMapper.map(recruiter, RecruiterProfileDto.class);
    }

    @Override
    public RecruiterProfileDto getProfileByUserId(Long userId) {
        RecruiterProfile recruiter = recruiterProfileRepository.findByUserUserId(userId)
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found for userId: " + userId));
        return modelMapper.map(recruiter, RecruiterProfileDto.class);
    }

    
    @Override
    public List<RecruiterProfileDto> getAllRecruiterProfiles() {
        return recruiterProfileRepository.findAll().stream()
                .map(profile -> {
                    RecruiterProfileDto dto = modelMapper.map(profile, RecruiterProfileDto.class);
                    dto.setEmail(profile.getUser().getEmail()); 
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public RecruiterProfileDto updateRecruiterProfile(Long recruiterId, RecruiterProfileDto dto) {
        RecruiterProfile recruiter = recruiterProfileRepository.findById(recruiterId)
                .orElseThrow(() -> new RuntimeException("Recruiter not found with id: " + recruiterId));

        recruiter.setRecruiterName(dto.getRecruiterName());
        recruiter.setCompanyName(dto.getCompanyName());
        recruiter.setDesignation(dto.getDesignation());

        RecruiterProfile updated = recruiterProfileRepository.save(recruiter);
        return modelMapper.map(updated, RecruiterProfileDto.class);
    }

    @Override
    public void deleteRecruiterAndUserByRecruiterId(Long recruiterId) {
        RecruiterProfile profile = recruiterProfileRepository.findById(recruiterId)
                .orElseThrow(() -> new RuntimeException("Recruiter not found with id: " + recruiterId));
        Long userId = profile.getUser().getUserId();

        recruiterProfileRepository.delete(profile);
        userRepository.deleteById(userId);
    }

    @Override
    public void deleteRecruiterAndUser(Long userId) {
        RecruiterProfile profile = recruiterProfileRepository.findByUserUserId(userId)
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found for userId: " + userId));
        recruiterProfileRepository.delete(profile);
        userRepository.deleteById(userId);
    }
    
    @Override
    public RecruiterProfileDto getProfileByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFound("User not found with email: " + email));

        RecruiterProfile profile = recruiterProfileRepository.findByUserUserId(user.getUserId())
            .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));

        return modelMapper.map(profile, RecruiterProfileDto.class);
    }
   


}
