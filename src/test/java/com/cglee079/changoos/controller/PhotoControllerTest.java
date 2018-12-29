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
import com.cglee079.changoos.model.PhotoVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.service.BoardImageService;
import com.cglee079.changoos.service.PhotoService;
import com.cglee079.changoos.service.ProjectService;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class PhotoControllerTest {

	@Mock	
	private PhotoService photoService;
	
	@InjectMocks
	private PhotoController photoController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(photoController).build();
	}
	
	@Test
	public void testPhotoList() throws Exception {
		List<PhotoVo> photos = new ArrayList<>();
		List<Integer> seqs = new ArrayList<>();
		
		when(photoService.list(null)).thenReturn(photos);
		when(photoService.seqs()).thenReturn(seqs);
		
		mockMvc.perform(get("/photo"))
			.andExpect(status().isOk())
			.andExpect(view().name("photo/photo_view"))
			.andExpect(model().attribute("photos", photos))
			.andExpect(model().attribute("seqs", seqs));
	}
	
	
	@Test
	public void testPhotoView() throws Exception {
		int seq = 3;
		PhotoVo photo = new PhotoVo();
		photo.setSeq(seq);
		Map<Integer, Boolean> likePhotos = new HashMap<>();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("likePhotos", likePhotos);
		
		when(photoService.get(likePhotos, seq)).thenReturn(photo);
		
		mockMvc.perform(get("/photo/view")
				.session(session)
				.param("seq", String.valueOf(seq)))
			.andExpect(status().isOk())
			.andExpect(content().json(new Gson().toJson(photo).toString()));
	}
	
	@Test
	public void testPhotoManage() throws Exception {
		mockMvc.perform(get("/mgnt/photo"))
			.andExpect(status().isOk())
			.andExpect(view().name("photo/photo_manage"));
	}
	
	@Test
	public void testPhotoManagePaging() throws Exception {
		Map<String,Object> params = new HashMap<>();
		List<PhotoVo> photos = new ArrayList<>();
		
		when(photoService.list(params)).thenReturn(photos);
		
		mockMvc.perform(get("/mgnt/photo/paging"))
			.andExpect(status().isOk())
			.andExpect(content().json(new Gson().toJson(photos).toString()));
	}
	
	
	@Test
	public void testPhotoUpload() throws Exception {
		mockMvc.perform(get("/mgnt/photo/upload"))
			.andExpect(status().isOk())
			.andExpect(view().name("photo/photo_upload"));
	}
	
	@Test
	public void testPhotoModify() throws Exception {
		int seq = 3;
		PhotoVo photo = new PhotoVo();
		
		when(photoService.get(seq)).thenReturn(photo);
		
		mockMvc.perform(get("/mgnt/photo/upload")
				.param("seq", String.valueOf(seq)))
			.andExpect(status().isOk())
			.andExpect(view().name("photo/photo_upload"))
			.andExpect(model().attribute("photo", photo));
	}
	
	@Test
	public void testPhotoDoUpload() throws Exception {
		mockMvc.perform(post("/mgnt/photo/upload.do"))
			.andExpect(redirectedUrl("/mgnt/photo"))
			.andExpect(status().isFound());
			
		verify(photoService).insert(any(PhotoVo.class));
	}
	
	@Test
	public void testPhotoDoModify() throws Exception {
		int seq = 3;
		
		mockMvc.perform(post("/mgnt/photo/upload.do")
				.param("seq", String.valueOf(seq)))
			.andExpect(redirectedUrl("/mgnt/photo"))
			.andExpect(status().isFound());
			
		verify(photoService).update(any(PhotoVo.class));
	}
	
	@Test
	public void testPhotoDoDelete() throws Exception {
		int seq = 3;
		boolean result = true;
		
		when(photoService.delete(seq)).thenReturn(result);
		
		mockMvc.perform(get("/mgnt/photo/delete.do")
				.param("seq", String.valueOf(seq)))
			.andExpect(status().isOk())
			.andExpect(content().json(new JSONObject().put("result", result).toString()));
	}
	
	@Test
	public void testPhotoImageDoUpload() throws Exception {
		PhotoVo photo = new PhotoVo();
		MockMultipartFile imageFile = new MockMultipartFile("image", new byte[1]);
		
		when(photoService.savePhoto(imageFile)).thenReturn(photo);
		
		mockMvc.perform(fileUpload("/mgnt/photo/image/upload.do")
				.file(imageFile))
			.andExpect(status().isOk())
			.andExpect(content().json(new Gson().toJson(photo).toString()));
	}
	
	@Test
	public void testphotoDoLike() throws Exception {
		PhotoVo photo = new PhotoVo();
		int seq = 3;
		boolean like = true;
		Map<Integer, Boolean> likePhotos = new HashMap<>();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("likePhotos", likePhotos);
		
		when(photoService.doLike(likePhotos, seq, like)).thenReturn(photo);
		
		mockMvc.perform(fileUpload("/photo/like.do")
				.session(session)
				.param("seq", String.valueOf(seq))
				.param("like", String.valueOf(like)))
			.andExpect(status().isOk())
			.andExpect(content().json(new Gson().toJson(photo).toString()));
	}
	
	
}
