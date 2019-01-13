package com.cglee079.changoos.controller;

import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandleController {
	
	Logger logger = Logger.getLogger(this.getClass().getName());
	
	@Resource(name = "message")
	Properties properties;
	
	@RequestMapping(value = "/error/401")
	public String handle401(HttpServletRequest request, Model model) {
		 logger.info("401");
		 
		 model.addAttribute("message", properties.getProperty("msg.error.status.401"));
		 return "error/default";
	}
	
	@RequestMapping(value = "/error/404")
	public String handle404(Model model) {
		 logger.info("404");
		 
		 model.addAttribute("message", properties.getProperty("msg.error.status.404"));
		 
		 return "error/default";
	}
	
	@RequestMapping(value = "/error/500")
	public String handle500(Model model) {
		 logger.info("500");
		 
		 model.addAttribute("message", properties.getProperty("msg.error.status.500"));
		 
		 return "error/default";
	}
	
	
	
}
