package com.dw.roo.catalog.web;
import com.dw.roo.catalog.domain.Course;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/courses")
@Controller
@RooWebScaffold(path = "courses", formBackingObject = Course.class)
public class CourseController {
}
