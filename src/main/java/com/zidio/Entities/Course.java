package com.zidio.Entities;

import jakarta.persistence.*;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    private String description;
    private int durationMonths;
    private double price;

    // Getters & Setters
    public Long getId() 
    {
    	return id; 
    	}
    public void setId(Long id) 
    {
    	this.id = id; 
    	}
    public String getName() 
    {
    	return name; 
    	}
    public void setName(String name) 
    {
    	this.name = name; 
    	}
    public String getType() 
    {
    	return type; 
    	}
    public void setType(String type) 
    {
    	this.type = type; 
    	}
    public String getDescription() 
    {
    	return description; 
    	}
    public void setDescription(String description) 
    {
    	this.description = description; 
    	}
    public int getDurationMonths() 
    {
    	return durationMonths; 
    	}
    public void setDurationMonths(int durationMonths) 
    {
    	this.durationMonths = durationMonths; 
    	}
    public double getPrice() 
    {
    	return price; 
    	}
    public void setPrice(double price) 
    {
    	this.price = price; 
    	}
}

