package com.cglee079.changoos.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.service.ProjectService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private ProjectService projectService;

	@RequestMapping(value = "/")
	public String home(Model model) {
		List<ProjectVo> projects = projectService.list();
		model.addAttribute("projects", projects);
		return "main_home";
	}
	
}
