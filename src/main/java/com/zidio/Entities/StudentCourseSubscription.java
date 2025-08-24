package com.zidio.Entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student_course_subscriptions")
public class StudentCourseSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "student_id")
    private StudentProfile student;

    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id")
    private Course course;

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    // Getters & Setters
    public Long getId() 
    {
    	return id; 
    	}
    public void setId(Long id) 
    {
    	this.id = id; 
    	}
    public StudentProfile getStudent() 
    {
    	return student; 
    	}
    public void setStudent(StudentProfile student) 
    {
    	this.student = student; 
    	}
    public Course getCourse() 
    {
    	return course; }
    public void setCourse(Course course) 
    {
    	this.course = course; 
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

