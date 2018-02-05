 package com.cglee079.portfolio.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.portfolio.model.ComtVo;
import com.cglee079.portfolio.model.BoardVo;
import com.cglee079.portfolio.service.BComtService;
import com.cglee079.portfolio.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BComtController {
	@Autowired
	private BComtService bcomtService;
	
	@ResponseBody
	@RequestMapping("board/comment/paging.do")
	public String doPaging(int boardSeq, int page, int perPgLine) throws SQLException, JsonProcessingException{
		List<ComtVo> bcomts= bcomtService.paging(boardSeq, page, perPgLine);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(bcomts);
	}
	
	@ResponseBody
	@RequestMapping("board/comment/submit.do")
	public String doSubmit(ComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.insert(comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	@ResponseBody
	@RequestMapping("board/comment/delete.do")
	public String doDelete(int seq, String password, boolean isAdmin) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.delete(seq, password, isAdmin);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	@ResponseBody
	@RequestMapping("board/comment/checkPwd.do")
	public String doCheckPwd(int seq, String password) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.checkPwd(seq, password);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	@ResponseBody
	@RequestMapping("board/comment/update.do")
	public String doUpdate(int seq, String contents) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.update(seq, contents);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
}
