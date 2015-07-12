package com.cos576.Hw1.service;

import java.util.List;

import com.cos576.Hw1.model.Course;

public interface CourseService {

	void createCourse(Course course);


	List<Course> getCourse();

	Course getCoursebyName(String courseName);

	void updateCourse(Course course);

	void deleteCourse(Long courseID);

}
