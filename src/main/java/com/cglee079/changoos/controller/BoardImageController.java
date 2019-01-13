package com.cglee079.changoos.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.service.BoardImageService;

@Controller
public class BoardImageController {
	@Autowired
	private BoardImageService boardImageService;
	
	@RequestMapping(value = "/board/post/image", method = RequestMethod.GET)
	public String imageUpload(Model model, String editor) {
		model.addAttribute("editor", editor);
		return "popup/popup_imageupload";
	}
	
	@ResponseBody
	@RequestMapping(value = "/board/post/image", method = RequestMethod.POST, consumes = "multipart/form-data")
	public String imageDoUpload(HttpSession session, MultipartFile image) throws IOException{
		String pathname = boardImageService.saveImage((String)session.getAttribute("tempDirId"), image);
		
		JSONObject result = new JSONObject();
		result.put("pathname", pathname);
		
		return result.toString();
	}
	
		
	@ResponseBody
	@RequestMapping(value = "/board/post/image", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public String imageDoPasteUpload(HttpSession session, String base64) throws IOException{
		String pathname = boardImageService.saveBase64((String)session.getAttribute("tempDirId"), base64);
		
		JSONObject result = new JSONObject();
		result.put("pathname", pathname);
		
		return result.toString();
	}
	
}
