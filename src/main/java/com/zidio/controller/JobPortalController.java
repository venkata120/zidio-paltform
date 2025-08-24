package com.zidio.controller;

import com.zidio.Entities.User;
import com.zidio.payload.JobPortalDto;
import com.zidio.repository.UserRepository;
import com.zidio.service.JobApplicationService;
import com.zidio.service.JobPortalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job")
public class JobPortalController {

    @Autowired
    private JobPortalService jobPortalService;

    @Autowired
    private JobApplicationService jobApplicationService;

    @Autowired
    private UserRepository userRepository;

    //  Specific mappings first
    @PostMapping("/post")
    public ResponseEntity<JobPortalDto> postJob(@RequestBody JobPortalDto dto, Authentication auth) {
        String email = auth.getName();
        return new ResponseEntity<>(jobPortalService.postJob(dto, email), HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<JobPortalDto>> getAllJobs() {
        return ResponseEntity.ok(jobPortalService.getAllJobs());
    }

    @GetMapping("/recommend")
    public ResponseEntity<List<JobPortalDto>> getRecommendedJobs(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(jobPortalService.getJobsForStudent(email));
    }

    @PostMapping("/apply/{jobId}")
    public ResponseEntity<String> applyToJob(@PathVariable Long jobId, Authentication authentication) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        jobApplicationService.applyToJob(user.getUserId(), jobId);
        return ResponseEntity.ok("Applied to job successfully.");
    }
    
    @GetMapping("/my-jobs")
    public ResponseEntity<List<JobPortalDto>> getJobsByRecruiter(Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(jobPortalService.getJobsByRecruiter(email));
    }


	/*
	 * @GetMapping("/recruiter/my-jobs") public String recruiterJobList(Model model,
	 * Authentication auth) { String email = auth.getName(); List<JobPortalDto> jobs
	 * = jobPortalService.getJobsByRecruiter(email); model.addAttribute("jobs",
	 * jobs); return "recruiterViewMyJobs"; // Resolves to
	 * /WEB-INF/views/recruiterViewMyJobs.jsp }
	 */

    //  Use regex to match only numeric jobId to avoid conflict with /post
    @GetMapping("/{jobId:\\d+}")
    public ResponseEntity<JobPortalDto> getJobById(@PathVariable Long jobId) {
        return ResponseEntity.ok(jobPortalService.getJobById(jobId));
    }

    @PutMapping("/{jobId:\\d+}")
    public ResponseEntity<JobPortalDto> updateJob(@PathVariable Long jobId,
                                                  @RequestBody JobPortalDto dto,
                                                  Authentication auth) {
        String email = auth.getName();
        return ResponseEntity.ok(jobPortalService.updateJobByUser(jobId, dto, email));
    }

    @DeleteMapping("/{jobId:\\d+}")
    public ResponseEntity<String> deleteJob(@PathVariable Long jobId, Authentication auth) {
        String email = auth.getName();
        jobPortalService.deleteJobByUser(jobId, email);
        return ResponseEntity.ok("Deleted");
    }
    
    
}
