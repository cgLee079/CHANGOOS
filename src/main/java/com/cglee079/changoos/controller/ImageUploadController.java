package com.cglee079.changoos.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.service.ImageUploadService;
import com.cglee079.changoos.util.PathHandler;

@Controller
public class ImageUploadController {
	@Autowired
	private ImageUploadService imageUploadService;
	
	@RequestMapping("/mgnt/image/upload")
	public String projectImgUpload(Model model, String editor) {
		model.addAttribute("editor", editor);
		return "popup/popup_imageupload";
	}
	
	@ResponseBody
	@RequestMapping("/mgnt/image/upload.do")
	public String projectImgUpload(Model model, String base64, String filename) throws IllegalStateException, IOException {
		String pathname= imageUploadService.saveContentImage(filename, base64);
		
		JSONObject result = new JSONObject();
		result.put("path", Path.TEMP_IMAGE_PATH);
		result.put("filename", filename);
		result.put("pathname", pathname);
		
		return result.toString();
	}
	
		
	@ResponseBody
	@RequestMapping(value = "/mgnt/image/paste-upload.do")
	public String blogDoImgUpload(HttpServletRequest request, String base64) throws IllegalStateException, IOException {
		String pathname = imageUploadService.saveContentImage(base64);
		
		JSONObject result = new JSONObject();
		result.put("path", Path.TEMP_IMAGE_PATH);
		result.put("filename", pathname);
		result.put("pathname", pathname);
		
		request.setAttribute("path", Path.TEMP_IMAGE_PATH);
		return result.toString();
	}
	
}
