// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.dw.roo.catalog.domain;

import com.dw.roo.catalog.domain.Course;
import com.dw.roo.catalog.domain.Instructor;
import java.util.Set;

privileged aspect Instructor_Roo_JavaBean {
    
    public String Instructor.getFirstname() {
        return this.firstname;
    }
    
    public void Instructor.setFirstname(String firstname) {
        this.firstname = firstname;
    }
    
    public String Instructor.getLastname() {
        return this.lastname;
    }
    
    public void Instructor.setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public String Instructor.getEmail() {
        return this.email;
    }
    
    public void Instructor.setEmail(String email) {
        this.email = email;
    }
    
    public Set<Course> Instructor.getCourses() {
        return this.courses;
    }
    
    public void Instructor.setCourses(Set<Course> courses) {
        this.courses = courses;
    }
    
}
