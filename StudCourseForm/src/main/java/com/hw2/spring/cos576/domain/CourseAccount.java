package com.hw2.spring.cos576.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.hw2.spring.cos576.domain.StudRole;

@NamedQuery(
		name = "findAccountByCoursename",
		query = "from CourseAccount where coursename = :coursename")
	@Entity
	@Table(name = "courseact")
public class CourseAccount {
	

	
	private Long courseID;
	private String coursename, facultyFirstName, facultyLastName, startDate, endDate;
	private Date dateCreated;
	
	@Id
	@GeneratedValue
	@Column(name = "courseID")
	public Long getCourseID() {
		return courseID;
	}
	public void setCourseID(Long courseID) {
		this.courseID = courseID;
	}
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name ="coursename")
	public String getCoursename() {
		return coursename;
	}
	
	public void setCoursename(String coursename) {
		this.coursename = coursename;
	}
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name ="facultyFirstName")
	public String getFacultyFirstName() {
		return facultyFirstName;
	}
	public void setFacultyFirstName(String facultyFirstName) {
		this.facultyFirstName = facultyFirstName;
	}
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name ="facultyLastName")
	public String getFacultyLastName() {
		return facultyLastName;
	}
	public void setFacultyLastName(String facultyLastName) {
		this.facultyLastName = facultyLastName;
	}
	
	@NotNull
	@Size(min = 1, max = 10)
	@Column(name ="startDate")
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	@NotNull
	@Size(min = 1, max = 10)
	@Column(name ="endDate")
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	@Column(name = "date_created")
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	
	

}
