package com.zidio.service;

import java.util.List;

import com.zidio.Entities.StudentProfile;
import com.zidio.payload.StudentProfileDto;

public interface StudentProfileService {
    StudentProfileDto createOrUpdateProfile(StudentProfileDto dto, Long userId);
    StudentProfileDto getProfileByUserId(Long userId);
    StudentProfile getEntityByUserId(Long userId);
    void updateResumeUrl(String userEmail, String resumeUrl);
    StudentProfileDto getProfileByEmail(String email);
    List<StudentProfileDto> getAllProfiles();
    void save(StudentProfile profile);


}




/*package com.zidio.service;

import com.zidio.payload.StudentProfileDto;

public interface StudentProfileService {
    StudentProfileDto createOrUpdateProfile(StudentProfileDto studentProfileDto, Long userId);
    StudentProfileDto getProfileByUserId(Long userId);
    void updateResumeUrl(String userEmail, String resumeUrl);
}*/


































/*
 * package com.zidio.service;
 * 
 * import com.zidio.payload.StudentProfileDto;
 * 
 * public interface StudentProfileService { StudentProfileDto
 * createOrUpdateProfile(StudentProfileDto studentProfileDto, Long userId);
 * StudentProfileDto getProfileByUserId(Long userId); }
 */




/*
 * package com.zidio.service;
 * 
 * import com.zidio.payload.StudentProfileDto; import java.util.List;
 * 
 * public interface StudentProfileService { StudentProfileDto
 * saveStudentProfile(long userId, StudentProfileDto dto);
 * List<StudentProfileDto> getAllStudentProfile(long userId); StudentProfileDto
 * getStudentProfile(long userId, long studentId); void
 * deleteStudentProfileDto(long userId, long studentId); }
 */