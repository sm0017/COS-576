// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.dw.roo.catalog.domain;

import com.dw.roo.catalog.domain.Instructor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Instructor_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Instructor.entityManager;
    
    public static final List<String> Instructor.fieldNames4OrderClauseFilter = java.util.Arrays.asList("firstname", "lastname", "email", "courses");
    
    public static final EntityManager Instructor.entityManager() {
        EntityManager em = new Instructor().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Instructor.countInstructors() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Instructor o", Long.class).getSingleResult();
    }
    
    public static List<Instructor> Instructor.findAllInstructors() {
        return entityManager().createQuery("SELECT o FROM Instructor o", Instructor.class).getResultList();
    }
    
    public static List<Instructor> Instructor.findAllInstructors(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Instructor o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Instructor.class).getResultList();
    }
    
    public static Instructor Instructor.findInstructor(Long id) {
        if (id == null) return null;
        return entityManager().find(Instructor.class, id);
    }
    
    public static List<Instructor> Instructor.findInstructorEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Instructor o", Instructor.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Instructor> Instructor.findInstructorEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Instructor o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Instructor.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Instructor.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Instructor.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Instructor attached = Instructor.findInstructor(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Instructor.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Instructor.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Instructor Instructor.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Instructor merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
