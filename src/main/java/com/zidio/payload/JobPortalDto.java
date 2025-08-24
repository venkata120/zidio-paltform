package com.zidio.payload;

public class JobPortalDto {
    private Long jobId;
    private String title;
    private String designation;
    private String skills;
    private String location;
    private String minRequirement;
    private String jobType;
    
    public Long getJobId()  
    {
    	return jobId; }
    public void setJobId(Long jobId) 
    { this.jobId = jobId; 
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
    { return skills; 
    }
    public void setSkills(String skills)
    { 
    	this.skills = skills;
    }

    
}

