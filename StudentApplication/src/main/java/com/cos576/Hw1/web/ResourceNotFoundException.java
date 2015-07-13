package com.cos576.Hw1.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


 
@ResponseStatus(value = HttpStatus.NOT_FOUND)
@SuppressWarnings("serial")
public class ResourceNotFoundException extends RuntimeException {
	
	public ResourceNotFoundException() { }
	
	public ResourceNotFoundException(String message) { super(message); }
	
	public ResourceNotFoundException(Throwable cause) { super(cause); }
	
	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}