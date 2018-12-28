package com.cglee079.changoos.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.service.BoardImageService;

@Controller
public class BoardImageController {
	@Autowired
	private BoardImageService boardImageService;
	
	@RequestMapping("/mgnt/board/image/upload")
	public String imageUpload(Model model, String editor) {
		model.addAttribute("editor", editor);
		return "popup/popup_imageupload";
	}
	
	@ResponseBody
	@RequestMapping("/mgnt/board/image/upload.do")
	public String imageDoUpload(Model model, MultipartFile image) throws IllegalStateException, IOException {
		String pathname= boardImageService.saveImage(image);
		
		JSONObject result = new JSONObject();
		result.put("pathname", pathname);
		
		return result.toString();
	}
	
		
	@ResponseBody
	@RequestMapping(value = "/mgnt/board/image/paste-upload.do")
	public String imageDoPasteUpload(HttpServletRequest request, String base64) throws IllegalStateException, IOException {
		String pathname = boardImageService.saveBase64(base64);
		
		JSONObject result = new JSONObject();
		result.put("filename", pathname);
		result.put("pathname", pathname);
		
		return result.toString();
	}
	
}
