package com.hw2.spring.cos576.web;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.ScriptAssert;


public class CourseForm {
	private String coursename, facultyFirstName, facultyLastName, startDate, endDate;

	@NotNull
	@Size(min = 1, max = 50)
	public String getCoursename() {
		return coursename;
	}

	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}

	@NotNull
	@Size(min = 1, max = 50)
	public String getFacultyFirstName() {
		return facultyFirstName;
	}

	public void setFacultyFirstName(String facultyFirstName) {
		this.facultyFirstName = facultyFirstName;
	}

	@NotNull
	@Size(min = 1, max = 50)
	public String getFacultyLastName() {
		return facultyLastName;
	}

	public void setFacultyLastName(String facultyLastName) {
		this.facultyLastName = facultyLastName;
	}

	@NotNull
	@Size(min = 1, max = 10)
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Size(min = 1, max = 10)
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "CourseForm [coursename=" + coursename + ", facultyFirstName="
				+ facultyFirstName + ", facultyLastName=" + facultyLastName
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
	
			

}
