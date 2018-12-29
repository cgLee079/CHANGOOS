package com.cglee079.changoos.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.service.BoardImageService;
import com.cglee079.changoos.service.ProjectService;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class BoardImageControllerTest {

	@Mock	
	private BoardImageService boardImageService;
	
	@InjectMocks
	private BoardImageController boardImageController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(boardImageController).build();
	}
	
	@Test
	public void testImageUpload() throws Exception {
		String editor = "SAMPLE_EDITOR";
		
		mockMvc.perform(get("/mgnt/board/image/upload")
				.param("editor", editor))
			.andExpect(status().isOk())
			.andExpect(view().name("popup/popup_imageupload"))
			.andExpect(model().attribute("editor", editor));
	}
	
	@Test
	public void testImageDoUpload() throws Exception {
		MockMultipartFile imageFile = new MockMultipartFile("image", new byte[1]);
		String pathname = "SAMPLE_PATHNAME";
		JSONObject result = new JSONObject();
		result.put("pathname", pathname);
		
		when(boardImageService.saveImage(same(imageFile))).thenReturn(pathname);
		
		mockMvc.perform(fileUpload("/mgnt/board/image/upload.do")
				.file(imageFile))
			.andExpect(status().isOk())
			.andExpect(content().json(result.toString()));
	}
	
	@Test
	public void testImageDoPasteUpload() throws Exception {
		String base64 = "SAMPLE_BASE64";
		String pathname = "SMAPLE_PATHNAME";
		JSONObject result = new JSONObject();
		result.put("filename", pathname);
		result.put("pathname", pathname);
		
		when(boardImageService.saveBase64(base64)).thenReturn(pathname);
		
		mockMvc.perform(fileUpload("/mgnt/board/image/paste-upload.do")
				.param("base64", base64))
			.andExpect(status().isOk())
			.andExpect(content().json(result.toString()));
	}
	
}
