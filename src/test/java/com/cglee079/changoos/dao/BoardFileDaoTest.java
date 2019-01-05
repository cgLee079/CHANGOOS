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
import com.cglee079.changoos.model.ProjectVo;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/test_config/dao-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/property-context.xml"})
public class BoardFileDaoTest {

	@Autowired private BoardFileDao boardFileDao;
	@Autowired private ProjectDao projectDao;
	
	@Value("#{db['project.file.tb.name']}")	private String TB;
	
	private BoardFileVo sampleFileA;
	
	@Before
	public void setUp() {
		sampleFileA = BoardFileVo.builder()
				.filename("filA 이름")
				.pathname("fileA 저장이름")
				.build();
	}
	

	@Test
	@Transactional
	@Rollback(true)
	public void testGet() { 
		int boardSeq = projectDao.insert(ProjectVo.builder().build());
		
		sampleFileA.setBoardSeq(boardSeq);
		boardFileDao.insert(TB, sampleFileA);
		
		int seq = sampleFileA.getSeq();
		
		//ACT
		BoardFileVo resultFile = boardFileDao.get(TB, seq);
		
		//ASSERT
		assertEquals(sampleFileA, resultFile);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDelete() { 
		int boardSeq = projectDao.insert(ProjectVo.builder().build());
		
		sampleFileA.setBoardSeq(boardSeq);
		boardFileDao.insert(TB, sampleFileA);
		
		int seq = sampleFileA.getSeq();
		
		//ACT
		boardFileDao.delete(TB, seq);
		
		//ASSERT
		BoardFileVo resultFile = boardFileDao.get(TB, seq);
		assertEquals(null, resultFile);
	}
	
}
