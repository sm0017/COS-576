package com.cos576.Hw1.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.cos576.Hw1.util.StringUtils;

public class Course {
	
	private Long courseID;
	private String courseName;
	private String courseStartDate;
	private String courseEndDate;
	private String facultyLastName;
	private String facultyFirstName;
	private String facultyEmail;
	
	

	public Long getCourseID() {
		return courseID;
	}
	public void setCourseID(Long courseID) {
		this.courseID = courseID;
	}
	
	@Length(min=1, max = 40)
	public String getFacultyLastName() {
		return facultyLastName;
	}
	
	
	@Length(min=1, max = 40)
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = StringUtils.cleanup(courseName);
	}
	public void setFacultyLastName(String facultyLastName) {
		this.facultyLastName = StringUtils.cleanup(facultyLastName);
		
	}
	
	@Length(min=1, max = 40)
	public String getFacultyFirstName() {
		return facultyFirstName;
	}
	public void setFacultyFirstName(String facultyFirstName) {
		this.facultyFirstName = StringUtils.cleanup(facultyFirstName);
	}
	public String getFacultyEmail() {
		return facultyEmail;
	}
	public void setFacultyEmail(String facultyEmail) {
		this.facultyEmail = StringUtils.cleanup(facultyEmail);
	}
	public String getCourseStartDate() {
		return courseStartDate;
	}
	public void setCourseStartDate(String courseStartDate) {
		this.courseStartDate = courseStartDate;
	}
	public String getCourseEndDate() {
		return courseEndDate;
	}
	public void setCourseEndDate(String courseEndDate) {
		this.courseEndDate = courseEndDate;
	}
	
	
	public String getFullName() 
	{
		String fullName = facultyFirstName + ", " + facultyLastName;
		
		
		return fullName;
	}
	
	@Override
	public String toString() {
		return "Course [courseID=" + courseID + ", facultyLastName="
				+ facultyLastName + ", facultyFirstName=" + facultyFirstName
				+ ", facultyEmail=" + facultyEmail + ", courseStartDate="
				+ courseStartDate + ", courseEndDate=" + courseEndDate + "]";
	}
	
	
	
	
	
	

}
