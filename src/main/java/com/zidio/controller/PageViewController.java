package com.zidio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zidio.payload.JobPortalDto;
import com.zidio.payload.RecruiterProfileDto;
import com.zidio.payload.StudentProfileDto;
import com.zidio.service.JobPortalService;
import com.zidio.service.RecruiterProfileService;
import com.zidio.service.StudentProfileService;




@Controller
public class PageViewController {
	
	 @Autowired
	    private JobPortalService jobPortalService;
	 
	 @Autowired
	 private StudentProfileService studentProfileService;

	 @Autowired
	 private RecruiterProfileService recruiterProfileService;
	
    @GetMapping("/ui/login")
    public String showLoginPage() {
        return "Login";  // Login.jsp
    }

    @GetMapping("/ui/Register")
    public String showRegisterPage() {
        return "Register";  // Register.jsp
    }

    @GetMapping("/Dashboard")
    public String showDashboardPage() {
        return "Dashboard";  // Dashboard.jsp
    }

    @GetMapping("/student/profile/form")
    public String showStudentProfileForm() {
        return "StudentProfile";  // StudentProfile.jsp
    }

    @GetMapping("/student/jobs")
    public String showJobList() {
        return "JobList";  // Matches JobList.jsp
    }
    
	/*
	 * @GetMapping("/student/job/view/{jobId}") public String viewJobDetails() {
	 * return "JobDetails"; // Matches JobDetails.jsp }
	 */
    @GetMapping("/student/job/view")
    public String viewJobDetails(@RequestParam Long jobId, Model model) {
        JobPortalDto job = jobPortalService.getJobById(jobId);
        model.addAttribute("job", job);
        return "JobDetails"; // JobDetails.jsp
    }
    
    
    
    @GetMapping("/student/profile/edit")
    public String editStudentProfile() {
        return "EditStudentProfile"; // Matches EditStudentProfile.jsp
    }
    
    @GetMapping("/student/applications")
    public String showStudentApplicationsPage() {
        return "studentMyApplications";  // JSP page name
    }
    
    @GetMapping("/student/my-applications")
    public String redirectToStudentApplications() {
        return "studentMyApplications";
    }
    
    @GetMapping("/student/courses")
    public String showStudentCoursesPage() {
        return "studentCourses"; // studentCourses.jsp
    }
    
    @GetMapping("/ui/forgot-password")
    public String showForgotPasswordPage() 
    {
        return "forgotPassword";
    }

    @GetMapping("/ui/reset-password")
    public String showResetPasswordPage() 
    {
        return "resetPassword";
    }
    
    @GetMapping("/admin/users")
    public String showAdminUsersPage() 
    {
        return "adminUsers";
    }
    
    @GetMapping("/admin/students")
    public String adminStudentListPage(Model model) {
        model.addAttribute("students", studentProfileService.getAllProfiles());
        return "adminViewStudentList";
    }

    @GetMapping("/admin/recruiters")
    public String adminRecruiterListPage(Model model) {
        model.addAttribute("recruiters", recruiterProfileService.getAllRecruiterProfiles());
        return "adminViewRecruiterList";
    }

    
    
    
    @GetMapping("/admin/student-profile")
    public String adminViewStudentProfile(@RequestParam String email, Model model) {
    	StudentProfileDto profile = studentProfileService.getProfileByEmail(email);

        model.addAttribute("student", profile);
        return "StudentProfile"; // This reuses StudentProfile.jsp
    }

    @GetMapping("/admin/recruiter-profile")
    public String adminViewRecruiterProfile(@RequestParam String email, Model model) {
        RecruiterProfileDto profile = recruiterProfileService.getProfileByEmail(email);
        model.addAttribute("recruiter", profile);
        return "RecruiterProfile"; // This reuses RecruiterProfile.jsp
    }
    
    
    
    
    @GetMapping("/student/resume/upload")
    public String showResumeUploadPage() 
    {
        return "resumeUpload"; // resumeUpload.jsp
    }
    
    @GetMapping("/recruiter/job/edit/{jobId}")
    public String editJobPage(@PathVariable Long jobId, Model model) {
        model.addAttribute("jobId", jobId);
        return "recruiterEditJob";
    }

    
    @GetMapping("/recruiter/job/post")
    public String showRecruiterJobPostPage() {
        return "RecruiterJobPost"; // must match JSP filename
    }
    
    @GetMapping("/recruiter/my-jobs")
    public String viewMyJobsPage() {
        return "recruiterViewMyJobs"; // loads recruiterViewMyJobs.jsp
    }
     
    
    @GetMapping("/recruiter/profile/form")
    public String showRecruiterProfileForm() {
        return "RecruiterProfile";
    }
       
    @GetMapping("/jobRecommendations")
    public String showJobRecommendationsPage() {
        return "jobRecommendations"; // jobRecommendations.jsp
    }
    
    @GetMapping("/notifications")
    public String showNotificationPage() {
        return "notifications"; // notification.jsp
    }
    
    
    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "PAGE CONTROLLER IS WORKING";
    }
}
