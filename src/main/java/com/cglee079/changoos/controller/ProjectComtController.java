package com.cglee079.changoos.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.changoos.model.ComtVo;
import com.cglee079.changoos.service.ProjectComtService;
import com.cglee079.changoos.util.AuthManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ProjectComtController {
	
	@Autowired
	private ProjectComtService pcomtService;
	
	/** 프로젝트 댓글 페이징 **/
	@ResponseBody
	@RequestMapping("/project/comment/paging.do")
	public String doPaging(int boardSeq, int page, int perPgLine) throws SQLException, JsonProcessingException{
		List<ComtVo> bcomts= pcomtService.paging(boardSeq, page, perPgLine);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(bcomts);
	}
	
	/** 프로젝트 댓글 삽입 **/
	@ResponseBody
	@RequestMapping("/project/comment/submit.do")
	public String doSubmit(ComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = pcomtService.insert(comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 프로젝트 댓글 삭제 **/
	@ResponseBody
	@RequestMapping("/project/comment/delete.do")
	public String doDelete(Authentication auth, int seq, String password) throws SQLException, JsonProcessingException{
		boolean result = pcomtService.delete(seq, password, AuthManager.isAdmin());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 프로젝트 댓글 비빌번호 확인 **/
	@ResponseBody
	@RequestMapping("project/comment/check-pwd.do")
	public String doCheckPwd(Authentication auth,  int seq, String password) throws SQLException, JsonProcessingException{
		boolean result = pcomtService.checkPwd(seq, password, AuthManager.isAdmin());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 프로젝트 댓글 수정 **/
	@ResponseBody
	@RequestMapping("project/comment/update.do")
	public String doUpdate(int seq, String contents) throws SQLException, JsonProcessingException{
		boolean result = pcomtService.update(seq, contents);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
}
