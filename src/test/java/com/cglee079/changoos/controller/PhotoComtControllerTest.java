package com.cglee079.changoos.controller;

import static org.mockito.Matchers.any;
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

import com.cglee079.changoos.model.PhotoComtVo;
import com.cglee079.changoos.service.PhotoComtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class PhotoComtControllerTest {

	@Mock	
	private PhotoComtService photoComtService;
	
	@InjectMocks
	private PhotoComtController photoComtController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(photoComtController).build();
	}
	
	@Test
	public void testPhotoCommentPaging() throws JsonProcessingException, Exception {
		int photoSeq = 3;
		
		List<PhotoComtVo> comts = new ArrayList<>();
		when(photoComtService.list(photoSeq)).thenReturn(comts);
		
		mockMvc.perform(get("/photo/comment/paging")
				.param("photoSeq", String.valueOf(photoSeq)))
			.andExpect(status().isOk())
			.andExpect(content().string(new ObjectMapper().writeValueAsString(comts)));
	}
	
	@Test
	public void testPhotoCommentDoUpload() throws JsonProcessingException, Exception {
		boolean result = true;
		
		when(photoComtService.insert(any(PhotoComtVo.class))).thenReturn(result);
		
		mockMvc.perform(post("/photo/comment/upload.do"))
			.andExpect(status().isOk())
			.andExpect(content().string(new ObjectMapper().writeValueAsString(result)));
	}
	
	@Test
	public void testPhotoCommentDoUpdate() throws JsonProcessingException, Exception {
		int seq = 3;
		String contents = "SAMPLE_CONTENT";
		boolean result = true;
		
		when(photoComtService.update(seq, contents)).thenReturn(result);
		
		mockMvc.perform(post("/photo/comment/update.do")
				.param("seq", String.valueOf(seq))
				.param("contents", contents))
			.andExpect(status().isOk())
			.andExpect(content().string(new ObjectMapper().writeValueAsString(result)));
	}
	
	@Test
	public void testPhotoCommentDoDelete() throws JsonProcessingException, Exception {
		int seq = 3;
		String password = "SAMPLE_PASSWORD";
		boolean result = true;
		
		when(photoComtService.delete(seq, password)).thenReturn(result);
		
		mockMvc.perform(post("/photo/comment/delete.do")
				.param("seq", String.valueOf(seq))
				.param("password", password))
			.andExpect(status().isOk())
			.andExpect(content().string(new ObjectMapper().writeValueAsString(result)));
		
	}
	
	
}
