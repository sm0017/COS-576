package com.hw2.spring.cos576.web;

import javax.inject.Inject;
import javax.validation.Valid;


import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hw2.spring.cos576.domain.CourseAccount;
import com.hw2.spring.cos576.service.CourseService;

@Controller
@RequestMapping("/courses")
public class CourseAccountController {
	private static final String VN_REG_FORM = "users/courseForm";
	private static final String VN_REG_OK = "redirect:users/courseregistration_ok.html";
	@Inject private CourseService courseService; 

															
	@InitBinder
	
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
				"coursename", "facultyFirstName", "facultyLastName", "startDate", "endDate"
						
			
			
		});
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	
}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		model.addAttribute("courseAccount", new CourseForm());
		System.out.println(" Smita" +VN_REG_FORM);
		return VN_REG_FORM;
	}
	
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String postRegistrationForm(
			@ModelAttribute("courseAccount") @Valid CourseForm form1,
			BindingResult result) {
		
		System.out.println("errors are" +result);
		courseService.addCourses(toAccount(form1),result);
		return (result.hasErrors() ? VN_REG_FORM : VN_REG_OK);
	}
	
	
	
	
	private static CourseAccount toAccount(CourseForm courseForm) {
		CourseAccount courseAccount = new CourseAccount();
		courseAccount.setCoursename(courseForm.getCoursename());
		courseAccount.setFacultyFirstName(courseForm.getFacultyFirstName());
		courseAccount.setFacultyLastName(courseForm.getFacultyLastName());
		courseAccount.setStartDate(courseForm.getStartDate());
		courseAccount.setEndDate(courseForm.getEndDate());
		
		return courseAccount;
	}
	
}

