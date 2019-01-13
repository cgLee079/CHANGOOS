package com.cglee079.changoos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cglee079.changoos.service.CommonStrService;

@Controller
public class IntroduceController {
	@Autowired
	private CommonStrService commonStringService;
	

	@RequestMapping(value = "/introduce")
	public String introduce(Model model) {
		String intro001 = commonStringService.get("INTRO", "001"); // 자기소개
		String intro002 = commonStringService.get("INTRO", "002"); // 이력내용
		
		model.addAttribute("intro001", intro001);
		model.addAttribute("intro002", intro002);
		
		return "introduce/introduce_view";
	}
}
