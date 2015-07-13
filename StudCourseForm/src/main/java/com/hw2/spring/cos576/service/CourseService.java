package com.hw2.spring.cos576.service;

import org.springframework.validation.Errors;

import com.hw2.spring.cos576.domain.CourseAccount;

public interface CourseService {
	
	
	boolean addCourses(CourseAccount courseAccount,Errors errors);
}
