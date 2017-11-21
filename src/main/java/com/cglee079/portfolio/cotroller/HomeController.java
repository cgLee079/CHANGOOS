package com.cglee079.portfolio.cotroller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cglee079.portfolio.model.ItemVo;
import com.cglee079.portfolio.service.ItemService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private ItemService itemService;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/")
	public String home(Locale locale, Model model) {
		List<ItemVo> items = itemService.list();
		model.addAttribute("items", items);
		return "home";
	}



}
