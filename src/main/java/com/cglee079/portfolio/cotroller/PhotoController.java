package com.cglee079.portfolio.cotroller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.portfolio.model.PhotoVo;
import com.cglee079.portfolio.service.PhotoService;
import com.cglee079.portfolio.util.ImageManager;
import com.drew.imaging.ImageProcessingException;
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
	public String photoDoUpload(HttpServletRequest request, PhotoVo photo, MultipartFile imageFile) throws IllegalStateException, IOException, ImageProcessingException {
		HttpSession session = request.getSession();
		String rootPath 	= session.getServletContext().getRealPath("");
		String imgPath		= "/resources/image/photo/";
		String snapshtPath 	= "/resources/image/photo/snapsht/";
		String imgName		= "photo_" + photo.getName() + "_";
		String snapshtName	= "photo_snapsht_" + photo.getName() + "_";
		
		if(imageFile.getSize() != 0){
			//image save
			imgName += imageFile.getOriginalFilename();
			File file = new File(rootPath + imgPath + imgName);
			imageFile.transferTo(file);
			photo.setImage(imgPath + imgName);
			
			HashMap<String, String> metadata = ImageManager.getImageMetaData(file);
			photo.setDate(metadata.get("Date/Time"));
			photo.setDevice(metadata.get("Model"));
			
			//snapsht save
			snapshtName += imageFile.getOriginalFilename();
			BufferedImage shapshtImg = ImageManager.getScaledImage(file, 100); 
			File snapshtfile = new File(rootPath + snapshtPath + snapshtName);
			ImageIO.write(shapshtImg, "jpg", snapshtfile);
			photo.setSnapsht(snapshtPath + snapshtName);
		} else{
			photo.setImage(imgPath + "default.jpg");
			photo.setSnapsht(snapshtPath + "default.jpg");
		}
		
		photoService.insert(photo);
		
		return "redirect:" + "/admin/photo/list";
	}
	
	@RequestMapping(value = "/admin/photo/upload.do", params = "seq")
	public String photoDoModify(HttpServletRequest request, PhotoVo photo, MultipartFile imageFile) throws IllegalStateException, IOException, ImageProcessingException{
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String imgPath	= "/resources/image/photo/";
		String snapshtPath 	= "/resources/image/photo/snapsht/";
		String imgName		= "photo_" + photo.getName() + "_";
		String snapshtName	= "photo_" + photo.getName() + "_snapsht";
		
		if(imageFile.getSize() != 0){
			File existFile = null;
			
			//image delete
			existFile = new File (rootPath + photo.getImage());
			if(existFile.exists()){ existFile.delete(); }
			
			//snapsht delete
			existFile = new File (rootPath + photo.getSnapsht());
			if(existFile.exists()){ existFile.delete(); }
			
			//image save
			imgName += imageFile.getOriginalFilename();
			File file = new File(rootPath + imgPath + imgName);
			imageFile.transferTo(file);
			photo.setImage(imgPath + imgName);
			
			HashMap<String, String> metadata = ImageManager.getImageMetaData(file);
			photo.setDate(metadata.get("Date/Time"));
			photo.setDevice(metadata.get("Model"));
			
			//snapsht save
			BufferedImage shapshtImg = ImageManager.getScaledImage(file, 100); 
			File snapshtfile = new File(rootPath + snapshtPath + snapshtName);
			ImageIO.write(shapshtImg, "jpg", snapshtfile);
			photo.setSnapsht(snapshtPath + snapshtName + ".jpg");
		} 
		
		photoService.update(photo);
		
		return "redirect:" + "/admin/photo/list";
	}
	
}
