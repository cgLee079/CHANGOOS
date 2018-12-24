package com.cglee079.changoos.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.model.StudyFileVo;
import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.service.StudyService;
import com.cglee079.changoos.util.PathHandler;
import com.google.gson.Gson;

@RunWith(PowerMockRunner.class)
@PrepareForTest({PathHandler.class})
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class StudyControllerTest {
	@Mock
	private StudyService studyService;

	@Mock
	private PathHandler commonService;

	@InjectMocks
	private StudyController studyController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(studyController).build();
		
		PowerMockito.mockStatic(PathHandler.class);
	}
	
	@Test
	public void testStudyList() throws Exception {
		Map<String, Object> param = new HashMap<>();
		List<String> categories = new ArrayList<String>();
		int count = 1;
		
		when(studyService.getCategories()).thenReturn(categories);
		when(studyService.count(param)).thenReturn(count);
		
		mockMvc.perform(get("/study"))
			.andExpect(status().isOk())
			.andExpect(view().name("study/study_list"))
			.andExpect(model().attribute("count", count))
			.andExpect(model().attribute("categories", categories));
			
	}
	
	@Test
	public void testStudyListPaging() throws Exception {
		Map<String, Object> params = new HashMap<>();
		List<StudyVo> studies = new ArrayList<StudyVo>();
		int count = 1;
				
		when(studyService.paging(params)).thenReturn(studies);
		when(studyService.count(params)).thenReturn(count);
		
		String data = new Gson().toJson(studies);
		JSONArray dataJson = new JSONArray(data);
		JSONObject result = new JSONObject();
		result.put("count", count);
		result.put("data", dataJson);
		
		mockMvc.perform(get("/study/paging"))
		.andExpect(status().isOk())
		.andExpect(content().json(result.toString()));
	}
	
	
	@Test
	public void testStudyView() throws Exception {
		List<StudyFileVo> files = new ArrayList<StudyFileVo>();
		List<Integer> visitStudies = new ArrayList<Integer>();
		String category = "category_sample";
		int seq = 3;
		int page = 1;
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("visitStudies", visitStudies);
		
		StudyVo study 		= new StudyVo();
		StudyVo beforeStudy = new StudyVo();
		StudyVo afterStudy 	= new StudyVo();
		study.setFiles(files);
		
		when(studyService.doView((List<Integer>)session.getAttribute("visitStudies"), seq)).thenReturn(study);
		when(studyService.getBefore(seq, category)).thenReturn(beforeStudy);
		when(studyService.getAfter(seq, category)).thenReturn(afterStudy);
		
		mockMvc.perform(get("/study/view")
				.session(session)
				.param("category", category)
				.param("page", String.valueOf(page))
				.param("seq", String.valueOf(seq)))
				.andExpect(status().isOk())
				.andExpect(view().name("study/study_view"))
				.andExpect(model().attribute("category", category))
				.andExpect(model().attribute("page", page))
				.andExpect(model().attribute("beforeStudy", beforeStudy))
				.andExpect(model().attribute("study", study))
				.andExpect(model().attribute("afterStudy", afterStudy))
				.andExpect(model().attribute("files", files));
	}
	
	@Test
	public void testStudyManage() throws Exception {
		mockMvc.perform(get("/mgnt/study"))
				.andExpect(status().isOk())
				.andExpect(view().name("study/study_manage"));
		
	}	
	
	@Test
	public void testStudyManagePaging() throws Exception {
		Map<String, Object> params = new HashMap<>();
		List<StudyVo> studies = new ArrayList<StudyVo>();
				
		when(studyService.list(params)).thenReturn(studies);
		
		mockMvc.perform(get("/mgnt/study/paging"))
		.andExpect(status().isOk())
		.andExpect(content().json(new Gson().toJson(studies).toString()));
		
	}
	
	public void testStudyUpload() throws Exception {
		mockMvc.perform(get("/mgnt/study/upload"))
				.andExpect(status().isOk())
				.andExpect(view().name("study/study_upload"));
		
	}	
	
	@Test
	public void testStudyModify() throws Exception {
		List<StudyFileVo> files = new ArrayList<StudyFileVo>();
		String contents = "contents_sample";
		String newContents = "newContents_sample";
		int seq = 3;
		
		StudyVo study = new StudyVo();
		study.setContents(contents);
		study.setFiles(files);
		
		when(studyService.get(seq)).thenReturn(study);
		when(PathHandler.copyToTempPath(study.getContents(), Path.STUDY_IMAGE_PATH)).thenReturn(newContents);
		
		mockMvc.perform(get("/mgnt/study/upload")
				.param("seq", String.valueOf(seq)))
				.andExpect(status().isOk())
				.andExpect(view().name("study/study_upload"))
				.andExpect(model().attribute("study", study))
				.andExpect(model().attribute("files", files))
				;
	}	
	
	@Test
	public void testStudyDoUpload() throws Exception {
		String contents = "contents_sample";
		String newcontents = "newcontents_sample";
		int seq = 3;
		
		StudyVo study = mock(StudyVo.class);
		study.setContents(contents);
		
		when(PathHandler.changeImagePath(study.getContents(), Path.STUDY_IMAGE_PATH)).thenReturn(newcontents);
		when(studyService.insert(any(StudyVo.class), anyObject())).thenReturn(seq);
		
		mockMvc.perform(fileUpload("/mgnt/study/upload.do")
				.param("contents", contents))
				.andExpect(redirectedUrl("/study/view?seq=" + String.valueOf(seq)))
				.andExpect(status().isFound());
	}
	
	@Test
	public void testStudyDoModify() throws Exception {
		String contents = "contents_sample";
		String newcontents = "newcontents_sample";
		int seq = 3;
		
		StudyVo study = mock(StudyVo.class);
		study.setContents(contents);
		
		when(PathHandler.changeImagePath(study.getContents(), Path.STUDY_IMAGE_PATH)).thenReturn(newcontents);
		when(studyService.insert(any(StudyVo.class), anyObject())).thenReturn(seq);
		
		mockMvc.perform(fileUpload("/mgnt/study/upload.do")
				.param("contents", contents)
				.param("seq", String.valueOf(seq)))
				.andExpect(redirectedUrl("/study/view?seq=" + String.valueOf(seq)))
				.andExpect(status().isFound());
	}
	
	@Test
	public void testStudyDoDelete() throws Exception{
		StudyVo study = mock(StudyVo.class);
		boolean result = true;
		int seq = 3;
		
		when(studyService.delete(seq)).thenReturn(result);
		when(studyService.get(seq)).thenReturn(study);
		
		mockMvc.perform(post("/mgnt/study/delete.do")
				.param("seq", String.valueOf(seq)))
				.andExpect(status().isOk())
				.andExpect(content().json(new JSONObject().put("result", result).toString()));
	}
	
	
}
