// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.dw.roo.catalog.domain;

import com.dw.roo.catalog.domain.Course;
import com.dw.roo.catalog.domain.Instructor;
import java.util.Date;

privileged aspect Course_Roo_JavaBean {
    
    public String Course.getCourseName() {
        return this.courseName;
    }
    
    public void Course.setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
    public String Course.getDescription() {
        return this.description;
    }
    
    public void Course.setDescription(String description) {
        this.description = description;
    }
    
    public Date Course.getStartdate() {
        return this.startdate;
    }
    
    public void Course.setStartdate(Date startdate) {
        this.startdate = startdate;
    }
    
    public Date Course.getEnddate() {
        return this.enddate;
    }
    
    public void Course.setEnddate(Date enddate) {
        this.enddate = enddate;
    }
    
    public Instructor Course.getInstrctor() {
        return this.instrctor;
    }
    
    public void Course.setInstrctor(Instructor instrctor) {
        this.instrctor = instrctor;
    }
    
}
