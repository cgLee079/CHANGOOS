package com.cglee079.changoos.service;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
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
		List<BoardFileVo> files = mock(List.class);
		List<BoardImageVo> images = mock(List.class);
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		
		ProjectVo expectProject = new ProjectVo();
		expectProject.setSeq(seq);
		expectProject.setFiles(files);
		expectProject.setImages(images);
		
		when(projectDao.get(seq)).thenReturn(project);
		when(boardImageService.list(imageTB, seq)).thenReturn(images);
		when(boardFileService.list(fileTB, seq)).thenReturn(files);
		
		//ACT
		ProjectVo resultProject = projectService.get(seq);
		
		//ASSERT
		assertEquals(resultProject, expectProject);
	}
	
	@Test
	public void testGetWithContents() {
		int seq = 3;
		List<BoardFileVo> files = mock(List.class);
		List<BoardImageVo> images = mock(List.class);
		String contents = "&";
		String newContents = "&amp;";
		
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		project.setContents(contents);
		
		ProjectVo expectProject = new ProjectVo();
		expectProject.setSeq(seq);
		expectProject.setContents(newContents);
		expectProject.setFiles(files);
		expectProject.setImages(images);
		
		when(projectDao.get(seq)).thenReturn(project);
		when(boardImageService.list(imageTB, seq)).thenReturn(images);
		when(boardFileService.list(fileTB, seq)).thenReturn(files);
		
		//ACT
		ProjectVo resultProject = projectService.get(seq);
		
		//ASSERT
		assertEquals(resultProject, expectProject);
	}
	
	@Test
	public void testGetBefore() {
		int seq = 3;
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		
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
		project.setSeq(seq);
		
		when(projectDao.getAfter(seq)).thenReturn(project);
		
		//ACT
		ProjectVo resultProject = projectService.getAfter(seq);
		
		//ASSERT
		assertEquals(project, resultProject);
	}
	
	@Test
	//TODO 어떻게하라는거야..정적클래스
	public void testDoView() {
		
	}
	
	@Test
	public void testList() {
		HashMap<String, Object> map = mock(HashMap.class);
		List<ProjectVo> projects = mock(List.class);
		
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
		int expect = projectService.insert(project, thumbnailFile, imageValues, fileValues);
		
		//ASSERT
		assertEquals(seq, expect);
		assertEquals(project, expectProject);
		verify(boardFileService).insertFiles(fileTB, fileDir, seq, fileValues);
		verify(projectService).saveThumbnail(project, thumbnailFile);
		verify(boardImageService).insertImages(imageTB, imageDir, seq, contents, imageValues);
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
		boolean result  = true;

		ProjectVo project = new ProjectVo();
		project.setContents(contents);
		project.setSeq(seq);
		
		ProjectVo expectProject = new ProjectVo();
		expectProject.setContents(newContents);
		expectProject.setThumbnail(thumbnail);
		expectProject.setSeq(seq);
		expectProject.setHits(0);
		
		when(projectDao.update(project)).thenReturn(result);
		doReturn(thumbnail).when(projectService).saveThumbnail(project, thumbnailFile);
		when(boardImageService.insertImages(imageTB, imageDir, seq, contents, imageValues)).thenReturn(newContents);
		
		//ACT
		boolean expect = projectService.update(project, thumbnailFile, imageValues, fileValues);
		
		//ASSERT
		assertEquals(result, expect);
		assertEquals(project, expectProject);
		verify(boardFileService).insertFiles(fileTB, fileDir, seq, fileValues);
		verify(projectService).saveThumbnail(project, thumbnailFile);
		verify(boardImageService).insertImages(imageTB, imageDir, seq, contents, imageValues);
		verify(projectDao).update(project);
	}
	
	@Test
	//TODO 어떻게하라는거야..싱글톤 ㅠㅠ
	public void testDeleteResultTrue() {
		int seq = 3;
		boolean result = true;
		List<BoardFileVo> files = mock(ArrayList.class);
		List<BoardImageVo> images = mock(ArrayList.class);
		BoardFileVo file = mock(BoardFileVo.class);
		BoardImageVo image = mock(BoardImageVo.class);
		String thumbnail = "SAMPLE_THUMBNAIL";
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		project.setThumbnail(thumbnail);
		
		when(boardFileService.list(fileTB, seq)).thenReturn(files);
		when(boardImageService.list(imageTB, seq)).thenReturn(images);
		when(files.size()).thenReturn(3);
		when(images.size()).thenReturn(3);
		when(files.get(anyInt())).thenReturn(file);
		when(images.get(anyInt())).thenReturn(image);
		when(projectDao.get(seq)).thenReturn(project);
		when(projectDao.delete(seq)).thenReturn(result);
		
		//ACT
		boolean expected = projectService.delete(seq);
		
		//ASSERT
		assertEquals(result, expected);
		verify(fileHandler).delete(realPath + thumbDir, project.getThumbnail());
		verify(fileHandler, times(3)).delete(realPath + fileDir, file.getPathname());
		verify(fileHandler, times(3)).delete(realPath + imageDir, image.getPathname());
		
	}
	
	@Test
	public void testDeleteResultFalse() {
		int seq = 3;
		boolean result = false;
		ProjectVo project = new ProjectVo();
		project.setSeq(seq);
		
		when(projectDao.get(seq)).thenReturn(project);
		when(projectDao.delete(seq)).thenReturn(result);
		
		//ACT
		boolean expected = projectService.delete(seq);
		
		//ASSERT
		assertEquals(result, expected);
	}
	
	@Test
	public void testSaveThumbnailWithGifFile() throws IllegalStateException, IOException {
		ProjectVo project = mock(ProjectVo.class);
		MultipartFile thumbnailFile = mock(MultipartFile.class);
		long filesize = 1024;
		String filename = "SAMPLE_FILENAME.GIF";
		
		when(thumbnailFile.getSize()).thenReturn(filesize);
		when(thumbnailFile.getOriginalFilename()).thenReturn(filename);
		
		//ACT
		String resultPathname = projectService.saveThumbnail(project, thumbnailFile);
		
		//ASSERT
		verify(fileHandler).delete(realPath + thumbDir, project.getThumbnail());
		verify(thumbnailFile).transferTo(new File(realPath + thumbDir, resultPathname));
	}
	
	@Test
	public void testSaveThumbnailWithNoneGifFile() throws IllegalStateException, IOException {
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
		MultipartFile thumbnailFile = spy(MultipartFile.class);
		
		long filesize = 0;
		
		doReturn(filesize).when(thumbnailFile).getSize();
		
		//ACT
		String resultPathname = projectService.saveThumbnail(project, thumbnailFile);
		
		//ASSERT
		assertEquals(null, resultPathname);
	}
	
}
