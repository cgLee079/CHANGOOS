package com.cglee079.changoos.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.model.ProjectFileVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.service.CommonService;
import com.cglee079.changoos.service.ProjectFileService;
import com.cglee079.changoos.service.ProjectService;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class ProjectControllerTest {
	@Mock
	private CommonService commonService;
	
	@Mock
	private ProjectService projectService;
	
	@Mock
	private ProjectFileService projectFileService;
	
	@InjectMocks
	private ProjectController projectController;
	
	private MockMvc mockMvc;
	
	@Before
	public void init() {
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
		List<Integer> visitProjects = new ArrayList<Integer>();
		List<ProjectFileVo> files = new ArrayList<>();
		int seq = 3;
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("visitProjects", visitProjects);
		
		ProjectVo bProject = new ProjectVo();
		ProjectVo cProject = new ProjectVo();
		ProjectVo aProject = new ProjectVo();
		bProject.setSeq(seq - 1);
		cProject.setSeq(seq);
		aProject.setSeq(seq + 1);
		
		when(projectService.doView((List<Integer>)session.getAttribute("visitProjects"), seq)).thenReturn(cProject);
		when(projectService.getBefore(seq)).thenReturn(bProject);
		when(projectService.getAfter(seq)).thenReturn(aProject);
		when(projectFileService.list(seq)).thenReturn(files);

		
		mockMvc.perform(get("/project/view").session(session).param("seq", String.valueOf(seq)))
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
	public void testprojectManageList() throws Exception {
		Map<String,Object> map = new HashMap<>();
		
		List<ProjectVo> projects = new ArrayList<>();
		ProjectVo project;
		
		project = new ProjectVo();
		project.setSeq(1);
		projects.add(project);
		
		project = new ProjectVo();
		project.setSeq(2);
		projects.add(project);
		
		when(projectService.list(map)).thenReturn(projects);
		
		mockMvc.perform(get("/mgnt/project/list.do"))
		.andExpect(status().isOk())
		.andDo(print())
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
		List<ProjectFileVo> files = new ArrayList<>();
		int seq = 3;
		
		ProjectVo cProject = new ProjectVo();
		cProject.setSeq(seq);
		cProject.setContents("contents");
		
		when(projectService.get(seq)).thenReturn(cProject);
		when(projectFileService.list(seq)).thenReturn(files);
		when(commonService.copyToTempPath(cProject.getContents(), Path.PROJECT_CONTENTS_PATH)).thenReturn("modify cotents");
		
		mockMvc.perform(get("/mgnt/project/upload").param("seq", String.valueOf(seq)))
		.andExpect(status().isOk())
		.andExpect(view().name("project/project_upload"))
		.andExpect(model().attribute("project", cProject))
		.andExpect(model().attribute("files", files));
	}
	
	@Test
	public void testProjectDoDelete() throws JSONException, Exception {
		int seq = 3;
		
		ProjectVo cProject = new ProjectVo();
		cProject.setSeq(seq);
		cProject.setContents("contents");
		
		when(projectService.get(seq)).thenReturn(cProject);
		when(projectService.delete(seq)).thenReturn(true);
		
		mockMvc.perform(post("/mgnt/project/delete.do").param("seq", String.valueOf(seq)))
		.andExpect(status().isOk())
		.andExpect(content().json(new JSONObject().put("result", true).toString()));
	}
	
	@Test
	public void testProjectDoUpload() throws JSONException, Exception {
		int seq = 3;
		String snaphtPath = "Snapshot Path";
		String content = "content";
		String modifyContent = "modifyContent";
		
		ProjectVo project = new ProjectVo();
		project.setContents(content);
		project.setSnapsht(snaphtPath);
		
		MockMultipartFile snapshtFile = new MockMultipartFile("snapshot", new byte[1]);
		
		when(projectService.saveSnapsht(project, snapshtFile)).thenReturn(snaphtPath);
		when(projectService.insert(project)).thenReturn(seq);
		when(commonService.moveToSavePath(project.getContents(), Path.PROJECT_CONTENTS_PATH)).thenReturn(modifyContent);
		
		mockMvc.perform(fileUpload("/mgnt/project/upload.do")
			.file(snapshtFile)
			.file("file01", new byte[1])
			.file("file02", new byte[1])
			.file("file03", new byte[1])
			.param("content", content))
			.andExpect(redirectedUrl("/mgnt/project"))
			.andExpect(status().isFound());
		
	}
	
	@Test
	public void testProjectDoModify() throws JSONException, Exception {
		int seq = 3;
		String snaphtPath = "Snapshot Path";
		String content = "content";
		String modifyContent = "modifyContent";
		
		ProjectVo project = new ProjectVo();
		project.setContents(content);
		project.setSnapsht(snaphtPath);
		
		MockMultipartFile snapshtFile = new MockMultipartFile("snapshot", new byte[1]);
		
		when(projectService.saveSnapsht(project, snapshtFile)).thenReturn(snaphtPath);
		when(projectService.insert(project)).thenReturn(seq);
		when(commonService.moveToSavePath(project.getContents(), Path.PROJECT_CONTENTS_PATH)).thenReturn(modifyContent);
		
		mockMvc.perform(fileUpload("/mgnt/project/upload.do")
			.file(snapshtFile)
			.file("file01", new byte[1])
			.file("file02", new byte[1])
			.file("file03", new byte[1])
			.param("content", content)
			.param("seq", String.valueOf(seq)))
			.andExpect(redirectedUrl("/mgnt/project"))
			.andExpect(status().isFound());
	}
	
}
