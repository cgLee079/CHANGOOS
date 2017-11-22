package com.cglee079.portfolio.cotroller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.portfolio.model.ItemVo;
import com.cglee079.portfolio.model.PhotoVo;
import com.cglee079.portfolio.service.PhotoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PhotoController {
	
	@Autowired
	private PhotoService photoService;
	
	@RequestMapping(value = "/photo")
	public String photoHome(Model model) {
		List<PhotoVo> photos = photoService.list();
		model.addAttribute("photos", photos);
		return "photo/photo_view";
	}
	
	@RequestMapping(value = "admin/photo/list")
	public String photoList(Model model) {
		List<PhotoVo> photos = photoService.list();
		model.addAttribute("photos", photos);
		return "photo/photo_list";
	}
	
	@ResponseBody
	@RequestMapping(value = "/photo/view.do")
	public String photoDoView(int seq) throws JsonProcessingException {
		PhotoVo photo = photoService.get(seq);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(photo);
	}
	
	@RequestMapping(value = "admin/photo/delete.do")
	public String photoDelete(int seq) {
		boolean result = photoService.delete(seq);
		return "redirect:" + "/admin/photo/list";
	}
	
	@RequestMapping(value = "admin/photo/upload", params = "!seq")
	public String photoUpload() {
		return "photo/photo_upload";
	}
	
	@RequestMapping(value = "/admin/photo/upload", params = "seq")
	public String photoModify(Model model, int seq) {
		PhotoVo photo = photoService.get(seq);
		model.addAttribute("photo", photo);
		return "photo/photo_upload";
	}
	
	@RequestMapping(value = "admin/photo/upload.do", params = "!seq")
	public String photoDoUpload(HttpServletRequest request, PhotoVo photo, MultipartFile imageFile) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String imgPath	= "/resources/image/photo/";
		String filename	= "photo_" + photo.getName() + "_";
		
		if(imageFile.getSize() != 0){
			filename += imageFile.getOriginalFilename();
			File file = new File(rootPath + imgPath + filename);
			imageFile.transferTo(file);
			photo.setImage(imgPath + filename);
		} else{
			photo.setImage(imgPath + "default.jpg");
		}
		
		photoService.insert(photo);
		
		return "redirect:" + "/admin/photo/list";
	}
	
	@RequestMapping(value = "/admin/photo/upload.do", params = "seq")
	public String photoDoModify(HttpServletRequest request, PhotoVo photo, MultipartFile imageFile) throws IllegalStateException, IOException{
		System.out.println("dddd");
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String imgPath	= "/resources/image/photo/";
		String filename	= "photo_" + photo.getName() + "_";
		
		if(imageFile.getSize() != 0){
			File existFile = new File (rootPath + photo.getImage());
			if(existFile.exists()){
				existFile.delete();
			}
			
			filename += imageFile.getOriginalFilename();
			File file = new File(rootPath + imgPath + filename);
			imageFile.transferTo(file);
			
			photo.setImage(imgPath + filename);
		} 
		
		photoService.update(photo);
		
		return "redirect:" + "/admin/photo/list";
	}
	
}
