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

import com.cglee079.changoos.util.ContentImageManager;

@Controller
public class CommonController {
	
	@RequestMapping("/mgnt/img-upload.do")
	public String projectImgUpload(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("upload")MultipartFile multiFile, String CKEditorFuncNum) throws IllegalStateException, IOException {
		
		String path = ContentImageManager.saveContentImage(multiFile);
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		model.addAttribute("path", path);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
		
		return "common/ckeditor_imgupload";
	}
	
	@ResponseBody
	@RequestMapping(value = "/mgnt/img-upload-base64.do")
	public String blogDoImgUpload(HttpServletRequest request, String base64) throws IllegalStateException, IOException {
		String path = ContentImageManager.saveContentImage(base64);
		
		JSONObject result = new JSONObject();
		result.put("path", path);
		request.setAttribute("path", path);
		return result.toString();
	}
	
}
