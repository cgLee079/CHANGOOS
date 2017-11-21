package com.cglee079.portfolio.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cglee079.portfolio.service.ItemService;

@Controller
public class AdminController {
	
	@RequestMapping("/login")
	public String adminLogin(){
		return "admin_login";
	}
}
