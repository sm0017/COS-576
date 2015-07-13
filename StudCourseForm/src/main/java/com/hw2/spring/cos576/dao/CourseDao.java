package com.hw2.spring.cos576.dao;

import com.hw2.spring.cos576.domain.CourseAccount;
import com.hw2.spring.cos576.dao.Dao;

public interface CourseDao extends Dao<CourseAccount> {

	
void create(CourseAccount courseAccount);
	
CourseAccount  findByCourseName(String coursename);
	
}
