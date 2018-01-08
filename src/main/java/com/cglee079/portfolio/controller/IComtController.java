package com.cglee079.portfolio.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.portfolio.model.ComtVo;
import com.cglee079.portfolio.service.IComtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class IComtController {
	
	@Autowired
	private IComtService icomtService;
	
	@ResponseBody
	@RequestMapping("item/comment/paging.do")
	public String doPaging(int boardSeq, int page, int perPgLine) throws SQLException, JsonProcessingException{
		List<ComtVo> bcomts= icomtService.paging(boardSeq, page, perPgLine);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(bcomts);
	}
	
	@ResponseBody
	@RequestMapping("item/comment/submit.do")
	public String doSubmit(ComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = icomtService.insert(comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	@ResponseBody
	@RequestMapping("item/comment/delete.do")
	public String doDelete(int seq, String password, boolean isAdmin) throws SQLException, JsonProcessingException{
		boolean result = icomtService.delete(seq, password, isAdmin);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	@ResponseBody
	@RequestMapping("item/comment/checkPwd.do")
	public String doCheckPwd(int seq, String password, boolean isAdmin) throws SQLException, JsonProcessingException{
		boolean result = icomtService.checkPwd(seq, password, isAdmin);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	@ResponseBody
	@RequestMapping("item/comment/update.do")
	public String doUpdate(int seq, String contents) throws SQLException, JsonProcessingException{
		boolean result = icomtService.update(seq, contents);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
}
