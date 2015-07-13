package com.hw2.spring.cos576.service;


import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import com.hw2.spring.cos576.dao.CourseDao;
import com.hw2.spring.cos576.dao.StudAccountDao;
import com.hw2.spring.cos576.dao.StudRoleDao;
import com.hw2.spring.cos576.domain.CourseAccount;
import com.hw2.spring.cos576.domain.StudAccount;
import com.hw2.spring.cos576.domain.StudRole;

@Service
@Transactional(readOnly = true)
public class CourseServiceImpl implements CourseService{

	
	
private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);
	
	@Inject private CourseDao courseAccountDao;

	@Override
	@Transactional(readOnly = false)	

	// Add the new course
	public boolean addCourses(CourseAccount courseAccount, Errors errors) {
		// TODO Auto-generated method stub
		validateCoursename(courseAccount.getCoursename(), errors);
		boolean valid = !errors.hasErrors();
		
		if (valid) {
			
			courseAccountDao.create(courseAccount);
		}
		
		return valid;
	}

	

		
		
	


	private void validateCoursename(String coursename, Errors errors) {
		if (courseAccountDao.findByCourseName(coursename) != null) {
			log.debug("Validation failed: duplicate coursename");
			errors.rejectValue("coursename", "error.duplicate", new String[] { coursename }, null);
		}
	}
	
	


	
}
