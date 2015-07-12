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

import com.cos576.Hw1.model.Student;
import com.cos576.Hw1.service.StudentService;

@Controller
@RequestMapping(value = "/students")
public class StudentController
{

	private static Logger log = LoggerFactory.getLogger(StudentController.class);
	
@Inject private StudentService studentService;
@Value("#{viewNames.studentList}") private String studentListViewNames;
@Value("#{viewNames.studentForm}") private String studentFormViewNames;
@Value("#{viewNames.createStudentSuccess}") private String createStudentSuccessViewNames;
@Value("#{viewNames.updateStudentSuccess}") private String updateStudentSuccessViewNames;
@Value("#{viewNames.deleteStudentSuccess}") private String deleteStudentSuccessViewNames;
@Value("#{viewNames.studentSerp}")private String studentSerpViewNames;

@InitBinder
public void initBinder(WebDataBinder binder)
{
	
	binder.setAllowedFields(new String[] {
			"firstName", "middleInitial", "lastName", "email"
		});

	
}

@RequestMapping(value = "/new", method = RequestMethod.GET)
public String createContactForm(HttpServletRequest req, Model model) {
	prepareNewStudentForm(req);
	model.addAttribute(new Student());
   return studentFormViewNames;
	
}


@RequestMapping(value = "", method = RequestMethod.POST)
public String createStudent(
		HttpServletRequest req,
		HttpServletResponse res,
		@ModelAttribute @Valid Student student,
		BindingResult result) {
	
	if (!result.hasErrors()) {
		studentService.createStudent(student);
		
		
		res.setStatus(HttpServletResponse.SC_CREATED);
		String location = req.getRequestURL() + "/" + student.getStudID();
	    log.debug("Setting Location={}", location);
		res.setHeader("Location", location);
		
		return createStudentSuccessViewNames;
	
	} else {
		prepareNewStudentForm(req);
		result.reject("global.error");
	   return studentFormViewNames;
	
		
	}
}


@RequestMapping(value = "", method = RequestMethod.GET)
public String getContacts(Model model) {
	model.addAttribute(studentService.getStudent());
	return studentListViewNames;
	
}

@RequestMapping(value = "/search", method = RequestMethod.GET)
public String searchByEmail(@RequestParam("email") String email, Model model) {
	model.addAttribute(studentService.getStudentByEmail(email));
return studentSerpViewNames;
	
}


@RequestMapping(value = "/{studID}", method = RequestMethod.GET)
public String getContact(
		HttpServletRequest req,
		@PathVariable("studID") long studID,
		Model model) {
	
	Student student = studentService.getStudentbyID(studID);
	if (student != null) {
		prepareExistingStudentForm(req, studID);
		model.addAttribute(studentService.getStudentbyID(studID));
		return studentFormViewNames;
	
	} else {
		throw new ResourceAccessException("No such student: " + studID);
	}
}

@RequestMapping(value = "/{studID}", method = RequestMethod.PUT)
public String updateStudent(
		HttpServletRequest req,
		@PathVariable("studID") Long studID,
		@ModelAttribute @Valid Student student,
		BindingResult result) {
	
	student.setStudID(studID);
	
	if (!result.hasErrors()) {
		studentService.updateStudent(student);
		return updateStudentSuccessViewNames;
		
		
	} else {
		prepareExistingStudentForm(req, studID);
		result.reject("global.error");
		return studentFormViewNames;
	
		
	}
}

@RequestMapping(value = "/{studID}", method = RequestMethod.DELETE)
public String deleteStudent(@PathVariable("studID") long studID) {
	studentService.deleteStudent(studID);
	return deleteStudentSuccessViewNames;

}


// =================================================================================================================
// Helper methods
// =================================================================================================================

private void prepareNewStudentForm(HttpServletRequest req) {
	setActionAndMethod(req, "/students.html", "POST");
}

private void prepareExistingStudentForm(HttpServletRequest req, long studID) {
	setActionAndMethod(req, "/students/" + studID + ".html", "PUT");
}

private void setActionAndMethod(HttpServletRequest req, String action, String method) {
	req.setAttribute("action", action);
	req.setAttribute("method", method);
}
}




