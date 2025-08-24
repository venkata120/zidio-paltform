package com.zidio.payload;

import lombok.Data;
import java.time.LocalDate;
@Data
public class JobApplicationDto {
    private Long id;
    private Long studentId;
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private LocalDate appliedDate;
    private String status; // NEW FIELD: SELECTED or REJECTED
    
    public Long getId() 
    {
    	return id; 
    	}
    public void setId(Long id) 
    {
    	this.id = id; 
    	}

    public Long getStudentId() 
    
    {
    	return studentId;
    	}
    public void setStudentId(Long studentId) 
    {
    	this.studentId = studentId; 
    	}

    public Long getJobId() 
    {
    	return jobId; 
    	}
    public void setJobId(Long jobId) 
    {
    	this.jobId = jobId; 
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


    public LocalDate getAppliedDate() 
    {
    	return appliedDate; 
    	}
    public void setAppliedDate(LocalDate appliedDate) 
    {
    	this.appliedDate = appliedDate; 
    	}

    public String getStatus() 
    {
    	return status; 
    	}
    public void setStatus(String status) 
    {
    	this.status = status; 
    	}
}
