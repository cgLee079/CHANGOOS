package com.cglee079.changoos.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
	@RequestMapping(value = "/photos", method = RequestMethod.GET)
	public String photoList(Model model, HashMap<String, Object> params) {
		params.put("enabled", true);
		
		List<PhotoVo> photos = photoService.list(params);
		List<Integer> seqs = photoService.seqs();
		model.addAttribute("photos", photos);
		model.addAttribute("seqs", seqs);
		return "photo/photo_view";
	}
	
	/** 사진 크게 보기, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/photos/{seq}", method = RequestMethod.GET)
	public String photoView(HttpSession session, @PathVariable int seq){
		PhotoVo photo = photoService.get((Set<Integer>)session.getAttribute("likePhotos"), seq);
		return new Gson().toJson(photo).toString();
	}
	
	/** 사진 관리 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/photos", method = RequestMethod.GET)
	public String photoManage() {
		return "photo/photo_manage";
	}
	
	/** 사진 관리 페이지 리스트, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/photos/records")
	public String photoManagePaging(@RequestParam Map<String, Object> map) {
		List<PhotoVo> photos = photoService.list(map);
		return new Gson().toJson(photos).toString();
	}
	
	/** 사진 업로드 페이지로 이동 **/
	@RequestMapping(value = "/photos/post", method = RequestMethod.GET)
	public String photoUpload() {
		return "photo/photo_upload";
	}
	
	/** 사진 수정 페이지로 이동 **/
	@RequestMapping(value = "/photos/post/{seq}", method = RequestMethod.GET)
	public String photoModify(Model model, @PathVariable int seq) {
		PhotoVo photo = photoService.get(seq);
		model.addAttribute("photo", photo);
		return "photo/photo_upload";
	}
	
	/** 사진 업로드 **/
	@RequestMapping(value = "/photos/post", method = RequestMethod.POST)
	public String photoDoUpload(HttpSession session, PhotoVo photo){
		photoService.insert(photo, (String)session.getAttribute("tempDirId"));
		return "redirect:" + "/mgnt/photos";
	}
	
	/** 사진 수정 **/
	@RequestMapping(value = "/photos/post/{seq}", method = RequestMethod.PUT)
	public String photoDoModify(HttpSession session, PhotoVo photo){
		photoService.update(photo, (String)session.getAttribute("tempDirId"));
		return "redirect:" + "/mgnt/photos";
	}
	
	/** 사진 삭제 , Ajax **/
	@ResponseBody
	@RequestMapping(value = "/photos/post/{seq}", method = RequestMethod.DELETE)
	public String photoDoDelete(@PathVariable int seq) {
		boolean result = photoService.delete(seq);
		return new JSONObject().put("result", result).toString();
	}
	
	/** 사진 파일 업로드 **/
	@ResponseBody
	@RequestMapping(value = "/photos/post/image", method = RequestMethod.POST)
	public String photoImageDoUpload(HttpSession session, MultipartFile image){
		PhotoVo photo = photoService.savePhoto(image, (String)session.getAttribute("tempDirId"));
		return new Gson().toJson(photo).toString();
	}
	
	/** 사진 좋아요, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/photos/{seq}/like", method = RequestMethod.PUT)
	public String photoDoLike(HttpSession session, @PathVariable int seq, boolean like){
		PhotoVo photo = photoService.doLike((Set<Integer>)session.getAttribute("likePhotos"), seq, like);
		return new Gson().toJson(photo).toString();
	}
	
}
