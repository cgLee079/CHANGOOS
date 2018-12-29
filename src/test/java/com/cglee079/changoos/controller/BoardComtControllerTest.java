package com.cglee079.changoos.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.service.BoardComtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class BoardComtControllerTest {

	@Mock	
	private BoardComtService boardComtService;
	
	@InjectMocks
	private BoardComtController boardComtController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(boardComtController).build();
	}
	
	@Test
	public void testCommentPaging() throws JsonProcessingException, Exception {
		String boardType = "SAMPLE_BOARDTYPE";
		int boardSeq = 3;
		int page = 1;
		int perPgLine = 10;
		
		List<BoardComtVo> comts = new ArrayList<>();
		when(boardComtService.paging(boardType, boardSeq, page, perPgLine)).thenReturn(comts);
		
		mockMvc.perform(get("/board/comment/paging")
				.param("boardType", boardType)
				.param("boardSeq", String.valueOf(boardSeq))
				.param("page", String.valueOf(page))
				.param("perPgLine", String.valueOf(perPgLine)))
			.andExpect(status().isOk())
			.andExpect(content().string(new ObjectMapper().writeValueAsString(comts)));
			
	}
	
	@Test
	public void testCommentDoUpload() throws JsonProcessingException, Exception {
		String boardType = "SAMPLE_BOARDTYPE";
		boolean result = true;
		
		when(boardComtService.insert(same(boardType), any(BoardComtVo.class))).thenReturn(result);
		
		mockMvc.perform(post("/board/comment/upload.do")
				.param("boardType", boardType))
			.andExpect(status().isOk())
			.andExpect(content().string(new ObjectMapper().writeValueAsString(result)));
			
	}
	
	@Test
	public void testCommentDoUpdate() throws JsonProcessingException, Exception {
		String boardType = "SAMPLE_BOARDTYPE";
		boolean result = true;
		
		when(boardComtService.update(same(boardType), any(BoardComtVo.class))).thenReturn(result);
		
		mockMvc.perform(post("/board/comment/update.do")
				.param("boardType", boardType))
			.andExpect(status().isOk())
			.andExpect(content().string(new ObjectMapper().writeValueAsString(result)));
	}
	
	@Test
	public void testCommentDoDelete() throws JsonProcessingException, Exception {
		String boardType = "SAMPLE_BOARDTYPE";
		boolean result = true;
		
		when(boardComtService.delete(same(boardType), any(BoardComtVo.class))).thenReturn(result);
		
		mockMvc.perform(post("/board/comment/delete.do")
				.param("boardType", boardType))
			.andExpect(status().isOk())
			.andExpect(content().string(new ObjectMapper().writeValueAsString(result)));
		
	}
	
	@Test
	public void testCommentDoCheckPassword() throws JsonProcessingException, Exception {
		String boardType = "SAMPLE_BOARDTYPE";
		boolean result = true;
		
		when(boardComtService.checkPwd(same(boardType), any(BoardComtVo.class))).thenReturn(result);
		
		mockMvc.perform(post("/board/comment/check-pwd.do")
				.param("boardType", boardType))
			.andExpect(status().isOk())
			.andExpect(content().string(new ObjectMapper().writeValueAsString(result)));
	}
	
	
	
}
