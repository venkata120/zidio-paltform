package com.zidio.serviceImpl;

import com.zidio.Entities.JobApplication;
import com.zidio.Entities.JobPortal;
import com.zidio.Entities.StudentProfile;
import com.zidio.Entities.User;

import com.zidio.payload.JobApplicationDto;
import com.zidio.repository.JobApplicationRepository;
import com.zidio.repository.JobPortalRepository;
import com.zidio.repository.StudentProfileRepository;
import com.zidio.repository.UserRepository;
import com.zidio.service.EMailService;
import com.zidio.service.JobApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    @Autowired
    private JobApplicationRepository jobAppRepo;

    @Autowired
    private StudentProfileRepository studentRepo;

    @Autowired
    private JobPortalRepository jobRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private EMailService emailService;

    @Override
    public void applyToJob(Long studentUserId, Long jobId) {
        User user = userRepo.findById(studentUserId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudentProfile student = studentRepo.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        JobPortal job = jobRepo.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        Optional<JobApplication> existing = jobAppRepo.findByStudentAndJob(student, job);

        if (existing.isPresent()) {
            LocalDate lastApplied = existing.get().getAppliedDate();
            if (lastApplied.plusMonths(3).isAfter(LocalDate.now())) {
                throw new RuntimeException("You can reapply only after 3 months.");
            }
            existing.get().setAppliedDate(LocalDate.now());
            jobAppRepo.save(existing.get());
            return;
        }

        //  Send acknowledgment email
        emailService.sendSimpleMessage(
                user.getEmail(),
                "✅ Application Received",
                "Dear " + user.getName() + ",\n\nWe have received your application for the job: " + job.getDesignation() + ".\n\nOur team will review it and inform you about the next steps.\n\nThank you,\nZidio Job Portal"
        );

        //  Skill match logic
        List<String> studentSkills = Arrays.stream(student.getSkills().split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        String jobTitle = job.getDesignation().toLowerCase();
        boolean match = studentSkills.stream().anyMatch(jobTitle::contains);

        JobApplication newApp = new JobApplication();
        newApp.setStudent(student);
        newApp.setJob(job);
        newApp.setAppliedDate(LocalDate.now());

        if (match) {
            newApp.setStatus("SELECTED");

            //  Selection email
            emailService.sendSimpleMessage(
                    user.getEmail(),
                    " Congratulations! You're Selected",
                    "Dear " + user.getName() + ",\n\nYou have been selected for the job: " + job.getDesignation() + ".\n\nPlease await further instructions from the recruiter.\n\nBest wishes,\nZidio Job Portal"
            );
        } else {
            newApp.setStatus("REJECTED");

            //  Rejection email
            emailService.sendSimpleMessage(
                    user.getEmail(),
                    " Application Update: Not Selected",
                    "Dear " + user.getName() + ",\n\nUnfortunately, your skills did not match the requirements for the job: " + job.getDesignation() + ".\n\nKeep applying, and don't give up!\n\n- Zidio Job Portal"
            );
        }

        jobAppRepo.save(newApp);
    }

    @Override
    public List<JobApplicationDto> getApplicationsForStudent(Long studentUserId) {
        StudentProfile student = studentRepo.findByUserUserId(studentUserId)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        List<JobApplication> applications = jobAppRepo.findAll().stream()
                .filter(app -> app.getStudent().getStudentId().equals(student.getStudentId()))
                .collect(Collectors.toList());

        return applications.stream().map(app -> {
            JobApplicationDto dto = new JobApplicationDto();
            dto.setJobId(app.getJob().getJobId());
            dto.setJobTitle(app.getJob().getTitle());
            dto.setCompanyName(app.getJob().getRecruiter().getCompanyName());
            dto.setAppliedDate(app.getAppliedDate());
            dto.setStatus(app.getStatus());
            dto.setStudentId(app.getStudent().getStudentId());
            return dto;
        }).collect(Collectors.toList());
    }
}
