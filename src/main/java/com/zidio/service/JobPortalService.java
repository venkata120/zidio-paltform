package com.zidio.service;

import com.zidio.payload.JobPortalDto;
import java.util.List;

public interface JobPortalService {
    JobPortalDto postJob(JobPortalDto dto, String userEmail);
    List<JobPortalDto> getAllJobs();
    JobPortalDto getJobById(Long jobId);
    JobPortalDto updateJobByUser(Long jobId, JobPortalDto dto, String userEmail);
    void deleteJobByUser(Long jobId, String userEmail);
    List<JobPortalDto> getJobsForStudent(String userEmail);
    List<JobPortalDto> getJobsByRecruiter(String email);

   
}



































/*
 * public interface JobPortalService { JobPortalDto postJob(Long recruiterId,
 * JobPortalDto dto); List<JobPortalDto> getAllJobs(); List<JobPortalDto>
 * getJobsByRecruiter(Long recruiterId); JobPortalDto updateJob(Long jobId, Long
 * recruiterId, JobPortalDto dto); void deleteJob(Long jobId, Long recruiterId);
 * JobPortalDto getJobById(Long jobId); Page<JobPortalDto> searchJobs(String
 * title, String location, int page, int size);
 * 
 * 
 * }
 */


/*
 * package com.zidio.service;
 * 
 * import com.zidio.payload.JobPortalDto; import java.util.List;
 * 
 * public interface JobPortalService { JobPortalDto postJob(Long recruiterId,
 * JobPortalDto dto); List<JobPortalDto> getAllJobs(); List<JobPortalDto>
 * getJobsByRecruiter(Long recruiterId); JobPortalDto updateJob(Long jobId, Long
 * recruiterId, JobPortalDto dto); void deleteJob(Long jobId, Long recruiterId);
 * JobPortalDto getJobById(Long jobId); }
 */


/*
 * package com.zidio.service;
 * 
 * import com.zidio.payload.JobPortalDto; import java.util.List;
 * 
 * public interface JobPortalService { JobPortalDto postJob(Long recruiterId,
 * JobPortalDto dto); List<JobPortalDto> getAllJobs(); }
 */