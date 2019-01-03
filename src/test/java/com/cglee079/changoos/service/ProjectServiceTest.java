package com.cglee079.changoos.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.ProjectDao;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.util.FileHandler;
import com.cglee079.changoos.util.ImageHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class ProjectServiceTest {
	
	@Mock private BoardImageService boardImageService;
	@Mock private BoardFileService boardFileService;
	@Mock private ProjectDao projectDao;
	@Mock private ImageHandler imageHandler;
	@Mock private FileHandler fileHandler;
	
	@Value("#{servletContext.getRealPath('/')}") private String realPath;
	
	@Value("#{location['project.file.dir.url']}") 	private String fileDir;
	@Value("#{location['project.image.dir.url']}")	private String imageDir;
	@Value("#{location['project.thumb.dir.url']}") 	private String thumbDir;
	
	@Value("#{db['project.file.tb.name']}") private String fileTB;
	@Value("#{db['project.image.tb.name']}") private String imageTB;
	
	@Value("#{constant['project.thumb.max.width']}")private int thumbMaxWidth;
	
	@Spy
	@InjectMocks
	private ProjectService projectService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		ReflectionTestUtils.setField(projectService, "realPath", realPath);
		ReflectionTestUtils.setField(projectService, "fileDir", fileDir);
		ReflectionTestUtils.setField(projectService, "imageDir", imageDir);
		ReflectionTestUtils.setField(projectService, "thumbDir", thumbDir);
		ReflectionTestUtils.setField(projectService, "fileTB", fileTB);
		ReflectionTestUtils.setField(projectService, "imageTB", imageTB);
		ReflectionTestUtils.setField(projectService, "thumbMaxWidth", thumbMaxWidth);
	}

	@Test
	public void testGetWithoutContent() {
		int seq = 3;
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		
		ProjectVo expectProject = new ProjectVo();
		expectProject.setSeq(seq);
		
		when(projectDao.get(seq)).thenReturn(project);
		
		//ACT
		ProjectVo resultProject = projectService.get(seq);
		
		//ASSERT
		assertEquals(expectProject, resultProject);
	}
	
	@Test
	public void testGetWithContents() {
		int seq = 3;
		String contents = "&";
		String newContents = "&amp;";
		
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		project.setContents(contents);
		
		ProjectVo expectProject = new ProjectVo();
		expectProject.setSeq(seq);
		expectProject.setContents(newContents);
		
		when(projectDao.get(seq)).thenReturn(project);
		
		//ACT
		ProjectVo resultProject = projectService.get(seq);
		
		//ASSERT
		assertEquals(expectProject, resultProject);
	}
	
	@Test
	public void testGetBefore() {
		int seq = 3;
		ProjectVo project = new ProjectVo();
		project.setSeq(seq - 1);
		
		when(projectDao.getBefore(seq)).thenReturn(project);
		
		//ACT
		ProjectVo resultProject = projectService.getBefore(seq);
		
		//ASSERT
		assertEquals(project, resultProject);
	}
	
	@Test
	public void testGetAfter() {
		int seq = 3;
		ProjectVo project = new ProjectVo();
		project.setSeq(seq + 1);
		
		when(projectDao.getAfter(seq)).thenReturn(project);
		
		//ACT
		ProjectVo resultProject = projectService.getAfter(seq);
		
		//ASSERT
		assertEquals(project, resultProject);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDoViewVisitedByAdmin() {
		int seq = 3;
		Set<Integer> visitProjects = new HashSet<>();
		visitProjects.add(seq);
		
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		project.setHits(0);
		
		when(projectDao.get(seq)).thenReturn(project);
		
		ProjectVo expectedProject = new ProjectVo();
		expectedProject.setSeq(seq);
		expectedProject.setHits(0);
		
		//ACT
		ProjectVo resultProject = projectService.doView(visitProjects, seq);
		
		//ASSERT
		assertEquals(expectedProject, resultProject);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDoViewNoneVisitedByAdmin() {
		int seq = 3;
		Set<Integer> visitProjects = new HashSet<>();
		
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		project.setHits(0);
		
		when(projectDao.get(seq)).thenReturn(project);
		
		ProjectVo expectedProject = new ProjectVo();
		expectedProject.setSeq(seq);
		expectedProject.setHits(0);
		
		//ACT
		ProjectVo resultProject = projectService.doView(visitProjects, seq);
		
		//
		assertFalse(visitProjects.contains(seq));
		assertEquals(expectedProject, resultProject);
	}
	
	@Test
	public void testDoViewVisitedByUser() {
		int seq = 3;
		Set<Integer> visitProjects = new HashSet<>();
		visitProjects.add(seq);
		
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		project.setHits(0);
		
		when(projectDao.get(seq)).thenReturn(project);
		
		ProjectVo expectedProject = new ProjectVo();
		expectedProject.setSeq(seq);
		expectedProject.setHits(0);
		
		//ACT
		ProjectVo resultProject = projectService.doView(visitProjects, seq);
		
		//ASSERT
		assertEquals(expectedProject, resultProject);
	}
	
	@Test
	public void testDoViewNoneVisitedByUser() {
		int seq = 3;
		Set<Integer> visitProjects = new HashSet<>();
		
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		project.setHits(0);
		
		when(projectDao.get(seq)).thenReturn(project);
		
		ProjectVo expectedProject = new ProjectVo();
		expectedProject.setSeq(seq);
		expectedProject.setHits(1);
		
		//ACT
		ProjectVo resultProject = projectService.doView(visitProjects, seq);
		
		//ASSERT
		assertTrue(visitProjects.contains(seq));
		assertEquals(expectedProject, resultProject);
		verify(projectDao).update(project);
	}
	
	@Test
	public void testList() {
		HashMap<String, Object> map = new HashMap<>();
		List<ProjectVo> projects = new ArrayList<>();
		
		when(projectDao.list(map)).thenReturn(projects);
		
		//ACT
		List<ProjectVo> resultProjects = projectService.list(map);
		
		//ASSERT
		assertEquals(projects, resultProjects);
	}
	
	@Test
	public void testInsert() throws Exception {
		int seq = 3;
		MockMultipartFile thumbnailFile = new MockMultipartFile("thumbnailFile", new byte[1]);
		String contents = "SAMPLE_CONTENTS";
		String fileValues = "SAMPLE_FILEVALUES";
		String imageValues = "SAMPLE_IMAGEVALUES";
		String thumbnail = "SAMPLE_THUMBNAIL";
		String newContents = "SAMPLE_NEWCONTENTS";
		
		ProjectVo project = new ProjectVo();
		project.setContents(contents);
		project.setSeq(seq);
		
		ProjectVo expectProject = new ProjectVo();
		expectProject.setContents(newContents);
		expectProject.setThumbnail(thumbnail);
		expectProject.setSeq(seq);
		expectProject.setHits(0);
		
		doReturn(thumbnail).when(projectService).saveThumbnail(project, thumbnailFile);
		when(projectDao.insert(project)).thenReturn(seq);
		when(boardImageService.insertImages(imageTB, imageDir, seq, contents, imageValues)).thenReturn(newContents);
		
		//ACT
		int result = projectService.insert(project, thumbnailFile, imageValues, fileValues);
		
		//ASSERT
		assertEquals(seq, result);
		assertEquals(expectProject, project);
		verify(boardFileService).insertFiles(fileTB, fileDir, seq, fileValues);
		verify(projectDao).update(project);
	}
	
	@Test
	public void testUpdate() throws Exception {
		int seq = 3;
		MockMultipartFile thumbnailFile = new MockMultipartFile("thumbnailFile", new byte[1]);
		String fileValues = "SAMPLE_FILEVALUES";
		String imageValues = "SAMPLE_IMAGEVALUES";
		String thumbnail = "SAMPLE_THUMBNAIL";
		String contents = "SAMPLE_CONTENTS";
		String newContents = "SAMPLE_NEWCONTENTS";
		boolean expect  = true;

		ProjectVo project = new ProjectVo();
		project.setContents(contents);
		project.setSeq(seq);
		
		ProjectVo expectProject = new ProjectVo();
		expectProject.setContents(newContents);
		expectProject.setThumbnail(thumbnail);
		expectProject.setSeq(seq);
		
		when(projectDao.update(project)).thenReturn(expect);
		doReturn(thumbnail).when(projectService).saveThumbnail(project, thumbnailFile);
		when(boardImageService.insertImages(imageTB, imageDir, seq, contents, imageValues)).thenReturn(newContents);
		
		//ACT
		boolean result = projectService.update(project, thumbnailFile, imageValues, fileValues);
		
		//ASSERT
		assertEquals(expect, result);
		assertEquals(expectProject, project);
		verify(boardFileService).insertFiles(fileTB, fileDir, seq, fileValues);
	}
	
	@Test
	public void testDeleteResultTrue() {
		int seq = 3;
		boolean expected = true;
		String thumbnail = "SAMPLE_THUMBNAIL";
		List<BoardFileVo> files = new ArrayList<>();
		List<BoardImageVo> images = new ArrayList<>();
		files.add(mock(BoardFileVo.class));
		files.add(mock(BoardFileVo.class));
		images.add(mock(BoardImageVo.class));
		images.add(mock(BoardImageVo.class));
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		project.setThumbnail(thumbnail);
		project.setImages(images);
		project.setFiles(files);
		when(projectDao.get(seq)).thenReturn(project);
		when(projectDao.delete(seq)).thenReturn(expected);
		
		//ACT
		boolean result = projectService.delete(seq);
		
		//ASSERT
		assertEquals(expected, result);
		verify(fileHandler).delete(realPath + thumbDir, project.getThumbnail());
		verify(fileHandler, times(2)).delete(eq(realPath + fileDir), anyString());
		verify(fileHandler, times(2)).delete(eq(realPath + imageDir), anyString());
	}
	
	@Test
	public void testDeleteResultFalse() {
		int seq = 3;
		boolean expected = false;
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		
		when(projectDao.get(seq)).thenReturn(project);
		when(projectDao.delete(seq)).thenReturn(expected);
		
		//ACT
		boolean result = projectService.delete(seq);
		
		//ASSERT
		assertEquals(expected, result);
		verify(fileHandler, atMost(0)).delete(anyString(), anyString());
	}
	
	@Test
	public void testSaveThumbnailWithFile() throws IllegalStateException, IOException {
		ProjectVo project = mock(ProjectVo.class);
		MultipartFile thumbnailFile = mock(MultipartFile.class);
		
		long filesize = 1024;
		String filename = "SAMPLE_FILENAME.PNG";
		
		when(thumbnailFile.getSize()).thenReturn(filesize);
		when(thumbnailFile.getOriginalFilename()).thenReturn(filename);
		
		//ACT
		String resultPathname = projectService.saveThumbnail(project, thumbnailFile);
		
		//ASSERT
		verify(fileHandler).delete(realPath + thumbDir, project.getThumbnail());
		verify(thumbnailFile).transferTo(any(File.class));
		verify(imageHandler).saveLowscaleImage(any(File.class), eq(thumbMaxWidth), eq("PNG"));
	}
	
	@Test
	public void testSaveThumbnailWithoutFile() throws IllegalStateException, IOException {
		ProjectVo project = mock(ProjectVo.class);
		MultipartFile thumbnailFile = mock(MultipartFile.class);
		
		long filesize = 0;
		
		when(thumbnailFile.getSize()).thenReturn(filesize);
		
		//ACT
		String resultPathname = projectService.saveThumbnail(project, thumbnailFile);
		
		//ASSERT
		assertEquals(null, resultPathname);
		verify(fileHandler, atMost(0)).delete(anyString(), anyString());
		verify(thumbnailFile, atMost(0)).transferTo(any(File.class));
		verify(imageHandler, atMost(0)).saveLowscaleImage(any(File.class), eq(thumbMaxWidth), anyString());
	}
	
}
