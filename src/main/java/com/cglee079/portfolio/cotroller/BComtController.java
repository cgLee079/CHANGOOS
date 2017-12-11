package com.cglee079.portfolio.cotroller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.portfolio.model.BComtVo;
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
	@RequestMapping("/board/comment_paging.do")
	public String doPaging(int boardSeq, int page, int perPgLine) throws SQLException, JsonProcessingException{
		List<BComtVo> bcomts= bcomtService.paging(boardSeq, page, perPgLine);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(bcomts);
	}
	
	@ResponseBody
	@RequestMapping("/board/comment_submit.do")
	public String doSubmit(BComtVo bcomt) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.insert(bcomt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	@ResponseBody
	@RequestMapping("/board/comment_delete.do")
	public String doDelete(int seq) throws SQLException, JsonProcessingException{
		boolean result = bcomtService.delete(seq);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
}
