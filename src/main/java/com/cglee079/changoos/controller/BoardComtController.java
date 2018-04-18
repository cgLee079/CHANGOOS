 package com.cglee079.changoos.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.service.BComtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BoardComtController {
	@Autowired
	private BComtService bcomtService;
	
	/** 게시판 댓글 페이징 **/
	@ResponseBody
	@RequestMapping("board/comment/paging.do")
	public String doPaging(int boardSeq, int page, int perPgLine) throws SQLException, JsonProcessingException{
		List<BoardComtVo> bcomts= bcomtService.paging(boardSeq, page, perPgLine);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(bcomts);
	}
	
	/** 게시판 댓글 등록 **/
	@ResponseBody
	@RequestMapping("board/comment/submit.do")
	public String doSubmit(BoardComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.insert(comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 댓글 삭제 **/
	@ResponseBody
	@RequestMapping("board/comment/delete.do")
	public String doDelete(int seq, String password, boolean isAdmin) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.delete(seq, password, isAdmin);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 비밀번호 체크 **/
	@ResponseBody
	@RequestMapping("board/comment/checkPwd.do")
	public String doCheckPwd(int seq, String password) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.checkPwd(seq, password);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 댓글 수정 **/
	@ResponseBody
	@RequestMapping("board/comment/update.do")
	public String doUpdate(int seq, String contents) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.update(seq, contents);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
}
