 package com.cglee079.changoos.controller;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.changoos.model.AdminVo;
import com.cglee079.changoos.model.ComtVo;
import com.cglee079.changoos.service.BlogComtService;
import com.cglee079.changoos.util.AuthManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BlogComtController {
	@Autowired
	private BlogComtService bcomtService;
	
	/** 게시판 댓글 페이징 **/
	@ResponseBody
	@RequestMapping("blog/comment/paging.do")
	public String doPaging(int boardSeq, int page, int perPgLine) throws SQLException, JsonProcessingException{
		List<ComtVo> bcomts= bcomtService.paging(boardSeq, page, perPgLine);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(bcomts);
	}
	
	/** 게시판 댓글 등록 **/
	@ResponseBody
	@RequestMapping("blog/comment/submit.do")
	public String doSubmit(ComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.insert(comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 댓글 삭제 **/
	@ResponseBody
	@RequestMapping("blog/comment/delete.do")
	public String doDelete(Authentication auth, int seq, String password) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.delete(seq, password, AuthManager.isAdmin());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 비밀번호 체크 **/
	@ResponseBody
	@RequestMapping("blog/comment/check-pwd.do")
	public String doCheckPwd(Authentication auth, int seq, String password) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.checkPwd(seq, password, AuthManager.isAdmin());
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 댓글 수정 **/
	@ResponseBody
	@RequestMapping("blog/comment/update.do")
	public String doUpdate(Authentication auth, int seq, String contents) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.update(seq, contents);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
}
