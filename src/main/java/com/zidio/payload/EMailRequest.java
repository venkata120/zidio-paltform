package com.zidio.payload;

import lombok.Data;

@Data
public class EMailRequest {

	public String to;
    public String subject;
    public String message;
    
	 public String getTo() 
    {
    	return to; 
    	}
		 public void setTo(String to)
		 {
			 this.to = to; }
		 
    
    public String getSubject() 
        {
        	return subject; 
        	}
    
			 public void setSubject(String subject) { 
				 this.subject = subject; 
				 }
			
    
    public String getMessage() 
    {
    	return message; 
    	}
		
		  public void setMessage(String message) { 
			  this.message = message; 
			  }     
}
