package com.cos576.Hw1.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cos576.Hw1.model.Course;

@Component
public class CourseRowMapper implements RowMapper<Course> {

	@Override
	public Course mapRow(ResultSet rscourse, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Course course = new Course();
		
		course.setCourseID(rscourse.getLong(1));
		course.setCourseName(rscourse.getString(2));
		course.setCourseStartDate(rscourse.getString(3));
		course.setCourseEndDate(rscourse.getString(4));
		course.setFacultyFirstName(rscourse.getString(5));
		course.setFacultyLastName(rscourse.getString(6));
		course.setFacultyEmail(rscourse.getString(7));
		
		return course;
		
		
	}

}
