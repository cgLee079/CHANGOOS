package com.cglee079.changoos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {
	
	@RequestMapping("/login")
	public String adminLogin(){
		return "main_login";
	}
}
