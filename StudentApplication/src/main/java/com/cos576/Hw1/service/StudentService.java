package com.cos576.Hw1.service;

import java.util.List;

import com.cos576.Hw1.model.Student;

public interface StudentService {

	void createStudent(Student student);

	// Return all the student
	List<Student> getStudent();

	List<Student> getStudentByEmail(String email);

	Student getStudentbyID(Long studID);

	void updateStudent(Student student);

	void deleteStudent(Long studID);

}

