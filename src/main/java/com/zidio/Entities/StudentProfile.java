package com.zidio.Entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobApplication> jobApplications;


    @Column(name = "resume_url", nullable = true)
    private String resumeUrl;

    private String education;
    private String skills;

    @Column(name = "experience_level")
    private String experienceLevel; // FRESHER, EXPERIENCED, INTERNSHIP

    @Column(name = "digilocker_verified")
    private Boolean digilockerVerified = false;

    @Column(name = "digilocker_token")
    private String digilockerToken;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    // Getters and Setters
    public Long getStudentId() 
    {
    	return studentId; 
    	}
    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    public String getResumeUrl() 
    {
    	return resumeUrl;
    	}
    public void setResumeUrl(String ResumeUrl) { 
    	this.resumeUrl = ResumeUrl; 
    	}
    public String getEducation() 
    {
    	return education; 
    	}
    public void setEducation(String education) 
    {
    	this.education = education; 
    	}
    public String getSkills() 
    {
    	return skills; 
    	}
    public void setSkills(String skills) 
    {
    	this.skills = skills; 
    	}
    public String getExperienceLevel() 
    {
    	return experienceLevel; 
    	}
    public void setExperienceLevel(String experienceLevel) 
    {
    	this.experienceLevel = experienceLevel; 
    	}
    public Boolean isDigilockerVerified() 
    {
    	return digilockerVerified; 
    	}
    public void setDigilockerVerified(Boolean digilockerVerified) 
    {
    	this.digilockerVerified = digilockerVerified; 
    	}
    public String getDigilockerToken() 
    {
    	return digilockerToken; }
    public void setDigilockerToken(String digilockerToken) 
    {
    	this.digilockerToken = digilockerToken; 
    	}
    public User getUser() 
    {
    	return user; 
    	}
    public void setUser(User user) 
    {
    	this.user = user;
    	}
}



