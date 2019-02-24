package com.cglee079.changoos.controller;

import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorHandleController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Resource(name = "message")
	Properties properties;
	
	@RequestMapping(value = "/error/{code}")
	public String handleHttpErrorStatus(Model model, @PathVariable String code) {
		 logger.info(code);
		 
		 model.addAttribute("message", properties.getProperty("msg.error.status." + code));
		 
		 return "error/default";
	}
	
	
}
