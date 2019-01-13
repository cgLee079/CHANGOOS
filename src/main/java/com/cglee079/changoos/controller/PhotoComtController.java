package com.cglee079.changoos.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.changoos.model.PhotoComtVo;
import com.cglee079.changoos.service.PhotoComtService;
import com.cglee079.changoos.util.AuthManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Controller
public class PhotoComtController {

	@Autowired
	private PhotoComtService photoComtService;

	@ResponseBody
	@RequestMapping(value = "/photos/{photoSeq}/comments", method = RequestMethod.GET )
	public String photoCommentPaging(@PathVariable int photoSeq) {
		List<PhotoComtVo> comts = photoComtService.list(photoSeq);
		return new Gson().toJson(comts).toString();
	}

	/** 사진 댓글 삽입 **/
	@ResponseBody
	@RequestMapping(value = "/photos/{photoSeq}/comments", method = RequestMethod.POST)
	public boolean photoCommentDoUpload(PhotoComtVo comt) {
		boolean result = photoComtService.insert(comt);
		return result;
	}
	
	/** 사진 댓글 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/photos/{photoSeq}/comments/{seq}", method = RequestMethod.DELETE)
	public boolean photoCommentDoDelete(PhotoComtVo comt) {
		boolean result = photoComtService.delete(comt);
		return result;
	}
	
	/** 사진 비밀번호 검증 **/
	@ResponseBody
	@RequestMapping(value = "/photos/{photoSeq}/comments/{seq}/check", method = RequestMethod.POST)
	public boolean photoCommentDoCheck(PhotoComtVo comt){
		boolean result = photoComtService.check(comt);
		return result;
	}
	

}
