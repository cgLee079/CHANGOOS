package com.cglee079.changoos.service;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.cglee079.changoos.dao.BoardComtDao;
import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.util.Formatter;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml"})
public class BoardComtServiceTest {
	
	@Mock private BoardComtDao boardcomtDao;
	
	@Value("#{db['project.comt.tb.name']}")	private String projectComtTB;
	@Value("#{db['study.comt.tb.name']}") 	private String studyComtTB;
	@Value("#{db['blog.comt.tb.name']}") 	private String blogComtTB;

	@Value("#{constant['board.type.id.project']}") 	private String projectID;
	@Value("#{constant['board.type.id.study']}") 	private String studyID;
	@Value("#{constant['board.type.id.blog']}")		private String blogID;
	
	@Spy
	@InjectMocks
	private BoardComtService boardComtService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		ReflectionTestUtils.setField(boardComtService, "projectComtTB", projectComtTB);
		ReflectionTestUtils.setField(boardComtService, "studyComtTB", studyComtTB);
		ReflectionTestUtils.setField(boardComtService, "blogComtTB", blogComtTB);
		ReflectionTestUtils.setField(boardComtService, "projectID", projectID);
		ReflectionTestUtils.setField(boardComtService, "studyID", studyID);
		ReflectionTestUtils.setField(boardComtService, "blogID", blogID);
	}

	@Test
	public void testPaging() {
		int page = 2;
		int perPgLine = 10;
		int startRow = (page - 1) * perPgLine;
		int boardSeq = 3;
		String TB = blogComtTB;
		String boardType = blogID;
		
		List<BoardComtVo> comts = new ArrayList<>();
		
		doReturn(blogComtTB).when(boardComtService).getTB(boardType);
		when(boardcomtDao.list(TB, boardSeq, startRow, perPgLine)).thenReturn(comts);
		
		//ACT
		List<BoardComtVo> resultComts = boardComtService.paging(boardType, boardSeq, page, perPgLine);
		
		assertEquals(comts, resultComts);
	}

	@Test
	public void testCount() {
		int boardSeq = 3;
		int count = 10;
		String TB = blogComtTB;
		String boardType = blogID;
		
		doReturn(blogComtTB).when(boardComtService).getTB(boardType);
		when(boardcomtDao.count(TB, boardSeq)).thenReturn(count);
		
		//ACT
		int resultCount = boardComtService.count(boardType, boardSeq);
		
		assertEquals(count, resultCount);
	}
	
	@Test
	public void testInsert() {
		boolean expect = true;
		String date = Formatter.toDate(new Date());
		String TB = blogComtTB;
		String boardType = blogID;
		
		BoardComtVo comt = BoardComtVo.builder().build();
		
		BoardComtVo expectComt = BoardComtVo.builder()
				.date(date)
				.build();
		
		doReturn(blogComtTB).when(boardComtService).getTB(boardType);
		when(boardcomtDao.insert(TB, comt)).thenReturn(expect);
		
		//ACT
		boolean result = boardComtService.insert(boardType, comt);
		
		//ASERT
		assertEquals(expect, result);
		assertEquals(expectComt, comt);
	}
	
	@Test
	public void testUpdate() {
		boolean expect = true;
		String TB = blogComtTB;
		String boardType = blogID;
		
		BoardComtVo comt = BoardComtVo.builder().build();
		
		doReturn(blogComtTB).when(boardComtService).getTB(boardType);
		when(boardcomtDao.update(TB, comt)).thenReturn(true);
		
		//ACT
		boolean result= boardComtService.update(boardType, comt);
		
		assertEquals(expect, result);
	}
	
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDeleteWithCorrectPwdByAdmin() {
		int seq = 3;
		boolean expect = true;
		String password = "SAMPLE_PASSWORD";
		String TB = blogComtTB;
		String boardType = blogID;
		
		BoardComtVo comt = BoardComtVo.builder()
				.seq(seq)
				.password(password)
				.build();
		
		BoardComtVo savedComt = BoardComtVo.builder()
				.seq(seq)
				.password(password)
				.build();
		
		doReturn(blogComtTB).when(boardComtService).getTB(boardType);
		when(boardcomtDao.get(TB, seq)).thenReturn(savedComt);
		when(boardcomtDao.delete(TB, seq)).thenReturn(expect);
		
		//ACT
		boolean result = boardComtService.delete(boardType, comt);
		
		//ASSERT
		assertEquals(expect, result);
		verify(boardcomtDao).delete(TB, seq);
	}
	
	@Test
	public void testDeleteWithCorrectPwdByUser() {
		int seq = 3;
		boolean expect = true;
		String password = "SAMPLE_PASSWORD";
		String TB = blogComtTB;
		String boardType = blogID;
		
		BoardComtVo comt = BoardComtVo.builder()
				.seq(seq)
				.password(password)
				.build();
		
		BoardComtVo savedComt = BoardComtVo.builder()
				.seq(seq)
				.password(password)
				.build();
		
		doReturn(blogComtTB).when(boardComtService).getTB(boardType);
		when(boardcomtDao.get(TB, seq)).thenReturn(savedComt);
		when(boardcomtDao.delete(TB, seq)).thenReturn(expect);
		
		//ACT
		boolean result = boardComtService.delete(boardType, comt);
		
		//ASSERT
		assertEquals(expect, result);
		verify(boardcomtDao).delete(TB, seq);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDeleteWithIncorrectPwdByAdmin() {
		int seq = 3;
		boolean expect = true;
		String password1 = "SAMPLE_PASSWORD1";
		String password2 = "SAMPLE_PASSWORD2";
		String TB = blogComtTB;
		String boardType = blogID;
		
		BoardComtVo comt = BoardComtVo.builder()
				.seq(seq)
				.password(password1)
				.build();
		
		BoardComtVo savedComt = BoardComtVo.builder()
				.seq(seq)
				.password(password2)
				.build();
		
		doReturn(blogComtTB).when(boardComtService).getTB(boardType);
		when(boardcomtDao.get(TB, seq)).thenReturn(savedComt);
		when(boardcomtDao.delete(TB, seq)).thenReturn(expect);
		
		//ACT
		boolean result = boardComtService.delete(boardType, comt);
		
		//ASSERT
		assertEquals(expect, result);
		verify(boardcomtDao).delete(TB, seq);
	}
	
	@Test
	public void testDeleteWithIncorrectPwdByUser() {
		int seq = 3;
		boolean expect = false;
		String password1 = "SAMPLE_PASSWORD1";
		String password2 = "SAMPLE_PASSWORD2";
		String TB = blogComtTB;
		String boardType = blogID;
		
		BoardComtVo comt = BoardComtVo.builder()
				.seq(seq)
				.password(password1)
				.build();
		
		BoardComtVo savedComt = BoardComtVo.builder()
				.seq(seq)
				.password(password2)
				.build();
		
		doReturn(blogComtTB).when(boardComtService).getTB(boardType);
		when(boardcomtDao.get(TB, seq)).thenReturn(savedComt);
		when(boardcomtDao.delete(TB, seq)).thenReturn(expect);
		
		//ACT
		boolean result = boardComtService.delete(boardType, comt);
		
		//ASSERT
		assertEquals(expect, result);
		verify(boardcomtDao, atMost(0)).delete(TB, seq);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testCheckPwdWithCorrectPwdByAdmin() {
		int seq = 3;
		boolean expect = true;
		String password = "SAMPLE_PASSWORD";
		String TB = blogComtTB;
		String boardType = blogID;
		
		BoardComtVo comt = BoardComtVo.builder()
				.seq(seq)
				.password(password)
				.build();
		
		BoardComtVo savedComt = BoardComtVo.builder()
				.seq(seq)
				.password(password)
				.build();
		
		doReturn(blogComtTB).when(boardComtService).getTB(boardType);
		when(boardcomtDao.get(TB, seq)).thenReturn(savedComt);
		
		//ACT
		boolean result = boardComtService.checkPwd(boardType, comt);
		
		//ASSERT
		assertEquals(expect, result);
	}
	
	@Test
	public void testCheckPwdWithCorrectPwdByUser() {
		int seq = 3;
		boolean expect = true;
		String password = "SAMPLE_PASSWORD1";
		String TB = blogComtTB;
		String boardType = blogID;
		
		BoardComtVo comt = BoardComtVo.builder()
				.seq(seq)
				.password(password)
				.build();
		
		BoardComtVo savedComt = BoardComtVo.builder()
				.seq(seq)
				.password(password)
				.build();
		
		doReturn(blogComtTB).when(boardComtService).getTB(boardType);
		when(boardcomtDao.get(TB, seq)).thenReturn(savedComt);
		
		//ACT
		boolean result = boardComtService.checkPwd(boardType, comt);
		
		//ASSERT
		assertEquals(expect, result);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testCheckPwdWithInCorrectPwdByAdmin() {
		int seq = 3;
		boolean expect = true;
		String password1 = "SAMPLE_PASSWORD1";
		String password2 = "SAMPLE_PASSWORD2";
		String TB = blogComtTB;
		String boardType = blogID;
		
		BoardComtVo comt = BoardComtVo.builder()
				.seq(seq)
				.password(password1)
				.build();
		
		BoardComtVo savedComt = BoardComtVo.builder()
				.seq(seq)
				.password(password2)
				.build();
		
		doReturn(blogComtTB).when(boardComtService).getTB(boardType);
		when(boardcomtDao.get(TB, seq)).thenReturn(savedComt);
		
		//ACT
		boolean result = boardComtService.checkPwd(boardType, comt);
		
		//ASSERT
		assertEquals(expect, result);
	}
	
	@Test
	public void testCheckPwdWithInCorrectPwdByUser() {
		int seq = 3;
		boolean expect = false;
		String password1 = "SAMPLE_PASSWORD1";
		String password2 = "SAMPLE_PASSWORD2";
		String TB = blogComtTB;
		String boardType = blogID;
		
		BoardComtVo comt = BoardComtVo.builder()
				.seq(seq)
				.password(password1)
				.build();
		
		BoardComtVo savedComt = BoardComtVo.builder()
				.seq(seq)
				.password(password2)
				.build();
		
		doReturn(blogComtTB).when(boardComtService).getTB(boardType);
		when(boardcomtDao.get(TB, seq)).thenReturn(savedComt);
		
		//ACT
		boolean result = boardComtService.checkPwd(boardType, comt);
		
		//ASSERT
		assertEquals(expect, result);
	}
	
	@Test 
	public void testGetTBWithBlogID() {
		String boardType = blogID;
		String expectTB = blogComtTB;
		
		String resultTB = boardComtService.getTB(boardType);
		
		assertEquals(expectTB, resultTB);
	}
	
	@Test 
	public void testGetTBWithProjectID() {
		String boardType = projectID;
		String expectTB = projectComtTB;
		
		String resultTB = boardComtService.getTB(boardType);
		
		assertEquals(expectTB, resultTB);
	}
	
	@Test 
	public void testGetTBWithStudyID() {
		String boardType = studyID;
		String expectTB = studyComtTB;
		
		String resultTB = boardComtService.getTB(boardType);
		
		assertEquals(expectTB, resultTB);
	}
}
