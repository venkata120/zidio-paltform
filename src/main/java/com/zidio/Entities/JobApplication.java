package com.zidio.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "job_applications",
       uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "job_id"}))
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private StudentProfile student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "job_id")
    private JobPortal job;
    
    @Column(name = "jobTitle")
    private String jobTitle;
    @Column(name = "companyName")
    private String companyName;

    @Column(name = "applied_date", nullable = false)
    private LocalDate appliedDate;
    
    @Column(name = "status")
    private String status;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public StudentProfile getStudent() {
        return student;
    }
    public void setStudent(StudentProfile student) {
        this.student = student;
    }

    public String getJobTitle() 
    {
    	return jobTitle; 
    	}
    public void setJobTitle(String jobTitle) 
    {
    	this.jobTitle = jobTitle; 
    	
    	}
    public String getCompanyName() {
    	return companyName; 
    	}
    public void setCompanyName(String companyName) 
    {
    	this.companyName = companyName; 
    	}
    
    public JobPortal getJob() {
        return job;
    }
    public void setJob(JobPortal job) {
        this.job = job;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }
    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
