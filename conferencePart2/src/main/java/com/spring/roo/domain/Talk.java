package com.spring.roo.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Talk {

    /**
     */
    @NotNull
    private String title;

    /**
     */
    @NotNull
    @Size(max = 4000)
    private String description;

    /**
     */
    @NotNull
    @ManyToOne
    
    private Speaker speaker;
}
