package com.cglee079.portfolio.cotroller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cglee079.portfolio.model.BoardVo;
import com.cglee079.portfolio.service.BComtService;
import com.cglee079.portfolio.service.BoardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.scene.layout.Border;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@Autowired 
	private BComtService bcomtService;
	
	@RequestMapping("/board")
	public String board(Model model) throws SQLException, JsonProcessingException{
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
		int comtCnt = bcomtService.count(seq);
		model.addAttribute("board", board);
		model.addAttribute("comtCnt", comtCnt);
		return "board/board_view";
	}

}
