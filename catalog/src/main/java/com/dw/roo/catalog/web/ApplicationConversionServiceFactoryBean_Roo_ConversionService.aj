// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.dw.roo.catalog.web;

import com.dw.roo.catalog.domain.Course;
import com.dw.roo.catalog.domain.Instructor;
import com.dw.roo.catalog.domain.Student;
import com.dw.roo.catalog.web.ApplicationConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    public Converter<Course, String> ApplicationConversionServiceFactoryBean.getCourseToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.dw.roo.catalog.domain.Course, java.lang.String>() {
            public String convert(Course course) {
                return new StringBuilder().append(course.getCourseName()).append(' ').append(course.getDescription()).append(' ').append(course.getStartdate()).append(' ').append(course.getEnddate()).toString();
            }
        };
    }
    
    public Converter<Long, Course> ApplicationConversionServiceFactoryBean.getIdToCourseConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.dw.roo.catalog.domain.Course>() {
            public com.dw.roo.catalog.domain.Course convert(java.lang.Long id) {
                return Course.findCourse(id);
            }
        };
    }
    
    public Converter<String, Course> ApplicationConversionServiceFactoryBean.getStringToCourseConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.dw.roo.catalog.domain.Course>() {
            public com.dw.roo.catalog.domain.Course convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Course.class);
            }
        };
    }
    
    public Converter<Instructor, String> ApplicationConversionServiceFactoryBean.getInstructorToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.dw.roo.catalog.domain.Instructor, java.lang.String>() {
            public String convert(Instructor instructor) {
                return new StringBuilder().append(instructor.getFirstname()).append(' ').append(instructor.getLastname()).append(' ').append(instructor.getEmail()).toString();
            }
        };
    }
    
    public Converter<Long, Instructor> ApplicationConversionServiceFactoryBean.getIdToInstructorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.dw.roo.catalog.domain.Instructor>() {
            public com.dw.roo.catalog.domain.Instructor convert(java.lang.Long id) {
                return Instructor.findInstructor(id);
            }
        };
    }
    
    public Converter<String, Instructor> ApplicationConversionServiceFactoryBean.getStringToInstructorConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.dw.roo.catalog.domain.Instructor>() {
            public com.dw.roo.catalog.domain.Instructor convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Instructor.class);
            }
        };
    }
    
    public Converter<Student, String> ApplicationConversionServiceFactoryBean.getStudentToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.dw.roo.catalog.domain.Student, java.lang.String>() {
            public String convert(Student student) {
                return new StringBuilder().append(student.getStudentName()).append(' ').append(student.getEmail()).toString();
            }
        };
    }
    
    public Converter<Long, Student> ApplicationConversionServiceFactoryBean.getIdToStudentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.dw.roo.catalog.domain.Student>() {
            public com.dw.roo.catalog.domain.Student convert(java.lang.Long id) {
                return Student.findStudent(id);
            }
        };
    }
    
    public Converter<String, Student> ApplicationConversionServiceFactoryBean.getStringToStudentConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.dw.roo.catalog.domain.Student>() {
            public com.dw.roo.catalog.domain.Student convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Student.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getCourseToStringConverter());
        registry.addConverter(getIdToCourseConverter());
        registry.addConverter(getStringToCourseConverter());
        registry.addConverter(getInstructorToStringConverter());
        registry.addConverter(getIdToInstructorConverter());
        registry.addConverter(getStringToInstructorConverter());
        registry.addConverter(getStudentToStringConverter());
        registry.addConverter(getIdToStudentConverter());
        registry.addConverter(getStringToStudentConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
