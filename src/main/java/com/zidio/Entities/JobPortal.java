package com.zidio.Entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "JobPortal")
public class JobPortal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recruiter_id", nullable = false)
    private RecruiterProfile recruiter;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String designation;
    
    @Column(length = 500)
    private String skills;

    @Column(nullable = false)
    private String location;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String minRequirement;
    
    @Column(name = "job_type", nullable = false)
    private String jobType;
    
    @OneToMany(mappedBy = "job", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobApplication> applications;

    public Long getJobId()
    {
    	return jobId;
    	}
    public void setJobId(Long jobId) 
    {
    	this.jobId = jobId;
    	}
    

    public RecruiterProfile getRecruiter() 
    {
    	return recruiter;
    	}
    
    public void setRecruiter(RecruiterProfile recruiter) 
    {
    	this.recruiter = recruiter; 
    	}
    

    public String getTitle() 
    {
    	return title;
    	}
    
    public void setTitle(String title) 
    {
    	this.title = title;
    	}

    public String getDesignation() 
    {
    	return designation;
    	}
    public void setDesignation(String designation) 
    {
    	this.designation = designation;
    	}

    public String getLocation() 
    {
    	return location;
    	}
    public void setLocation(String location) 
    {
    	this.location = location;
    	}

    public String getMinRequirement() 
    {
    	return minRequirement; 
    	}
    
    public void setMinRequirement(String minRequirement)
    {
    	this.minRequirement = minRequirement;
    	}
    
    public String getJobType() 
    {
    	return jobType; 
    	}
    public void setJobType(String jobType) 
    {
    	this.jobType = jobType;
    	}
    
    public String getSkills() 
    {
    	return skills; 
    	}
    public void setSkills(String skills) 
    {
    	this.skills = skills;
    	}

    
}


