package com.cglee079.portfolio.cotroller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.portfolio.model.ComtVo;
import com.cglee079.portfolio.model.BoardVo;
import com.cglee079.portfolio.service.ComtService;
import com.cglee079.portfolio.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class ComtController {
	
	@Autowired
	private ComtService comtService;
	
	@ResponseBody
	@RequestMapping("/item/comment_paging.do")
	public String doPaging(int parentSeq, int page, int perPgLine) throws SQLException, JsonProcessingException{
		List<ComtVo> bcomts= comtService.paging(parentSeq, page, perPgLine);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(bcomts);
	}
	
	@ResponseBody
	@RequestMapping("/item/comment_submit.do")
	public String doSubmit(ComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = comtService.insert(comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	@ResponseBody
	@RequestMapping("/item/comment_delete.do")
	public String doDelete(int seq, String password) throws SQLException, JsonProcessingException{
		boolean result = comtService.delete(seq, password);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	@ResponseBody
	@RequestMapping("/item/comment_checkPwd.do")
	public String doCheckPwd(int seq, String password) throws SQLException, JsonProcessingException{
		boolean result = comtService.checkPwd(seq, password);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	@ResponseBody
	@RequestMapping("/item/comment_update.do")
	public String doUpdate(int seq, String contents) throws SQLException, JsonProcessingException{
		boolean result = comtService.update(seq, contents);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
}
