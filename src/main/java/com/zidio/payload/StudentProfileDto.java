package com.zidio.payload;
  
  import jakarta.validation.constraints.NotBlank; 
  import lombok.Data;
  
  @Data
  public class StudentProfileDto 
  { 
	  private Long studentId;
  
	  private String email;
	  
  private String resumeUrl;
  
  @NotBlank(message = "Education is required") 
  private String education;
  
  @NotBlank(message = "Skills are required") 
  private String skills;
  
  @NotBlank(message = "Experience Level is required")
  private String experienceLevel;
  
  public String getExperienceLevel() 
  {
	  return experienceLevel;
	  } public void setExperienceLevel(String experienceLevel) 
	  {
		  this.experienceLevel = experienceLevel; 
		  }

  private boolean digilockerVerified;
  
  public boolean isDigilockerVerified() 
  {
	    return digilockerVerified;
	}
	public void setDigilockerVerified(boolean digilockerVerified) 
	{
	    this.digilockerVerified = digilockerVerified;
	}
  
  public Long getstudentId() 
  { 
	  return studentId; }
  
  public void setstudentId(Long studentId)
  {
	  this.studentId = studentId; }
  
  public String getResumeUrl() 
  {
	  return resumeUrl;
	  } public void setResumeUrl(String ResumeUrl) 
	  {
		  this.resumeUrl = ResumeUrl; 
		  }
	  public String getEducation() 
	  {
		  return education; 
		  }
	  public void setEducation(String name)
	  {
  this.education = name; 
  }
	  public String getSkills() 
	  {
		  return skills; 
		  }
	  public void setSkills(String name) 
	  {
		  this.skills = name;
  
  }
	  
	  public String getEmail() {
	        return email;
	    }

	    public void setEmail(String email) {
	        this.email = email;
	    }
  }
 

