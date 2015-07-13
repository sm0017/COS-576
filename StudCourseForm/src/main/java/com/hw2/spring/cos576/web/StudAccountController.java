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

import com.hw2.spring.cos576.domain.StudAccount;
import com.hw2.spring.cos576.service.StudAccountService;

@Controller
@RequestMapping("/users")
public class StudAccountController {
	private static final String VN_REG_FORM = "users/registrationForm";
	private static final String VN_REG_OK = "redirect:users/registration_ok.html";
	@Inject private StudAccountService studAccountService; 

	@InitBinder
	
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { 
			"username", "password", "confirmPassword",
			"firstName", "lastName", "email", "marketingOk", "acceptTerms"
		});
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	
}
	
	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String getRegistrationForm(Model model) {
		model.addAttribute("studaccount", new StudAccountForm());
		return VN_REG_FORM;
	}
	
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public String postRegistrationForm(
			@ModelAttribute("studaccount") @Valid StudAccountForm form,
			BindingResult result) {
		
		convertPasswordError(result);
		studAccountService.registerStudAccount(toAccount(form), form.getPassword(), result);
		return (result.hasErrors() ? VN_REG_FORM : VN_REG_OK);
	}
	
	
	
	private static void convertPasswordError(BindingResult result) {
		// Map class-level Hibernate error message to field-level Spring error message.
		for (ObjectError error : result.getGlobalErrors()) {
			String msg = error.getDefaultMessage();
			if ("studaccount.password.mismatch.message".equals(msg)) {
				// Don't show if there's already some other error message.
				if (!result.hasFieldErrors("password")) {
					result.rejectValue("password", "error.mismatch");
				}
			}
		}
		
	}
	
	private static StudAccount toAccount(StudAccountForm studAccountForm) {
		StudAccount studaccount = new StudAccount();
		studaccount.setUsername(studAccountForm.getUsername());
		studaccount.setFirstName(studAccountForm.getFirstName());
		studaccount.setLastName(studAccountForm.getLastName());
		studaccount.setEmail(studAccountForm.getEmail());
		studaccount.setMarketingOk(studAccountForm.isMarketingOk());
		studaccount.setAcceptTerms(studAccountForm.getAcceptTerms());
		studaccount.setEnabled(true);
		return studaccount;
	}
	
}

