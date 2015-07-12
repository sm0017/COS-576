package com.cos576.Hw1.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.cos576.Hw1.util.StringUtils;

public class Student {
	
	private Long studID;
	private String lastName;
	private String middleInitial;
	private String firstName;
	private String email;
	
	
	
	public Long getStudID() {
		return studID;
	}
	public void setStudID(Long studID) {
		this.studID = studID;
	}
	
	@NotNull
	@Length(min=1, max = 40)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = StringUtils.cleanup(lastName);
	}
	

	@NotNull
	@Length(min=1, max = 40)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = StringUtils.cleanup(firstName);
	}
	
	@Length(max =1)
	public String getMiddleInitial() {
		return middleInitial;
	}
	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = StringUtils.cleanup(middleInitial);
	}
	@Email
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = StringUtils.cleanup(email);
	}
	
	
	
	public String getFullName() {
		String fullName = lastName + ", " + firstName;
		if (! (middleInitial == null || "".equals(middleInitial.trim()))) {
			fullName += " " + middleInitial + ".";
		}
		return fullName;
	}
	@Override
	public String toString() {
		return "Student [studID=" + studID + ", lastName=" + lastName
				+ ", middleInitial=" + middleInitial + ", firstName="
				+ firstName + ", email=" + email + "]";
	}
	
	

}
