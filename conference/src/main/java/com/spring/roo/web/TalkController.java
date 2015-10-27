package com.spring.roo.web;
import com.spring.roo.domain.Talk;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/talks")
@Controller
@RooWebScaffold(path = "talks", formBackingObject = Talk.class)
public class TalkController {
}
