package com.dw.roo.catalog.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Student {

    /**
     */
    @NotNull
    private String StudentName;

    /**
     */
    @NotNull
    @Column(unique = true)
    private String email;

    /**
     */
    @ManyToOne
    private Course course;

    /**
     */
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<Course>();
}
