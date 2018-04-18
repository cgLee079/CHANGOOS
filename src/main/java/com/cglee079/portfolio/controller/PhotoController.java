package com.cglee079.portfolio.controller;

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
import com.cglee079.portfolio.util.Formatter;
import com.cglee079.portfolio.util.ImageManager;
import com.cglee079.portfolio.util.TimeStamper;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PhotoController {
	@Autowired
	private PhotoService photoService;
	
	/** 사진 페이지로 이동 **/
	@RequestMapping(value = "/photo")
	public String photoHome(Model model) {
		List<PhotoVo> photos = photoService.list();
		model.addAttribute("photos", photos);
		return "photo/photo_view";
	}
	
	/** 사진 관리 페이지로 이동 **/
	@RequestMapping(value = "/admin/photo/manage")
	public String photoList(Model model) {
		List<PhotoVo> photos = photoService.list();
		model.addAttribute("photos", photos);
		return "photo/photo_manage";
	}
	
	/** 사진 크게 보기 **/
	@ResponseBody
	@RequestMapping(value = "/photo/view.do")
	public String photoDoView(int seq) throws JsonProcessingException {
		PhotoVo photo = photoService.get(seq);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(photo);
	}
	
	/** 사진 삭제 **/
	@RequestMapping(value = "admin/photo/delete.do")
	public String photoDelete(HttpSession session, int seq) {
		String rootPath = session.getServletContext().getRealPath("");
		PhotoVo photo = photoService.get(seq);
		photoService.deleteFile(rootPath, photo.getImage());
		photoService.deleteFile(rootPath, photo.getSnapsht());
		
		boolean result = photoService.delete(seq);
		return "redirect:" + "/admin/photo/manage";
	}
	
	/** 사진 업로드 페이지로 이동 **/
	@RequestMapping(value = "admin/photo/upload", params = "!seq")
	public String photoUpload() {
		return "photo/photo_upload";
	}
	
	/** 사진 수정 페이지로 이동 **/
	@RequestMapping(value = "/admin/photo/upload", params = "seq")
	public String photoModify(Model model, int seq) {
		PhotoVo photo = photoService.get(seq);
		model.addAttribute("photo", photo);
		return "photo/photo_upload";
	}
	
	/** 사진 업로드 **/
	@RequestMapping(value = "admin/photo/upload.do", params = "!seq")
	public String photoDoUpload(HttpServletRequest request, PhotoVo photo, MultipartFile imageFile) throws IllegalStateException, IOException, ImageProcessingException, MetadataException {
		HttpSession session = request.getSession();
		String rootPath 	= session.getServletContext().getRealPath("");
		
		if(imageFile.getSize() != 0){
			photo = photoService.saveFile(photo, rootPath, imageFile);
		}
		photoService.insert(photo);
		
		return "redirect:" + "/admin/photo/manage";
	}
	
	/** 사진 수정 **/
	@RequestMapping(value = "/admin/photo/upload.do", params = "seq")
	public String photoDoModify(HttpServletRequest request, PhotoVo photo, MultipartFile imageFile) throws IllegalStateException, IOException, ImageProcessingException, MetadataException{
		HttpSession session = request.getSession();
		String rootPath 	= session.getServletContext().getRealPath("");
		
		
		if(imageFile.getSize() != 0){
			photoService.deleteFile(rootPath, photo.getImage());
			photoService.deleteFile(rootPath, photo.getSnapsht());
			
			photo = photoService.saveFile(photo, rootPath, imageFile);
		} 
		
		photoService.update(photo);
		
		return "redirect:" + "/admin/photo/manage";
	}
	
	
}
