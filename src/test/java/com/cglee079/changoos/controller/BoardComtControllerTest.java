package com.cglee079.changoos.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.service.BoardComtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml"})
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
		int offset = 0;
		int limit = 10;
		
		List<BoardComtVo> comts = new ArrayList<>();
		when(boardComtService.paging(boardType, boardSeq, offset, limit)).thenReturn(comts);
		
		mockMvc.perform(get("/" + boardType + "/" + boardSeq + "/comments")
				.param("offset", String.valueOf(offset))
				.param("limit", String.valueOf(limit)))
			.andExpect(status().isOk())
			.andExpect(content().string(new Gson().toJson(comts).toString()));
			
	}
	
	@Test
	public void testCommentDoUpload() throws JsonProcessingException, Exception {
		String boardType = "SAMPLE_BOARDTYPE";
		int boardSeq = 3;
		boolean result = true;
		
		when(boardComtService.insert(eq(boardType), any(BoardComtVo.class))).thenReturn(result);
		
		mockMvc.perform(post("/" + boardType + "/" + boardSeq + "/comments"))
			.andExpect(status().isOk())
			.andExpect(content().string(String.valueOf(result)));
	}
	
	@Test
	public void testCommentDoUpdate() throws JsonProcessingException, Exception {
		String boardType = "SAMPLE_BOARDTYPE";
		int boardSeq = 3;
		int seq = 10;
		boolean result = true;
		
		when(boardComtService.update(eq(boardType), any(BoardComtVo.class))).thenReturn(result);
		
		mockMvc.perform(put("/" + boardType + "/" + boardSeq + "/comments/" + seq))
			.andExpect(status().isOk())
			.andExpect(content().string(String.valueOf(result)));
	}
	
	@Test
	public void testCommentDoDelete() throws JsonProcessingException, Exception {
		String boardType = "SAMPLE_BOARDTYPE";
		int boardSeq = 3;
		int seq = 10;
		boolean result = true;
		
		when(boardComtService.delete(eq(boardType), eq(seq))).thenReturn(result);
		
		mockMvc.perform(delete("/" + boardType + "/" + boardSeq + "/comments/" + seq))
			.andExpect(status().isOk())
			.andExpect(content().string(String.valueOf(result)));
	}
	
	@Test
	public void testCommentDoCheckPassword() throws JsonProcessingException, Exception {
		String boardType = "SAMPLE_BOARDTYPE";
		int boardSeq = 3;
		int seq = 10;
		boolean result = true;
		
		when(boardComtService.checkPwd(eq(boardType), any(BoardComtVo.class))).thenReturn(result);
		
		mockMvc.perform(post("/" + boardType + "/" + boardSeq + "/comments/" + seq + "/check"))
			.andExpect(status().isOk())
			.andExpect(content().string(String.valueOf(result)));
	}
	
	
	
}
