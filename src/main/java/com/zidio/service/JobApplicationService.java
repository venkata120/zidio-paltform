package com.zidio.service;

import java.util.List;

import com.zidio.payload.JobApplicationDto;

public interface JobApplicationService {

    void applyToJob(Long studentUserId, Long jobId);
    List<JobApplicationDto> getApplicationsForStudent(Long studentUserId);

    

}

