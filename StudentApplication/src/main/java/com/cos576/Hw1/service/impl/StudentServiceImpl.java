package com.cos576.Hw1.service.impl;


import static org.springframework.util.Assert.notNull;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.cos576.Hw1.model.Student;
import com.cos576.Hw1.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {

	@Inject private NamedParameterJdbcOperations jdbcTemplate;
	@Inject private StudentRowMapper studentRowMapper;

	private static final String CREATE_SQL = "insert into student (last_name, first_name, middleInitial, email) values (:lastName, :firstName, :middleInitial, :email)";
	private static final String FIND_ALL_SQL = "select studID, last_name, first_name, middleInitial, email from student";
	private static final String FIND_ALL_BY_EMAIL_LIKE_SQL = "select studID, last_name, first_name, middleInitial, email from student where email like :email";
	private static final String FIND_ONE_SQL = "select studID, last_name, first_name, middleInitial, email from student where studID = :studID";
	private static final String UPDATE_SQL = "update student set last_name = :lastName, first_name = :firstName, middleInitial = :middleInitial, email = :email "
			+ "where studID = :studID";
	private static final String DELETE_SQL = "delete from student where studID = :studID";

	@Override
	public void createStudent(Student student) {
		System.out.println("I am in studentserviceImpl");
		notNull(student, "student can't be null");
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("firstName", student.getFirstName())
				.addValue("middleInitial", student.getMiddleInitial())
				.addValue("lastName", student.getLastName())
				.addValue("email", student.getEmail());
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(CREATE_SQL, params, keyHolder);
		student.setStudID(keyHolder.getKey().longValue());

	}

	@Override
	public List<Student> getStudent() {

		return jdbcTemplate.query(FIND_ALL_SQL, new HashMap<String, Object>(),
				studentRowMapper);
	}

	@Override
	public List<Student> getStudentByEmail(String email) {
		notNull(email, "email can't be null");
		SqlParameterSource params = new MapSqlParameterSource("email", "%"
				+ email + "%");
		return jdbcTemplate.query(FIND_ALL_BY_EMAIL_LIKE_SQL, params,
				studentRowMapper);
	}

	@Override
	public Student getStudentbyID(Long studID) {

		notNull(studID, "studID can't be null");
		SqlParameterSource params = new MapSqlParameterSource("studID", studID);
		return jdbcTemplate.queryForObject(FIND_ONE_SQL, params,
				studentRowMapper);
	}

	@Override
	public void updateStudent(Student student) {
		// TODO Auto-generated method stub

		notNull(student, "student can't be null");
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("studID", student.getStudID())
				.addValue("firstName", student.getFirstName())
				.addValue("middleInitial", student.getMiddleInitial())
				.addValue("lastName", student.getLastName())
				.addValue("email", student.getEmail());
		jdbcTemplate.update(UPDATE_SQL, params);

	}

	@Override
	public void deleteStudent(Long studID) {
		// TODO Auto-generated method stub
		notNull(studID, "studID can't be null");
		jdbcTemplate.update(DELETE_SQL, new MapSqlParameterSource("studID",
				studID));

	}

}
