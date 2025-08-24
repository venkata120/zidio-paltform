package com.zidio.payload;

import lombok.Data;
import java.time.LocalDate;


@Data
public class StudentCourseSubscriptionDto {
    private Long id;
    private Long courseId;
    private String courseName;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
    
    public Long getId() 
    {
    	return id; 
    	}
    public void setId(Long id) 
    {
    	this.id = id; 
    	}
    public Long getCourseId() 
    {
    	return courseId; 
    	}
    public void setCourseId(Long courseId) 
    {
    	this.courseId = courseId; 
    	}
    public String getCourseName() 
    {
    	return courseName; 
    	}
    public void setCourseName(String courseName) 
    
    {
    	this.courseName = courseName;
    	}
    public LocalDate getStartDate() 
    {
    	return startDate; 
    	}
    public void setStartDate(LocalDate startDate) 
    {
    	this.startDate = startDate; 
    	}
    public LocalDate getEndDate() 
    {
    	return endDate; 
    	}
    public void setEndDate(LocalDate endDate) 
    {
    	this.endDate = endDate; 
    	}
    public boolean isActive() 
    {
    	return active; 
    	}
    public void setActive(boolean active) 
    {
    	this.active = active; 
    	}
}

