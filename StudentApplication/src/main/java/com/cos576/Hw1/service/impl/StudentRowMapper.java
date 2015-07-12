package com.cos576.Hw1.service.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cos576.Hw1.model.Student;

@Component
public class StudentRowMapper implements RowMapper<Student> {

	
	public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		Student student = new Student();
		student.setStudID(rs.getLong(1));
		student.setFirstName(rs.getString(2));
		student.setMiddleInitial(rs.getString(3));
		student.setLastName(rs.getNString(4));
		student.setEmail(rs.getString(5));
		return student;
		
	}

	
}
