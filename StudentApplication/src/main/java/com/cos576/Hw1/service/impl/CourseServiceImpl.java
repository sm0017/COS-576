package com.cos576.Hw1.service.impl;

import static org.springframework.util.Assert.notNull;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import org.springframework.cglib.transform.impl.AddDelegateTransformer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.cos576.Hw1.model.Course;
import com.cos576.Hw1.service.CourseService;

@Service
public class CourseServiceImpl implements CourseService {

	@Inject
	private NamedParameterJdbcOperations jdbcTemplate;
	@Inject
	private CourseRowMapper courseRowMapper;
	private static final String CREATE_SQL = "insert into course"
			+ " (course_Name, courseStartDate, courseEndDate, "
			+ "faculty_LastName, faculty_FirstName, faculty_Email) "
			+ "values (:courseName, :courseStartDate, :courseEndDate, "
			+ " :facultyLastName, :facultyFirstName, :facultyEmail)";
	private static final String FIND_ALL_SQL = "select course_ID, course_Name,"
			+ " courseStartDate, courseEndDate, faculty_LastName, faculty_FirstName, faculty_Email from course ";
	private static final String FIND_ONE_BY_COURSE_NAME_LIKE_SQL = "select course_ID, course_Name,  courseStartDate , courseEndDate,"
			+ "faculty_LastName, faculty_FirstName, faculty_Email from course "
			+ "where course_Name =:courseName ";
	private static final String UPDATE_SQL = "update course set  course_Name = :courseName, "
			+ "courseStartDate = :courseStartDate, courseEndDate = :courseStartDate, "
			+ "faculty_LastName = :facultyLastName, faculty_FirstName = :facultyFirstName, "
			+"faculty_Email= :facultyEmail,"
			+ "where courseID = :courseID";
	private static final String DELETE_SQL = "delete from course where course_ID = :courseID";

	@Override
	public void createCourse(Course course) {
		// TODO Auto-generated method stub

		System.out.println("I am in courseserviceImpl");
		notNull(course, "course can't be null");
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("courseID", course.getCourseID())
				.addValue("courseName", course.getCourseName())
				.addValue("courseStartDate", course.getCourseStartDate())
				.addValue("courseEndDate", course.getCourseEndDate())
				.addValue("facultyLastName", course.getFacultyFirstName())
				.addValue("facultyFirstName", course.getFacultyFirstName())
				.addValue("facultyEmail", course.getFacultyEmail());
		jdbcTemplate.update(CREATE_SQL, params);

	}

	@Override
	public List<Course> getCourse() {
		// TODO Auto-generated method stub

		return jdbcTemplate.query(FIND_ALL_SQL, new HashMap<String, Object>(),
				courseRowMapper);

	}

	@Override
	public Course getCoursebyName(String courseName) {
		// TODO Auto-generated method stub
		//notNull(courseName, "courseName can't be null");
		SqlParameterSource params = new MapSqlParameterSource("courseName", "%"
				+ courseName + "%");
		
		
		
		return jdbcTemplate.queryForObject(FIND_ONE_BY_COURSE_NAME_LIKE_SQL,
				params, courseRowMapper);

	}

	@Override
	public void updateCourse(Course course) {
		// TODO Auto-generated method stub

		notNull(course, "course can't be null");
		SqlParameterSource params = new MapSqlParameterSource()
				.addValue("courseID", course.getCourseID())
				.addValue("courseName", course.getCourseName())
				.addValue("courseStartDate", course.getCourseStartDate())
				.addValue("courseEndDate", course.getCourseEndDate())
				.addValue("facultyLastName", course.getFacultyFirstName())
				.addValue("facultyFirstName", course.getFacultyFirstName())
				.addValue("facultyEmail", course.getFacultyEmail());
		jdbcTemplate.update(UPDATE_SQL, params);

	}

	@Override
	public void deleteCourse(Long courseID) {
		// TODO Auto-generated method stub

		notNull(courseID, "courseID can't be null");
System.out.println ("I am in delete student smita");
		
		jdbcTemplate.update(DELETE_SQL, new MapSqlParameterSource("courseID",
				courseID));

	}

}
