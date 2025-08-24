package com.zidio.serviceImpl;

import com.zidio.Entities.*;
import com.zidio.exception.UserNotFound;
import com.zidio.payload.JobPortalDto;
import com.zidio.repository.*;
import com.zidio.service.EMailService;
import com.zidio.service.JobPortalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobPortalServiceImpl implements JobPortalService {

    @Autowired
    private JobPortalRepository jobPortalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecruiterProfileRepository recruiterProfileRepository;

    @Autowired
    private StudentProfileRepository studentProfileRepository;

    @Autowired
    private EMailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    // 1. Post a job
    @Override
    public JobPortalDto postJob(JobPortalDto dto, String recruiterEmail) {
        User user = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new UserNotFound("Recruiter not found"));

        RecruiterProfile recruiter = recruiterProfileRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));

        JobPortal job = modelMapper.map(dto, JobPortal.class);
        job.setRecruiter(recruiter);
        JobPortal saved = jobPortalRepository.save(job);

        // Email notification to students
        userRepository.findAll().stream()
                .filter(u -> u.getRole() == User.Role.STUDENT)
                .forEach(student -> emailService.sendSimpleMessage(
                        student.getEmail(),
                        "New Job Posted",
                        "New job posted: " + dto.getTitle() + "\nDesignation: " + dto.getDesignation()
                ));

        // Email confirmation to recruiter
        emailService.sendSimpleMessage(
                user.getEmail(),
                "Job Created Successfully",
                "Your job titled '" + dto.getTitle() + "' has been posted."
        );

        return convertToDto(saved);
    }

    // 2. Get all jobs
    @Override
    public List<JobPortalDto> getAllJobs() {
        return jobPortalRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 3. Get job by ID
    @Override
    public JobPortalDto getJobById(Long jobId) {
        JobPortal job = jobPortalRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return convertToDto(job);
    }

    // 4. Update job by recruiter
    @Override
    public JobPortalDto updateJobByUser(Long jobId, JobPortalDto dto, String recruiterEmail) {
        User user = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new UserNotFound("User not found"));

        JobPortal job = jobPortalRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getRecruiter().getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized to update this job");
        }

        modelMapper.map(dto, job);
        JobPortal updated = jobPortalRepository.save(job);
        return convertToDto(updated);
    }

    // 5. Delete job by recruiter
    @Override
    public void deleteJobByUser(Long jobId, String recruiterEmail) {
        User user = userRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new UserNotFound("User not found"));

        JobPortal job = jobPortalRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        if (!job.getRecruiter().getUser().getUserId().equals(user.getUserId())) {
            throw new RuntimeException("Unauthorized to delete this job");
        }

        jobPortalRepository.delete(job);
    }

    // 6. Get job recommendations for student based on experience level
    @Override
    public List<JobPortalDto> getJobsForStudent(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        StudentProfile profile = studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student profile not found"));

        if (profile.getExperienceLevel().equalsIgnoreCase("FRESHER") && !profile.isDigilockerVerified()) {
            throw new RuntimeException("DigiLocker verification is required for Freshers.");
        }

        List<JobPortalDto> recommended = jobPortalRepository.findAll().stream()
                .filter(job -> {
                    String jobType = job.getJobType().toUpperCase();
                    String experienceLevel = profile.getExperienceLevel().toUpperCase();
                    return jobType.equals(experienceLevel) || (experienceLevel.equals("FRESHER") && jobType.equals("INTERNSHIP"));
                })
                .map(job -> {
                    JobPortalDto dto = convertToDto(job);
                    System.out.println("✅ Job ID Mapped: " + dto.getJobId() + ", Title: " + dto.getTitle());
                    return dto;
                })
                .collect(Collectors.toList());

        return recommended;
    }


    // 7. Get jobs posted by current recruiter
    @Override
    public List<JobPortalDto> getJobsByRecruiter(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found"));

        RecruiterProfile recruiter = recruiterProfileRepository.findByUserUserId(user.getUserId())
                .orElseThrow(() -> new RuntimeException("Recruiter profile not found"));

        return jobPortalRepository.findByRecruiterRecruiterId(recruiter.getRecruiterId()).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    //  Manual DTO Conversion
    private JobPortalDto convertToDto(JobPortal job) {
        JobPortalDto dto = new JobPortalDto();
        dto.setJobId(job.getJobId());
        dto.setTitle(job.getTitle());
        dto.setSkills(job.getSkills());
        dto.setDesignation(job.getDesignation());
        dto.setLocation(job.getLocation());
        dto.setJobType(job.getJobType());
        dto.setMinRequirement(job.getMinRequirement());
        return dto;
    }
}








