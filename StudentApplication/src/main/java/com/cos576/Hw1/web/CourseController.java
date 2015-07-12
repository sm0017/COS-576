package com.cos576.Hw1.web;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.ResourceAccessException;

import com.cos576.Hw1.model.Course;

import com.cos576.Hw1.service.CourseService;



@Controller
@RequestMapping(value = "/courses")
public class CourseController {
	private static Logger log1 = LoggerFactory.getLogger(CourseController.class);
	
	
	@Inject private CourseService courseService;

	@Value("#{viewNames.courseList}") 
	private String courseListViewNames;
	
	@Value("#{viewNames.courseForm}")
	private String courseFormViewNames;
	
	@Value("#{viewNames.createCourseSuccess}") 
	private String createCourseSuccessViewNames;
	
	@Value("#{viewNames.updateCourseSuccess}") 
	private String updateCourseSuccessViewNames;
	
	@Value("#{viewNames.deleteCourseSuccess}") 
	private String deleteCourseSuccessViewNames;
	
	@Value("#{viewNames.courseSerp}")
	private String courseSerpViewNames;
	
	@InitBinder
	public void initBinder(WebDataBinder binder)
	{
	binder.setAllowedFields(new String[] {
			 "courseName", "facultyFirstName", "facultyLastName", 
				"facultyEmail" , "courseStartDate", "courseEndDate" 
			});
		

		
	}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String createCourseForm(HttpServletRequest req, Model model) {
		prepareNewCourseForm(req);
		model.addAttribute(new Course());
		System.out.println(courseFormViewNames + "Smita");
	   return courseFormViewNames;
		
	}


	@RequestMapping(value = "", method = RequestMethod.POST)
	public String createCourse(
			HttpServletRequest req,
			HttpServletResponse res,
			@ModelAttribute @Valid Course course,
			BindingResult result) {
		System.out.println(course);
		if (!result.hasErrors()) {
			courseService.createCourse(course);
			
			
			res.setStatus(HttpServletResponse.SC_CREATED);
			String location = req.getRequestURL() + "/" + course.getCourseID();
		    log1.debug("Setting Location={}", location);
			res.setHeader("Location", location);
			
			return createCourseSuccessViewNames;
		
		} else {
			System.out.println("smita : " + result );
			prepareNewCourseForm(req);
			result.reject("global.error");
		   return courseFormViewNames;
		
			
		}
	}


	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getCourse(Model model) {
		model.addAttribute(courseService.getCourse());
		return courseListViewNames;
		
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String getCoursebyName(@RequestParam("courseName") String courseName, Model model) {
		System.out.println("smita" +courseName);
		model.addAttribute(courseService.getCoursebyName(courseName));
	return courseSerpViewNames;
		
	}


	

	@RequestMapping(value = "/{courseID}", method = RequestMethod.PUT)
	public String updateCourse(
			HttpServletRequest req,
			@PathVariable("courseID") Long courseID,
			@ModelAttribute @Valid Course course,
			BindingResult result) {
		
		course.setCourseID(courseID);
		
		if (!result.hasErrors()) {
			courseService.updateCourse(course);
			return updateCourseSuccessViewNames;
			
		} else {
			prepareExistingStudentForm(req, courseID);
			result.reject("global.error");

			return courseFormViewNames;
		
			
		}
	}
	

	@RequestMapping(value = "/{courseID}", method = RequestMethod.DELETE)
	public String deleteCourse(@PathVariable("courseID") long courseID) {
		System.out.println("value of deleted course id" +courseID);
		courseService.deleteCourse(courseID);
		return deleteCourseSuccessViewNames;

	}


	// =================================================================================================================
	// Helper methods
	// =================================================================================================================

	private void prepareNewCourseForm(HttpServletRequest req) {
		setActionAndMethod(req, "/courses.html", "POST");
	}

	private void prepareExistingStudentForm(HttpServletRequest req, long courseID) {
		setActionAndMethod(req, "/courses/" + courseID + ".html", "PUT");
	}

	private void setActionAndMethod(HttpServletRequest req, String action, String method) {
		req.setAttribute("action", action);
		req.setAttribute("method", method);
	}
	
	
}
