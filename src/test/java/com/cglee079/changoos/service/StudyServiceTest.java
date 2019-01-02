package com.cglee079.changoos.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.cglee079.changoos.dao.StudyDao;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.util.FileHandler;
import com.cglee079.changoos.util.Formatter;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class StudyServiceTest {
	
	@Mock private BoardImageService boardImageService;
	@Mock private BoardFileService boardFileService;
	@Mock private StudyDao studyDao;
	@Mock private FileHandler fileHandler;
	
	@Value("#{servletContext.getRealPath('/')}") private String realPath;
	@Value("#{location['study.file.dir.url']}") 	private String fileDir;
	@Value("#{location['study.image.dir.url']}")	private String imageDir;
	@Value("#{db['study.file.tb.name']}") private String fileTB;
	@Value("#{db['study.image.tb.name']}") private String imageTB;
	
	@Spy
	@InjectMocks
	private StudyService studyService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		ReflectionTestUtils.setField(studyService, "realPath", realPath);
		ReflectionTestUtils.setField(studyService, "fileDir", fileDir);
		ReflectionTestUtils.setField(studyService, "imageDir", imageDir);
		ReflectionTestUtils.setField(studyService, "fileTB", fileTB);
		ReflectionTestUtils.setField(studyService, "imageTB", imageTB);
	}

	@Test
	public void testCount() {
		Map<String, Object> params = new HashMap<>();
		
		int count = 3;
		
		when(studyDao.count(params)).thenReturn(count);
		
		//ACT
		int result = studyService.count(params);
		
		//ASSERT
		assertEquals(count, result);
	}
	
	@Test
	public void testGetWithoutContent() {
		int seq = 3;
		List<BoardFileVo> files = new ArrayList<>();
		List<BoardImageVo> images = new ArrayList<>();
		StudyVo study = new StudyVo();
		study.setSeq(seq);
		
		StudyVo expectStudy = new StudyVo();
		expectStudy.setSeq(seq);
		expectStudy.setFiles(files);
		expectStudy.setImages(images);
		
		when(studyDao.get(seq)).thenReturn(study);
		
		//ACT
		StudyVo resultStudy = studyService.get(seq);
		
		//ASSERT
		assertEquals(expectStudy, resultStudy);
	}
	
	@Test
	public void testGetWithContents() {
		int seq = 3;
		List<BoardFileVo> files = new ArrayList<>();
		List<BoardImageVo> images = new ArrayList<>();
		String contents = "&";
		String newContents = "&amp;";
		
		StudyVo study = new StudyVo();
		study.setSeq(seq);
		study.setContents(contents);
		
		StudyVo expectStudy = new StudyVo();
		expectStudy.setSeq(seq);
		expectStudy.setFiles(files);
		expectStudy.setImages(images);
		expectStudy.setContents(newContents);
		
		when(studyDao.get(seq)).thenReturn(study);
		
		//ACT
		StudyVo resultStudy = studyService.get(seq);
		
		//ASSERT
		assertEquals(expectStudy, resultStudy);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDoViewVisitedByAdmin() {
		int seq = 3;
		List<BoardFileVo> files = new ArrayList<>();
		List<BoardImageVo> images = new ArrayList<>();
		Set<Integer> visitStudies = new HashSet<>();
		visitStudies.add(seq);
		
		StudyVo study = new StudyVo();
		study.setSeq(seq);
		study.setHits(0);
		
		when(studyDao.get(seq)).thenReturn(study);
		
		StudyVo expectedStudy = new StudyVo();
		expectedStudy.setSeq(seq);
		expectedStudy.setFiles(files);
		expectedStudy.setImages(images);
		expectedStudy.setHits(0);
		
		//ACT
		StudyVo resultStudy = studyService.doView(visitStudies, seq);
		
		//ASSERT
		assertEquals(expectedStudy, resultStudy);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDoViewNoneVisitedByAdmin() {
		int seq = 3;
		List<BoardFileVo> files = new ArrayList<>();
		List<BoardImageVo> images = new ArrayList<>();
		Set<Integer> visitStudies = new HashSet<>();
		
		StudyVo study = new StudyVo();
		study.setSeq(seq);
		study.setHits(0);
		
		when(studyDao.get(seq)).thenReturn(study);
		
		StudyVo expectedStudy = new StudyVo();
		expectedStudy.setSeq(seq);
		expectedStudy.setFiles(files);
		expectedStudy.setImages(images);
		expectedStudy.setHits(0);
		
		//ACT
		StudyVo resultStudy = studyService.doView(visitStudies, seq);
		
		//
		assertFalse(visitStudies.contains(seq));
		assertEquals(expectedStudy, resultStudy);
	}
	
	@Test
	public void testDoViewVisitedByAnyone() {
		int seq = 3;
		List<BoardFileVo> files = new ArrayList<>();
		List<BoardImageVo> images = new ArrayList<>();
		Set<Integer> visitStudies = new HashSet<>();
		visitStudies.add(seq);
		
		StudyVo study = new StudyVo();
		study.setSeq(seq);
		study.setHits(0);
		
		when(studyDao.get(seq)).thenReturn(study);
		
		StudyVo expectedStudy = new StudyVo();
		expectedStudy.setSeq(seq);
		expectedStudy.setFiles(files);
		expectedStudy.setImages(images);
		expectedStudy.setHits(0);
		
		//ACT
		StudyVo resultStudy = studyService.doView(visitStudies, seq);
		
		//ASSERT
		assertEquals(expectedStudy, resultStudy);
	}
	
	@Test
	public void testDoViewNoneVisitedByAnyone() {
		int seq = 3;
		List<BoardFileVo> files = new ArrayList<>();
		List<BoardImageVo> images = new ArrayList<>();
		Set<Integer> visitStudies = new HashSet<>();
		
		StudyVo study = new StudyVo();
		study.setSeq(seq);
		study.setHits(0);
		
		when(studyDao.get(seq)).thenReturn(study);
		
		StudyVo expectedStudy = new StudyVo();
		expectedStudy.setSeq(seq);
		expectedStudy.setFiles(files);
		expectedStudy.setImages(images);
		expectedStudy.setHits(1);
		
		//ACT
		StudyVo resultStudy = studyService.doView(visitStudies, seq);
		
		//ASSERT
		assertTrue(visitStudies.contains(seq));
		assertEquals(expectedStudy, resultStudy);
		verify(studyDao).update(study);
	}
	
	
	@Test
	public void testGetBefore() {
		int seq = 3;
		String category = "SAMPLE_CATEGORY";
		StudyVo study = new StudyVo();
		study.setSeq(seq - 1);
		
		when(studyDao.getBefore(seq, category)).thenReturn(study);
		
		//ACT
		StudyVo resultStudy = studyService.getBefore(seq, category);
		
		//ASSERT
		assertEquals(study, resultStudy);
	}
	
	@Test
	public void testGetAfter() {
		int seq = 3;
		String category = "SAMPLE_CATEGORY";
		StudyVo study = new StudyVo();
		study.setSeq(seq + 1);
		
		when(studyDao.getAfter(seq, category)).thenReturn(study);
		
		//ACT
		StudyVo resultStudy = studyService.getAfter(seq, category);
		
		//ASSERT
		assertEquals(study, resultStudy);
	}
	
	@Test
	public void testGetCategories() {
		List<String> categories = new ArrayList<>();
		
		when(studyDao.getCategories()).thenReturn(categories);
		
		//ACT
		List<String> resultCategories = studyService.getCategories();
		
		//ASSERT
		assertEquals(categories, resultCategories);
	}
	
	
	@Test
	public void testList() {
		HashMap<String, Object> map = new HashMap<>();
		List<StudyVo> studies = new ArrayList<>();
		
		when(studyService.list(map)).thenReturn(studies);
		
		//ACT
		List<StudyVo> resultStudies = studyService.list(map);
		
		//ASSERT
		assertEquals(studies, resultStudies);
	}
	
	
	@Test
	public void testPaging() {
		String page = "1";
		String perPgLine = "10";
		String content = "<body>SAMPLE_CONTENTS\nNN<body>";
		String newContent = "SAMPLE_CONTENTS NN";
		
		Map<String, Object> params = new HashMap<>();
		params.put("page", page);
		params.put("perPgLine", perPgLine);
		
		List<StudyVo> studies = new ArrayList<>();
		StudyVo study = new StudyVo();
		study.setContents(content);
		studies.add(study);
		
		StudyVo expectedStudy = new StudyVo();
		expectedStudy.setContents(newContent);
		
		when(studyDao.list(params)).thenReturn(studies);
		
		//ACT
		List<StudyVo> resultStudies = studyService.paging(params);
		
		//ASSERT
		assertEquals(0, params.get("startRow"));
		assertEquals(expectedStudy, resultStudies.get(0));
	}
	
	@Test
	public void testInsert() throws Exception {
		int seq = 3;
		String contents = "SAMPLE_CONTENTS";
		String fileValues = "SAMPLE_FILEVALUES";
		String imageValues = "SAMPLE_IMAGEVALUES";
		String newContents = "SAMPLE_NEWCONTENTS";
		
		StudyVo study = new StudyVo();
		study.setContents(contents);
		study.setSeq(seq);
		
		StudyVo expectStudy = new StudyVo();
		expectStudy.setDate(Formatter.toDate(new Date()));
		expectStudy.setContents(newContents);
		expectStudy.setSeq(seq);
		expectStudy.setHits(0);
		
		when(studyDao.insert(study)).thenReturn(seq);
		when(boardImageService.insertImages(imageTB, imageDir, seq, contents, imageValues)).thenReturn(newContents);
		
		//ACT
		int result = studyService.insert(study, imageValues, fileValues);
		
		//ASSERT
		assertEquals(seq, result);
		assertEquals(expectStudy, study);
		verify(boardFileService).insertFiles(fileTB, fileDir, seq, fileValues);
		verify(studyDao).update(study);
	}
	
	@Test
	public void testUpdate() throws Exception {
		int seq = 3;
		String contents = "SAMPLE_CONTENTS";
		String fileValues = "SAMPLE_FILEVALUES";
		String imageValues = "SAMPLE_IMAGEVALUES";
		String newContents = "SAMPLE_NEWCONTENTS";
		boolean expected = true;
		
		StudyVo study = new StudyVo();
		study.setContents(contents);
		study.setSeq(seq);
		
		StudyVo expectStudy = new StudyVo();
		expectStudy.setContents(newContents);
		expectStudy.setSeq(seq);
		expectStudy.setHits(0);
		
		when(boardImageService.insertImages(imageTB, imageDir, seq, contents, imageValues)).thenReturn(newContents);
		when(studyDao.update(study)).thenReturn(true);
		
		//ACT
		boolean result = studyService.update(study, imageValues, fileValues);
		
		//ASSERT
		assertEquals(expected, result);
		assertEquals(expectStudy, study);
		verify(boardFileService).insertFiles(fileTB, fileDir, seq, fileValues);
		verify(studyDao).update(study);
	}
	
	@Test
	public void testDeleteResultTrue() {
		int seq = 3;
		boolean expected = true;
		List<BoardFileVo> files = new ArrayList<>();
		List<BoardImageVo> images = new ArrayList<>();
		files.add(mock(BoardFileVo.class));
		files.add(mock(BoardFileVo.class));
		images.add(mock(BoardImageVo.class));
		images.add(mock(BoardImageVo.class));
		
		when(studyDao.delete(seq)).thenReturn(expected);
		
		//ACT
		boolean result = studyService.delete(seq);
		
		//ASSERT
		assertEquals(expected, result);
		verify(fileHandler, times(2)).delete(eq(realPath + fileDir), anyString());
		verify(fileHandler, times(2)).delete(eq(realPath + imageDir), anyString());
		
	}
	
	@Test
	public void testDeleteResultFalse() {
		int seq = 3;
		boolean expected = false;

		when(studyDao.delete(seq)).thenReturn(expected);
		
		//ACT
		boolean result = studyService.delete(seq);
		
		//ASSERT
		assertEquals(expected, result);
	}
}
