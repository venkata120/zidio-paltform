package com.zidio.service;

import com.zidio.payload.RecruiterProfileDto;
import java.util.List;

public interface RecruiterProfileService {

    
    RecruiterProfileDto saveRecruiterProfile(Long userId, RecruiterProfileDto dto);

    
    RecruiterProfileDto getRecruiterProfileById(Long recruiterId);

    
    RecruiterProfileDto getProfileByUserId(Long userId);
    
    RecruiterProfileDto getProfileByEmail(String email);

   
    List<RecruiterProfileDto> getAllRecruiterProfiles();

    
    RecruiterProfileDto updateRecruiterProfile(Long recruiterId, RecruiterProfileDto dto);

    
    void deleteRecruiterAndUserByRecruiterId(Long recruiterId);

    
    void deleteRecruiterAndUser(Long userId);
    
 
}

