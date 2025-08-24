package com.zidio.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class StudentProfileNotFound  extends RuntimeException{

	
	private String message;
	public StudentProfileNotFound( String message) {
		
		super(message);
		this.message = message;
		
	}
	
}
