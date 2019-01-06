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
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.cglee079.changoos.dao.PhotoComtDao;
import com.cglee079.changoos.model.PhotoComtVo;
import com.cglee079.changoos.util.Formatter;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml"})
public class PhotoComtServiceTest {
	
	@Mock private PhotoComtDao photoComtDao;
	
	@Spy
	@InjectMocks
	private PhotoComtService photoComtService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testList() {
		int photoSeq = 3;
		
		List<PhotoComtVo> comts = new ArrayList<>();
		
		when(photoComtDao.list(photoSeq)).thenReturn(comts);
		
		//ACT
		List<PhotoComtVo> resultComts = photoComtService.list(photoSeq);
		
		assertEquals(comts, resultComts);
	}

	@Test
	public void testCount() {
		int photoSeq = 3;
		int count = 10;
		
		when(photoComtDao.count(photoSeq)).thenReturn(count);
		
		//ACT
		int resultCount = photoComtService.count(photoSeq);
		
		assertEquals(count, resultCount);
	}
	
	@Test
	public void testInsert() {
		boolean expect = true;
		String date = Formatter.toDate(new Date());
		
		PhotoComtVo comt = PhotoComtVo.builder().build();
		
		PhotoComtVo expectComt = PhotoComtVo.builder()
				.date(date)
				.build();
		
		when(photoComtDao.insert(comt)).thenReturn(expect);
		
		//ACT
		boolean result = photoComtService.insert(comt);
		
		//ASERT
		assertEquals(expect, result);
		assertEquals(expectComt, comt);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDeleteWithCorrectPwdByAdmin() {
		int seq = 3;
		boolean expect = true;
		String password = "SAMPLE_PASSWORD";
		
		PhotoComtVo comt = PhotoComtVo.builder()
				.seq(seq)
				.password(password)
				.build();
		
		PhotoComtVo savedComt = PhotoComtVo.builder()
				.seq(seq)
				.password(password)
				.build();
		
		when(photoComtDao.get(seq)).thenReturn(savedComt);
		when(photoComtDao.delete(seq)).thenReturn(expect);
		
		//ACT
		boolean result = photoComtService.delete(comt);
		
		//ASSERT
		assertEquals(expect, result);
		verify(photoComtDao).delete(seq);
	}
	
	@Test
	public void testDeleteWithCorrectPwdByUser() {
		int seq = 3;
		boolean expect = true;
		String password = "SAMPLE_PASSWORD";
		
		PhotoComtVo comt = PhotoComtVo.builder()
				.seq(seq)
				.password(password)
				.build();
		
		PhotoComtVo savedComt = PhotoComtVo.builder()
				.seq(seq)
				.password(password)
				.build();
		
		when(photoComtDao.get(seq)).thenReturn(savedComt);
		when(photoComtDao.delete(seq)).thenReturn(expect);
		
		//ACT
		boolean result = photoComtService.delete(comt);
		
		//ASSERT
		assertEquals(expect, result);
		verify(photoComtDao).delete(seq);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDeleteWithIncorrectPwdByAdmin() {
		int seq = 3;
		boolean expect = true;
		String password1 = "SAMPLE_PASSWORD1";
		String password2 = "SAMPLE_PASSWORD2";
		
		PhotoComtVo comt = PhotoComtVo.builder()
				.seq(seq)
				.password(password1)
				.build();
		
		PhotoComtVo savedComt = PhotoComtVo.builder()
				.seq(seq)
				.password(password2)
				.build();
		
		when(photoComtDao.get(seq)).thenReturn(savedComt);
		when(photoComtDao.delete(seq)).thenReturn(expect);
		
		//ACT
		boolean result = photoComtService.delete(comt);
		
		//ASSERT
		assertEquals(expect, result);
		verify(photoComtDao).delete(seq);
	}
	
	@Test
	public void testDeleteWithIncorrectPwdByUser() {
		int seq = 3;
		boolean expect = false;
		String password1 = "SAMPLE_PASSWORD1";
		String password2 = "SAMPLE_PASSWORD2";
		
		PhotoComtVo comt = PhotoComtVo.builder()
				.seq(seq)
				.password(password1)
				.build();
		
		PhotoComtVo savedComt = PhotoComtVo.builder()
				.seq(seq)
				.password(password2)
				.build();
		
		when(photoComtDao.get(seq)).thenReturn(savedComt);
		when(photoComtDao.delete(seq)).thenReturn(expect);
		
		//ACT
		boolean result = photoComtService.delete(comt);
		
		//ASSERT
		assertEquals(expect, result);
		verify(photoComtDao, atMost(0)).delete(seq);
	}
}
	
