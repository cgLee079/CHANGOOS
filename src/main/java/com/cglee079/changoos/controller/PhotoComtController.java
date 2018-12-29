package com.cglee079.changoos.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.changoos.model.PhotoComtVo;
import com.cglee079.changoos.service.PhotoComtService;
import com.cglee079.changoos.util.AuthManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class PhotoComtController {

	@Autowired
	private PhotoComtService photoComtService;

	@ResponseBody
	@RequestMapping("/photo/comment/paging")
	public String photoCommentPaging(int photoSeq) throws SQLException, JsonProcessingException {
		List<PhotoComtVo> comts = photoComtService.list(photoSeq);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(comts);
	}

	/** 사진 댓글 삽입 **/
	@ResponseBody
	@RequestMapping("/photo/comment/upload.do")
	public String photoCommentDoUpload(PhotoComtVo comt) throws SQLException, JsonProcessingException {
		boolean result = photoComtService.insert(comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 사진 댓글 수정 **/
	@ResponseBody
	@RequestMapping("/photo/comment/update.do")
	public String photoCommentDoUpdate(int seq, String contents) throws SQLException, JsonProcessingException {
		boolean result = photoComtService.update(seq, contents);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}

	/** 사진 댓글 삭제 **/
	@ResponseBody
	@RequestMapping("/photo/comment/delete.do")
	public String photoCommentDoDelete(int seq, String password) throws SQLException, JsonProcessingException {
		boolean result = photoComtService.delete(seq, password);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}

	/** 사진 댓글 비밀번호 확인 **/
	// TODO 프론트단 구현 안됨
//	@ResponseBody
//	@RequestMapping("/photo/comment/check-pwd.do")
//	public String photoCommentDoCheckPassword(int seq, String password) throws SQLException, JsonProcessingException {
//		boolean result = photoComtService.checkPwd(seq, password);
//		ObjectMapper mapper = new ObjectMapper();
//		return mapper.writeValueAsString(result);
//	}


}
