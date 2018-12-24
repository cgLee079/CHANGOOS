package com.cglee079.changoos.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
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

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.model.ProjectFileVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.service.ProjectService;
import com.cglee079.changoos.util.PathHandler;
import com.cglee079.changoos.util.MyFileUtils;
import com.google.gson.Gson;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FileUtils.class, MyFileUtils.class, PathHandler.class})
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
		
		PowerMockito.mockStatic(PathHandler.class);
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
		cProject.setFiles(files);
		
		when(projectService.doView((List<Integer>)session.getAttribute("visitProjects"), seq)).thenReturn(cProject);
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
		String contents = "contents_sample";
		String newContents = "newContents_sample";
		
		ProjectVo cProject = new ProjectVo();
		cProject.setSeq(seq);
		cProject.setContents(contents);
		cProject.setFiles(files);
		
		when(projectService.get(seq)).thenReturn(cProject);
		when(PathHandler.copyToTempPath(cProject.getContents(), Path.PROJECT_IMAGE_PATH)).thenReturn(newContents);
		
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
		String snaphtPath = "snapsht_sample";
		String contents = "contents_sample";
		String newContents = "newContents_sample";
		
		MockMultipartFile snapshtFile = new MockMultipartFile("snapshot", new byte[1]);
		
		when(projectService.saveSnapsht(any(ProjectVo.class), eq(snapshtFile))).thenReturn(snaphtPath);
		when(projectService.insert(any(ProjectVo.class), anyObject(), anyObject())).thenReturn(seq);
		when(PathHandler.changeImagePath(contents, Path.PROJECT_IMAGE_PATH)).thenReturn(newContents);
		
		mockMvc.perform(fileUpload("/mgnt/project/upload.do")
			.file(snapshtFile)
			.file("file01", new byte[1])
			.file("file02", new byte[1])
			.file("file03", new byte[1])
			.param("contents", contents))
			.andExpect(redirectedUrl("/project/view?seq=" + seq))
			.andExpect(status().isFound());
		
	}
	
	@Test
	public void testProjectDoModify() throws JSONException, Exception {
		int seq = 3;
		String snaphtPath = "snapsht_sample";
		String contents = "content_sample";
		String newContents = "newContent_sample";
		
		MockMultipartFile snapshtFile = new MockMultipartFile("snapshot", new byte[1]);
		
		when(projectService.saveSnapsht(any(ProjectVo.class), eq(snapshtFile))).thenReturn(snaphtPath);
		when(projectService.insert(any(ProjectVo.class), anyObject(), anyObject())).thenReturn(seq);
		when(PathHandler.changeImagePath(contents, Path.PROJECT_IMAGE_PATH)).thenReturn(newContents);
		
		mockMvc.perform(fileUpload("/project/upload.do")
			.file(snapshtFile)
			.file("file01", new byte[1])
			.file("file02", new byte[1])
			.file("file03", new byte[1])
			.param("content", contents)
			.param("seq", String.valueOf(seq)))
			.andExpect(redirectedUrl("/mgnt/project"))
			.andExpect(status().isFound());
	}
	
	/*  파일 다운로드 어떻게 처리해야하나...*/
//	@Test
//	public void testProjectDoFiledownload() throws Exception {
//		String filename = "경로_파일명";
//		String realNm = "파일명";
//		
//		ProjectFileVo projectFile = new ProjectFileVo();
//		projectFile.setPathNm(filename);
//		projectFile.setRealNm(realNm);
//		
//		File file = mock(File.class);
//		when(file.exists()).thenReturn(true);
//		when(file.getName()).thenReturn("MockingFile");
//		
//		System.out.println(file.getName());
//		byte[] fileByte = new byte[1];
//		
//		PowerMockito.mockStatic(FileUtils.class);
//		PowerMockito.mockStatic(MyFileUtils.class);
//		PowerMockito.whenNew(File.class).withArguments(any()).thenReturn(file);
//
//
//		MockHttpSession session = new MockHttpSession();
//		when(projectFileService.get(filename)).thenReturn(projectFile);
//		when(FileUtils.readFileToByteArray(file)).thenReturn(fileByte);
//		when(MyFileUtils.encodeFilename(anyObject(), eq(projectFile.getRealNm()))).thenReturn("S");
//	
//		
//		mockMvc.perform(get("/project/download.do")
//				.session(session)
//				.param("filename", filename))
//				.andExpect(status().isOk())
//				.andReturn();
//	}
	
	@Test
	public void testProjectDoFileDelete() throws Exception {
		int fileSeq = 3;
		when(projectService.deleteFile(fileSeq)).thenReturn(true);
		
		mockMvc.perform(post("/mgnt/project/file/delete.do")
				.param("seq", String.valueOf(fileSeq)))
				.andExpect(status().isOk())
				.andExpect(content().json(new JSONObject().put("result", true).toString()));
	}
}
