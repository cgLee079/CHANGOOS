package com.cglee079.portfolio.cotroller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.drew.metadata.MetadataException;
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
	public String photoDelete(HttpSession session, int seq) {
		String rootPath = session.getServletContext().getRealPath("");
		PhotoVo photo = photoService.get(seq);
		deleteFile(rootPath, photo.getImage());
		deleteFile(rootPath, photo.getSnapsht());
		
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
	public String photoDoUpload(HttpServletRequest request, PhotoVo photo, MultipartFile imageFile) throws IllegalStateException, IOException, ImageProcessingException, MetadataException {
		HttpSession session = request.getSession();
		String rootPath 	= session.getServletContext().getRealPath("");
		String imgPath		= "/resources/image/photo/";
		String snapshtPath 	= "/resources/image/photo/snapsht/";
		String timeStamp	= new SimpleDateFormat("YYMMdd_HHmmss").format(new Date());
		String imgName		= "photo_" + timeStamp + "_" + photo.getName();
		String snapshtName	= "photo_snapsht_" + timeStamp + "_" + photo.getName();
		
		if(imageFile.getSize() != 0){
			//naming
			String imgExt = ImageManager.getExt(imageFile.getOriginalFilename());
			imgName 	+= "." + imgExt;
			snapshtName += "." + imgExt;
			
			//multipartfile save;
			File photofile = new File(rootPath + imgPath + imgName);
			imageFile.transferTo(photofile);
			HashMap<String, String> metadata = ImageManager.getImageMetaData(photofile);
			photo.setDate(metadata.get("Date/Time"));
			photo.setDevice(metadata.get("Model"));
			
			//photo resize , rotate;
			int orientation = ImageManager.getOrientation(photofile);
			BufferedImage photoImg = ImageManager.getLowScaledImage(photofile, 720 , imgExt); 
			if(orientation != 1){
				photoImg =  ImageManager.rotateImageForMobile(photoImg, orientation);
			}
			ImageIO.write(photoImg, imgExt, photofile);
			
			//make snapsht
			BufferedImage shapshtImg = ImageManager.getLowScaledImage(photofile, 100, imgExt); 
			if(orientation != 1 ){
				shapshtImg = ImageManager.rotateImageForMobile(shapshtImg, orientation);
			}
			File snapshtfile = new File(rootPath + snapshtPath + snapshtName);
			ImageIO.write(shapshtImg, imgExt, snapshtfile);
			
			photo.setSnapsht(snapshtPath + snapshtName);
			photo.setImage(imgPath + imgName);
		} else{
			photo.setImage(imgPath + "default.jpg");
			photo.setSnapsht(snapshtPath + "default.jpg");
		}
		
		photoService.insert(photo);
		
		return "redirect:" + "/admin/photo/list";
	}
	
	@RequestMapping(value = "/admin/photo/upload.do", params = "seq")
	public String photoDoModify(HttpServletRequest request, PhotoVo photo, MultipartFile imageFile) throws IllegalStateException, IOException, ImageProcessingException, MetadataException{
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String imgPath		= "/resources/image/photo/";
		String snapshtPath 	= "/resources/image/photo/snapsht/";
		String timeStamp	= new SimpleDateFormat("YYMMdd_HHmmss").format(new Date());
		String imgName		= "photo_" + timeStamp + "_" + photo.getName();
		String snapshtName	= "photo_snapsht_" + timeStamp + "_" + photo.getName();
		
		if(imageFile.getSize() != 0){
			deleteFile(rootPath, photo.getImage());
			deleteFile(rootPath, photo.getSnapsht());
			
			//naming
			String imgExt = ImageManager.getExt(imageFile.getOriginalFilename());
			imgName 	+= "." + imgExt;
			snapshtName += "." + imgExt;
			
			//multipartfile save;
			File photofile = new File(rootPath + imgPath + imgName);
			imageFile.transferTo(photofile);
			HashMap<String, String> metadata = ImageManager.getImageMetaData(photofile);
			photo.setDate(metadata.get("Date/Time"));
			photo.setDevice(metadata.get("Model"));
			
			//photo resize , rotate;
			int orientation = ImageManager.getOrientation(photofile);
			BufferedImage photoImg = ImageManager.getLowScaledImage(photofile, 720 , imgExt);
			if(orientation != 1){
				photoImg =  ImageManager.rotateImageForMobile(photoImg, orientation);
			}
			ImageIO.write(photoImg, imgExt, photofile);
			
			//make snapsht
			BufferedImage shapshtImg = ImageManager.getLowScaledImage(photofile, 100, imgExt); 
			if(orientation != 1 ){
				shapshtImg = ImageManager.rotateImageForMobile(shapshtImg, orientation);
			}
			File snapshtfile = new File(rootPath + snapshtPath + snapshtName);
			ImageIO.write(shapshtImg, imgExt, snapshtfile);
			
			photo.setSnapsht(snapshtPath + snapshtName);
			photo.setImage(imgPath + imgName);
		} 
		
		photoService.update(photo);
		
		return "redirect:" + "/admin/photo/list";
	}
	
	private void deleteFile(String rootPath, String subPath){
		File existFile = null;
		existFile = new File (rootPath + subPath);
		if(existFile.exists()){ existFile.delete(); }
	}
	
}
