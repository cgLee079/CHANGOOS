package com.cglee079.portfolio.cotroller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.portfolio.model.BoardVo;
import com.cglee079.portfolio.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board")
	public String adminLogin(Model model){
		int count = boardService.count();
		model.addAttribute("count", count);
		return "board/board_list";
	}
	
	@ResponseBody
	@RequestMapping("/board/board_paging.do")
	public String doPaging(int page, int perPgLine) throws SQLException, JsonProcessingException{
		List<BoardVo> boards= boardService.paging(page, perPgLine);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(boards);
	}
	
	@RequestMapping("/board/board_view")
	public String doPaging(Model model, int seq) throws SQLException, JsonProcessingException{
		BoardVo board = boardService.get(seq);
		model.addAttribute("board", board);
		return "board/board_view";
	}

}
