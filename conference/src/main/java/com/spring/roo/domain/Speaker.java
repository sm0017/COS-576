package com.spring.roo.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.persistence.Column;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Speaker {

    /**
     */
    @NotNull
    private String lastname;

    /**
     */
    @NotNull
    private String firstname;

    /**
     */
    @NotNull
    @Column(unique = true)
    private String email;

    /**
     */
    private String organization;

    /**
     */
    @NotNull
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date birthdate;

    /**
     */
    @Min(25L)
    @Max(60L)
    private Long age;

    /**
     */
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Talk> talks = new HashSet<Talk>();
}
