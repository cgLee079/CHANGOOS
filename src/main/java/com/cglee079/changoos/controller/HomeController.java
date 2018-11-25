package com.cglee079.changoos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cglee079.changoos.service.CommonCodeService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private CommonCodeService commonCodeService;
	
	@RequestMapping(value = "/")
	public String home(Model model) {
		String home001 = commonCodeService.get("HOME", "001"); // 메인홈 메시지
		model.addAttribute("home001", home001);
		return "main_home";
	}
	
}
