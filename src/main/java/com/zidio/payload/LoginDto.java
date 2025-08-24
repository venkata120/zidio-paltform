package com.zidio.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
		private String email;
	    private String password;
		
		
		 public String getemail() 
		    {
		    	return email; 
		    	}
		 
		 public String getpassword() 
		    {
		    	return password; 
		    	}
		   

}
