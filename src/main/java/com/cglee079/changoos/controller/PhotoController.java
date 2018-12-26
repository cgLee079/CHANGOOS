package com.cglee079.changoos.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.model.PhotoVo;
import com.cglee079.changoos.service.PhotoService;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

@Controller
public class PhotoController {
	@Autowired
	private PhotoService photoService;
	
	/** 사진 페이지로 이동 **/
	@RequestMapping(value = "/photo")
	public String photoList(Model model) {
		List<PhotoVo> photos = photoService.list(null);
		List<Integer> seqs = photoService.seqs();
		model.addAttribute("photos", photos);
		model.addAttribute("seqs", seqs);
		return "photo/photo_view";
	}
	
	/** 사진 크게 보기, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/photo/view.do")
	public String photoView(HttpSession session, int seq) throws JsonProcessingException {
		PhotoVo photo = photoService.get(seq);
		JSONObject photoJSON = new JSONObject(new Gson().toJson(photo));
		
		Map<Integer, Boolean> likePhotos = (Map<Integer, Boolean>)session.getAttribute("likePhotos");
		photoJSON.put("like", likePhotos.get(seq));
		
		return photoJSON.toString();
	}
	
	/** 사진 좋아요, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/photo/doLike.do")
	public String doLike(HttpSession session, int seq, boolean like) throws JsonProcessingException {
		int likeCnt = photoService.doLike((Map<Integer, Boolean>)session.getAttribute("likePhotos"), seq, like);
		JSONObject result = new JSONObject();
		result.put("likeCnt", likeCnt);
		result.put("like", like);
		return result.toString();
	}
	
	
	/** 사진 관리 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/photo")
	public String photoManage(Model model) {
		return "photo/photo_manage";
	}
	
	/** 사진 관리 페이지 리스트, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/photo/list.do")
	public String photoManagePaging(@RequestParam Map<String, Object> map) {
		List<PhotoVo> photos = photoService.list(map);
		Gson gson = new Gson();
		return gson.toJson(photos).toString();
	}
	
	/** 사진 삭제 , Ajax **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/photo/delete.do")
	public String photoDelete(int seq) {
		boolean result = photoService.delete(seq);
		return new JSONObject().put("result", result).toString();
	}
	
	/** 사진 업로드 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/photo/upload", params = "!seq")
	public String photoUpload() {
		return "photo/photo_upload";
	}
	
	/** 사진 수정 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/photo/upload", params = "seq")
	public String photoModify(Model model, int seq) {
		PhotoVo photo = photoService.get(seq);
		model.addAttribute("photo", photo);
		return "photo/photo_upload";
	}
	
	/** 사진 파일 업로드 **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/photo/image/upload.do", method = RequestMethod.POST, params = "!seq")
	public String photoImageDoUpload(MultipartFile image) throws IllegalStateException, IOException, ImageProcessingException, MetadataException {
		PhotoVo photo = photoService.savePhoto(image);
		return  new Gson().toJson(photo).toString();
	}
	
	/** 사진 업로드 **/
	@RequestMapping(value = "/mgnt/photo/upload.do", method = RequestMethod.POST)
	public String photoDoUpload(PhotoVo photo) throws IllegalStateException, IOException, ImageProcessingException, MetadataException {
		photoService.insert(photo);
		return "redirect:" + "/mgnt/photo";
	}
	
	/** 사진 수정 **/
	@RequestMapping(value = "/mgnt/photo/upload.do", method = RequestMethod.POST, params = "seq")
	public String photoDoModify(PhotoVo photo) throws IllegalStateException, IOException, ImageProcessingException, MetadataException{
		photoService.update(photo);
		return "redirect:" + "/mgnt/photo";
	}
	
}
