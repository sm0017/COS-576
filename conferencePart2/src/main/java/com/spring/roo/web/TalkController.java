package com.spring.roo.web;
import com.spring.roo.domain.Talk;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;

@RequestMapping("/talks")
@Controller
@RooWebScaffold(path = "talks", formBackingObject = Talk.class)
public class TalkController {

    @Autowired
    private transient MailSender mailTemplate;

    public void sendMessage(String mailFrom, String subject, String mailTo, String message) {
        org.springframework.mail.SimpleMailMessage mailMessage = new org.springframework.mail.SimpleMailMessage();
        mailMessage.setFrom(mailFrom);
        mailMessage.setSubject(subject);
        mailMessage.setTo(mailTo);
        mailMessage.setText(message);
        mailTemplate.send(mailMessage);
    }
    
    @RequestMapping(method = RequestMethod.POST)
	public String create(@Valid Talk talk, BindingResult result, Model model,
			HttpServletRequest request) {
		if (result.hasErrors()) {
			model.addAttribute("talk", talk);
			return "talks/create";
		}
		talk.persist();
		sendMessage("purvatm@gmail.com", "Your talk is created",
				talk.getSpeaker().getEmail(), 
"Congrats your talk is created");
		return "redirect:/talks/"
				+ encodeUrlPathSegment(talk.getId().toString(), request);
	}

	private String encodeUrlPathSegment(String pathSegment,
			HttpServletRequest request) {
		String enc = request.getCharacterEncoding();
		if (enc == null) {
			enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
		}
		try {
			pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
		} catch (UnsupportedEncodingException uee) {
		}
		return pathSegment;
	}
}
