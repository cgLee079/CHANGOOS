package com.cglee079.changoos.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.service.StudyService;
import com.google.gson.Gson;
import com.mysql.fabric.xmlrpc.base.Params;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml"})
public class StudyControllerTest {
	@Mock
	private StudyService studyService;

	@InjectMocks
	private StudyController studyController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(studyController).build();
	}
	
	@Test
	public void testStudyList() throws Exception {
		Map<String, Object> param = new HashMap<>();
		List<String> categories = new ArrayList<String>();
		String title = "SAMPLE_TITLE";
		String category ="SAMPLE_CATEGORY";
		int totalCount = 1;
		
		param.put("title", title);
		param.put("category", category);
		
		when(studyService.getCategories()).thenReturn(categories);
		when(studyService.count(param)).thenReturn(totalCount);
		
		mockMvc.perform(get("/studies")
				.param("category", category)
				.param("title", title))
			.andExpect(status().isOk())
			.andExpect(view().name("study/study_list"))
			.andExpect(model().attribute("totalCount", totalCount))
			.andExpect(model().attribute("categories", categories))
			.andExpect(model().attribute("category", category))
			.andExpect(model().attribute("title", title));
	}
	
	@Test
	public void testStudyListPaging() throws Exception {
		Map<String, Object> params = new HashMap<>();
		params.put("enabled", true);
		
		List<StudyVo> studies = new ArrayList<StudyVo>();
				
		when(studyService.paging(params)).thenReturn(studies);
		
		mockMvc.perform(get("/studies/records"))
			.andExpect(status().isOk())
			.andExpect(content().json(new Gson().toJson(studies)));
	}
	
	
	@Test
	public void testStudyView() throws Exception {
		List<BoardFileVo> files = new ArrayList<BoardFileVo>();
		Set<Integer> visitStudies = new HashSet<Integer>();
		String category = "SAMPLE_CATEGORY";
		int seq = 3;
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("visitStudies", visitStudies);
		
		StudyVo study = StudyVo.builder()
				.files(files)
				.build();
		StudyVo beforeStudy = StudyVo.builder().build();
		StudyVo afterStudy 	= StudyVo.builder().build();
		
		when(studyService.doView((Set<Integer>)session.getAttribute("visitStudies"), seq)).thenReturn(study);
		when(studyService.getBefore(seq, category)).thenReturn(beforeStudy);
		when(studyService.getAfter(seq, category)).thenReturn(afterStudy);
		
		mockMvc.perform(get("/studies/" + seq)
				.session(session)
				.param("category", category))
			.andExpect(status().isOk())
			.andExpect(view().name("study/study_view"))
			.andExpect(model().attribute("category", category))
			.andExpect(model().attribute("beforeStudy", beforeStudy))
			.andExpect(model().attribute("study", study))
			.andExpect(model().attribute("afterStudy", afterStudy))
			.andExpect(model().attribute("files", files));
	}
	
	@Test
	public void testStudyManage() throws Exception {
		mockMvc.perform(get("/mgnt/studies"))
			.andExpect(status().isOk())
			.andExpect(view().name("study/study_manage"));
	}	
	
	@Test
	public void testStudyManagePaging() throws Exception {
		Map<String, Object> params = new HashMap<>();
		List<StudyVo> studies = new ArrayList<StudyVo>();
				
		when(studyService.list(params)).thenReturn(studies);
		
		mockMvc.perform(get("/mgnt/studies/records"))
			.andExpect(status().isOk())
			.andExpect(content().json(new Gson().toJson(studies).toString()));
		
	}
	
	@Test
	public void testStudyUpload() throws Exception {
		mockMvc.perform(get("/studies/post"))
			.andExpect(status().isOk())
			.andExpect(view().name("study/study_upload"));
	}	
	
	@Test
	public void testStudyModify() throws Exception {
		List<BoardFileVo> files = new ArrayList<BoardFileVo>();
		List<BoardImageVo> images = new ArrayList<BoardImageVo>();
		
		int seq = 3;
		
		StudyVo study = StudyVo.builder()
				.files(files)
				.images(images)
				.build();
		
		when(studyService.get(seq)).thenReturn(study);
		
		mockMvc.perform(get("/studies/post/" + seq))
			.andExpect(status().isOk())
			.andExpect(view().name("study/study_upload"))
			.andExpect(model().attribute("study", study))
			.andExpect(model().attribute("files", files))
			.andExpect(model().attribute("images", images));
	}	
	
	@Test
	public void testStudyDoUpload() throws Exception {
		String imageValues = "SAMPLE_IMAGEVALUES";
		String fileValues = "SAMPLE_FILEVALUES";
		String tempDirId = "SAMPLE_TEMPDIR_ID";
		int seq = 3;
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("tempDirId", tempDirId);
		
		when(studyService.insert(any(StudyVo.class), eq((String)session.getAttribute("tempDirId")), same(imageValues), same(fileValues))).thenReturn(seq);
		
		mockMvc.perform(post("/studies/post")
				.session(session)
				.param("imageValues", imageValues)
				.param("fileValues", fileValues))
			.andExpect(redirectedUrl("/studies/" + seq))
			.andExpect(status().isFound());
		
	}
	
	@Test
	public void testStudyDoModify() throws Exception {
		String imageValues = "SAMPLE_IMAGEVALUES";
		String fileValues = "SAMPLE_FILEVALUES";
		String tempDirId = "SAMPLE_TEMPDIR_ID";
		int seq = 3;
		boolean result = true;
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("tempDirId", tempDirId);
		
		mockMvc.perform(put("/studies/post/" + seq)
				.session(session)
				.param("imageValues", imageValues)
				.param("fileValues", fileValues))
			.andExpect(redirectedUrl("/studies/" + seq))
			.andExpect(status().isFound());
		
		verify(studyService).update(any(StudyVo.class), eq((String)session.getAttribute("tempDirId")), same(imageValues), same(fileValues));
	}
	
	@Test
	public void testStudyDoDelete() throws Exception{
		StudyVo study = mock(StudyVo.class);
		boolean result = true;
		int seq = 3;
		
		when(studyService.delete(seq)).thenReturn(result);
		when(studyService.get(seq)).thenReturn(study);
		
		mockMvc.perform(delete("/studies/post/" + seq))
			.andExpect(status().isOk())
			.andExpect(content().json(new JSONObject().put("result", result).toString()));
	}
	
	
}
