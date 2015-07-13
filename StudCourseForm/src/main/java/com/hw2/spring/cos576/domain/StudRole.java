package com.hw2.spring.cos576.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "studrole")
@NamedQuery(name = "findStudByName", query = "from StudRole where name= :name")


public class StudRole {
	private Long studID;
    private String name;

    
   
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "studID")
    public Long getStudID() {
		return studID;
	}
   
   @SuppressWarnings("unused")
	public void setStudID(Long studID) {
	this.studID = studID;
}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
    
    
    
}
