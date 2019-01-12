 package com.cglee079.changoos.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.service.BoardComtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BoardComtController {
	@Autowired
	private BoardComtService boardComtService;
	
	/** 게시판 댓글 페이징 **/
	@ResponseBody
	@RequestMapping(value = "/{boardType}/{boardSeq}/comments", method = RequestMethod.GET)
	public String commentPaging(@PathVariable String boardType, @PathVariable int boardSeq, int offset, int limit) throws SQLException, JsonProcessingException{
		List<BoardComtVo> comts= boardComtService.paging(boardType, boardSeq, offset, limit);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(comts);
	}
	
	/** 게시판 댓글 등록 **/
	@ResponseBody
	@RequestMapping(value = "/{boardType}/{boardSeq}/comments", method = RequestMethod.POST)
	public String commentDoUpload(@PathVariable String boardType, BoardComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = boardComtService.insert(boardType, comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 댓글 수정 **/
	@ResponseBody
	@RequestMapping(value = "/{boardType}/{boardSeq}/comments/{seq}", method = RequestMethod.PUT)
	public String commentDoUpdate(@PathVariable String boardType, BoardComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = boardComtService.update(boardType, comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(true);
	}
	
	/** 게시판 댓글 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/{boardType}/{boardSeq}/comments/{seq}", method = RequestMethod.DELETE)
	public String commentDoDelete(@PathVariable String boardType, @PathVariable int seq) throws SQLException, JsonProcessingException{
		boolean result = boardComtService.delete(boardType, seq);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 댓글 비밀번호 체크 **/
	@ResponseBody
	@RequestMapping(value = "/{boardType}/{boardSeq}/comments/{seq}/check", method = RequestMethod.POST)
	public String commentDoCheckPassword(@PathVariable String boardType, BoardComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = boardComtService.checkPwd(boardType, comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	

}
