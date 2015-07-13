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
		name = "findAccountByUsername",
		query = "from StudAccount where username = :username")
	@Entity
	@Table(name = "studaccount")
public class StudAccount {
	

	
	
	private Long studID;
	private String username, firstName, lastName, email;
	private boolean marketingOk = true, acceptTerms = false, enabled = true;
	private Date dateCreated;
	private Collection<StudRole> studRoles = new HashSet<StudRole>();
	
	@Id
	@GeneratedValue
	@Column(name = "studID")
	public Long getStudID() {
		return studID;
	}
	
	@SuppressWarnings("unused")
	public void setStudID(Long studID) {
		this.studID = studID;
	}


	@NotNull
	@Size(min = 1, max = 50)
	@Column(name ="username")
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "first_name")
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "last_name")
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	@Transient
	public String getFullName() { return firstName + " " + lastName; }
	
	@NotNull
	@Size(min = 6, max = 50)
	@Email
	@Column(name = "email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Column(name = "marketing_ok")
	public boolean isMarketingOk() {
		return marketingOk;
	}
	
	
	public void setMarketingOk(boolean marketingOk) {
		this.marketingOk = marketingOk;
	}
	
	@AssertTrue(message = "{account.acceptTerms.assertTrue.message}")
	@Column(name = "accept_terms")
	public boolean isAcceptTerms() {
		return acceptTerms;
	}
	public void setAcceptTerms(boolean acceptTerms) {
		this.acceptTerms = acceptTerms;
	}
	
	@Column(name = "enabled")
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	@Column(name = "date_created")
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}



	@ManyToMany
	@JoinTable(
			name = "studAccount_studRole",
			joinColumns = { @JoinColumn(name = "studAccount_id", referencedColumnName = "studID") },
			inverseJoinColumns = { @JoinColumn(name = "studRole_id", referencedColumnName = "studID") })
	
	
	public Collection<StudRole> getStudRoles() {
		return studRoles;
	}


	public void setStudRoles(Collection<StudRole> studRoles) {
		this.studRoles = studRoles;
	}
	

}
