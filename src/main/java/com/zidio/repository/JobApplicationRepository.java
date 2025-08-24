package com.zidio.repository;

import com.zidio.Entities.JobApplication;
import com.zidio.Entities.JobPortal;
import com.zidio.Entities.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    Optional<JobApplication> findByStudentAndJob(StudentProfile student, JobPortal job);

}

