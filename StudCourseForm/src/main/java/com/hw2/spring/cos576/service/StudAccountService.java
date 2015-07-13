package com.hw2.spring.cos576.service;

import org.springframework.validation.Errors;

import com.hw2.spring.cos576.domain.StudAccount;

public interface StudAccountService {
	/*
	 * 
	 * Registers the given student, check for valid username
	 * 
	 */
	
	boolean registerStudAccount(StudAccount studaccount, String password, Errors errors);
}
