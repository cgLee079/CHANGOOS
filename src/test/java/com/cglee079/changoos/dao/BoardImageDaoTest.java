package com.cglee079.changoos.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.model.ProjectVo;

@Transactional
@Rollback(true)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/test_config/dao-context.xml",
	"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardImageDaoTest {

	@Autowired private BoardImageDao boardImageDao;
	@Autowired private ProjectDao projectDao;
	
	@Value("#{db['project.image.tb.name']}")	private String TB;
	
	private BoardImageVo sampleImageA;
	
	@Before
	public void setUp() {
		sampleImageA = BoardImageVo.builder()
				.filename("fileA 이름")
				.pathname("fileA 저장이름")
				.build();
	}
	

	@Test
	public void testGet() { 
		int boardSeq = projectDao.insert(ProjectVo.builder().build());
		
		sampleImageA.setBoardSeq(boardSeq);
		boardImageDao.insert(TB, sampleImageA);
		
		int seq = sampleImageA.getSeq();
		
		//ACT
		BoardImageVo resultImage = boardImageDao.get(TB, seq);
		
		//ASSERT
		assertEquals(sampleImageA, resultImage);
	}
	
	@Test
	public void testDelete() { 
		int boardSeq = projectDao.insert(ProjectVo.builder().build());
		
		sampleImageA.setBoardSeq(boardSeq);
		boardImageDao.insert(TB, sampleImageA);
		
		int seq = sampleImageA.getSeq();
		
		//ACT
		boardImageDao.delete(TB, seq);
		
		//ASSERT
		BoardImageVo resultImage = boardImageDao.get(TB, seq);
		assertEquals(null, resultImage);
	}
	
}
