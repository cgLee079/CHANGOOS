package com.cglee079.changoos.controller;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

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

import com.cglee079.changoos.service.BoardImageService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml"})
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
		
		mockMvc.perform(get("/board/post/image")
				.param("editor", editor))
			.andExpect(status().isOk())
			.andExpect(view().name("popup/popup_imageupload"))
			.andExpect(model().attribute("editor", editor));
	}
	
	@Test
	public void testImageDoUpload() throws Exception {
		MockMultipartFile imageFile = new MockMultipartFile("image", new byte[1]);
		String pathname = "SAMPLE_PATHNAME";
		String tempDirId = "SAMPLE_TEMPDIR_ID";
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("tempDirId", tempDirId);
		
		JSONObject result = new JSONObject();
		result.put("pathname", pathname);
		
		when(boardImageService.saveImage(eq((String)session.getAttribute("tempDirId")), same(imageFile))).thenReturn(pathname);
		
		mockMvc.perform(fileUpload("/board/post/image")
				.file(imageFile)
				.session(session)
				.contentType("multipart/form-data"))
			.andExpect(status().isOk())
			.andExpect(content().json(result.toString()));
	}
	
	@Test
	public void testImageDoPasteUpload() throws Exception {
		String base64 = "SAMPLE_BASE64";
		String pathname = "SMAPLE_PATHNAME";
		String tempDirId = "SAMPLE_TEMPDIR_ID";
		
		JSONObject result = new JSONObject();
		result.put("pathname", pathname);
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("tempDirId", tempDirId);
		
		when(boardImageService.saveBase64((String)session.getAttribute("tempDirId"), base64)).thenReturn(pathname);
		
		mockMvc.perform(fileUpload("/board/post/image")
				.session(session)
				.param("base64", base64)
				.contentType("application/x-www-form-urlencoded"))
			.andExpect(status().isOk())
			.andExpect(content().json(result.toString()));
	}
	
}
