package com.cglee079.portfolio.cotroller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.portfolio.model.FileBean;
import com.cglee079.portfolio.model.Item;
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
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		List<Item> items = itemService.list();
		System.out.println(items);
		return "home";
	}

	@RequestMapping(value = "/item/upload", method = RequestMethod.GET)
	public String itemUpload(Locale locale, Model model) {
		return "itemUpload";
	}

	@RequestMapping(value = "/item/imgUpload.do", method = RequestMethod.POST)
	public void itemImgUpload(HttpServletRequest requesst, 
			@RequestParam("upload")MultipartFile file, String CKEditorFuncNum) {
		System.out.println(CKEditorFuncNum);
		System.out.println(file);
	}

}
