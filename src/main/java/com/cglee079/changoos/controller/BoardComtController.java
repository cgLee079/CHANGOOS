 package com.cglee079.changoos.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
	@RequestMapping("/board/comment/paging")
	public String commentPaging(String boardType, int boardSeq, int page, int perPgLine) throws SQLException, JsonProcessingException{
		List<BoardComtVo> comts= boardComtService.paging(boardType, boardSeq, page, perPgLine);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(comts);
	}
	
	/** 게시판 댓글 등록 **/
	@ResponseBody
	@RequestMapping("/board/comment/upload.do")
	public String commentDoUpload(String boardType, BoardComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = boardComtService.insert(boardType, comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 댓글 수정 **/
	@ResponseBody
	@RequestMapping("/board/comment/update.do")
	public String commentDoUpdate(String boardType, BoardComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = boardComtService.update(boardType, comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 댓글 삭제 **/
	@ResponseBody
	@RequestMapping("/board/comment/delete.do")
	public String commentDoDelete(String boardType, BoardComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = boardComtService.delete(boardType, comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	
	/** 게시판 댓글 비밀번호 체크 **/
	@ResponseBody
	@RequestMapping("/board/comment/check-pwd.do")
	public String commentDoCheckPassword(String boardType, BoardComtVo comt) throws SQLException, JsonProcessingException{
		boolean result = boardComtService.checkPwd(boardType, comt);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(result);
	}
	

}
