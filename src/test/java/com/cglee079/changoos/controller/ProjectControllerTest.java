package com.cglee079.changoos.controller;

import static org.mockito.Matchers.any;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.service.ProjectService;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class ProjectControllerTest {
	@Mock
	private ProjectService projectService;
	
	@InjectMocks
	private ProjectController projectController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
	}
	
	@Test
	public void testProjectList() throws Exception {
		List<ProjectVo> lists = new ArrayList<ProjectVo>();
		when(projectService.list(null)).thenReturn(lists);
		
		mockMvc.perform(get("/project"))
			.andExpect(status().isOk())
			.andExpect(view().name("project/project_list"))
			.andExpect(model().attribute("projects", lists));
	}
	
	@Test
	public void testProjectView() throws Exception {
		Set<Integer> visitProjects = new HashSet<Integer>();
		List<BoardFileVo> files = new ArrayList<>();
		int seq = 3;
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("visitProjects", visitProjects);
		
		ProjectVo bProject = ProjectVo.builder()
				.seq(seq-1)
				.build();
		ProjectVo cProject = ProjectVo.builder()
				.seq(seq)
				.files(files)
				.build();
		ProjectVo aProject = ProjectVo.builder()
				.seq(seq+1)
				.build();
		
		when(projectService.doView((Set<Integer>)session.getAttribute("visitProjects"), seq)).thenReturn(cProject);
		when(projectService.getBefore(seq)).thenReturn(bProject);
		when(projectService.getAfter(seq)).thenReturn(aProject);

		
		mockMvc.perform(get("/project/view")
				.session(session)
				.param("seq", String.valueOf(seq)))
			.andExpect(status().isOk())
			.andExpect(view().name("project/project_view"))
			.andExpect(model().attribute("project", cProject))
			.andExpect(model().attribute("beforeProject", bProject))
			.andExpect(model().attribute("afterProject", aProject))
			.andExpect(model().attribute("files", files));
		
	}
	
	
	@Test
	public void testProjectManage() throws Exception {
		mockMvc.perform(get("/mgnt/project"))
		.andExpect(status().isOk())
		.andExpect(view().name("project/project_manage"));
	}
	
	@Test
	public void testprojectManagePaging() throws Exception {
		Map<String,Object> map = new HashMap<>();
		
		List<ProjectVo> projects = new ArrayList<>();
		
		ProjectVo project1 = ProjectVo.builder()
				.seq(1)
				.build();
		ProjectVo project2 = ProjectVo.builder()
				.seq(2)
				.build();
		
		projects.add(project1);
		projects.add(project2);
		
		when(projectService.list(map)).thenReturn(projects);
		
		mockMvc.perform(get("/mgnt/project/list.do"))
			.andExpect(status().isOk())
			.andExpect(content().json(new Gson().toJson(projects).toString()));
	}
	
	@Test
	public void testProjectUpload() throws Exception {
		mockMvc.perform(get("/mgnt/project/upload"))
			.andExpect(status().isOk())
			.andExpect(view().name("project/project_upload"));
	}
	
	@Test
	public void testProjectModify() throws Exception {
		List<BoardFileVo> files = new ArrayList<>();
		List<BoardImageVo> images = new ArrayList<BoardImageVo>();
		
		int seq = 3;
		
		ProjectVo project = ProjectVo.builder()
				.seq(seq)
				.files(files)
				.images(images)
				.build();
		
		when(projectService.get(seq)).thenReturn(project);
		
		mockMvc.perform(get("/mgnt/project/upload")
				.param("seq", String.valueOf(seq)))
			.andExpect(status().isOk())
			.andExpect(view().name("project/project_upload"))
			.andExpect(model().attribute("project", project))
			.andExpect(model().attribute("files", files))
			.andExpect(model().attribute("images", images));
	}
	
	@Test
	public void testProjectDoUpload() throws JSONException, Exception {
		MockMultipartFile thumbnailFile = new MockMultipartFile("thumbnailFile", new byte[1]);
		String imageValues = "SAMPLE_IMAGEVALUES";
		String fileValues = "SAMPLE_FILEVALUES";
		int seq = 3;
		
		when(projectService.insert(any(ProjectVo.class), same(thumbnailFile), same(imageValues), same(fileValues))).thenReturn(seq);
		
		mockMvc.perform(fileUpload("/mgnt/project/upload.do")
				.file(thumbnailFile)
				.param("imageValues", imageValues)
				.param("fileValues", fileValues))
			.andExpect(redirectedUrl("/project/view?seq=" + seq))
			.andExpect(status().isFound());
		
		verify(projectService).insert(any(ProjectVo.class), same(thumbnailFile), same(imageValues), same(fileValues));
		
	}
	
	@Test
	public void testProjectDoModify() throws JSONException, Exception {
		MockMultipartFile thumbnailFile = new MockMultipartFile("thumbnailFile", new byte[1]);
		String imageValues = "SAMPLE_IMAGEVALUES";
		String fileValues = "SAMPLE_FILEVALUES";
		int seq = 3;
		
		when(projectService.update(any(ProjectVo.class), same(thumbnailFile), same(imageValues), same(fileValues))).thenReturn(true);
		
		mockMvc.perform(fileUpload("/mgnt/project/upload.do")
				.file(thumbnailFile)
				.param("seq", String.valueOf(seq))
				.param("imageValues", imageValues)
				.param("fileValues", fileValues))
			.andExpect(redirectedUrl("/project/view?seq=" + seq))
			.andExpect(status().isFound());
		
		verify(projectService).update(any(ProjectVo.class), same(thumbnailFile), same(imageValues), same(fileValues));
	}
	
	@Test
	public void testProjectDoDelete() throws JSONException, Exception {
		int seq = 3;
		
		when(projectService.delete(seq)).thenReturn(true);
		
		mockMvc.perform(post("/mgnt/project/delete.do")
				.param("seq", String.valueOf(seq)))
			.andExpect(status().isOk())
			.andExpect(content().json(new JSONObject().put("result", true).toString()));
	}
}
