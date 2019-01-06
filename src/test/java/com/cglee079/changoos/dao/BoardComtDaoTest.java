package com.cglee079.changoos.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

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

import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.model.ProjectVo;

@Transactional
@Rollback(true)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/test_config/dao-context.xml",
	"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardComtDaoTest {

	@Autowired private BoardComtDao boardComtDao;
	@Autowired private ProjectDao projectDao;
	
	@Value("#{db['project.comt.tb.name']}")	private String TB;
	
	private BoardComtVo sampleComtA;
	private BoardComtVo sampleComtB;
	private BoardComtVo sampleComtC;
	
	@Before
	public void setUp() {
		sampleComtA = BoardComtVo.builder()
				.contents("comtA 내용")
				.username("comtA 유저명")
				.password("comtA 비밀번호")
				.build();
		
		sampleComtB = BoardComtVo.builder()
				.contents("comtB 내용")
				.username("comtB 유저명")
				.password("comtB 비밀번호")
				.build();
		
		sampleComtC = BoardComtVo.builder()
				.contents("comtC 내용")
				.username("comtC 유저명")
				.password("comtC 비밀번호")
				.build();
	}
	

	@Test
	public void testCount() { 
		int boardSeq = projectDao.insert(ProjectVo.builder().build());
		
		sampleComtA.setBoardSeq(boardSeq);
		sampleComtB.setBoardSeq(boardSeq);
		sampleComtC.setBoardSeq(boardSeq);
		
		boardComtDao.insert(TB, sampleComtA);
		boardComtDao.insert(TB, sampleComtB);
		boardComtDao.insert(TB, sampleComtC);
		
		//ACT
		int result = boardComtDao.count(TB, boardSeq);
		
		//ASSERT
		assertEquals(3, result);
	}
	
	@Test
	public void testGet() { 
		int boardSeq = projectDao.insert(ProjectVo.builder().build());
		
		sampleComtA.setBoardSeq(boardSeq);
		boardComtDao.insert(TB, sampleComtA);
		int seq  = sampleComtA.getSeq();
		
		//ACT
		BoardComtVo resultComt = boardComtDao.get(TB, seq);
		
		//ASSERT
		assertEquals(sampleComtA, resultComt);
	}
	
	@Test
	public void testDelete() { 
		int boardSeq = projectDao.insert(ProjectVo.builder().build());
		
		sampleComtA.setBoardSeq(boardSeq);
		boardComtDao.insert(TB, sampleComtA);
		int seq = sampleComtA.getSeq();
		
		//ACT
		boardComtDao.delete(TB, seq);
		
		//ASSERT
		BoardComtVo resultComt = boardComtDao.get(TB, seq);
		assertEquals(null, resultComt);
	}
	
	@Test
	public void testUpdate() { 
		int boardSeq = projectDao.insert(ProjectVo.builder().build());
		sampleComtA.setBoardSeq(boardSeq);
		boardComtDao.insert(TB, sampleComtA);
		int seq = sampleComtA.getSeq();
		
		sampleComtA.setContents("새로운 내용");
		
		//ACT
		boardComtDao.update(TB, sampleComtA);
		
		//ASSERT
		BoardComtVo resultComt = boardComtDao.get(TB, seq);
		assertEquals(sampleComtA, resultComt);
	}
	
	@Test
	public void testList() { 
		int boardSeq = projectDao.insert(ProjectVo.builder().build());
		int startRow = 0;
		int perPgLine= 10;
		
		sampleComtA.setBoardSeq(boardSeq);
		sampleComtB.setBoardSeq(boardSeq);
		sampleComtC.setBoardSeq(boardSeq);
		
		boardComtDao.insert(TB, sampleComtA);
		boardComtDao.insert(TB, sampleComtB);
		boardComtDao.insert(TB, sampleComtC);
		
		//ACT
		List<BoardComtVo> resultComts = boardComtDao.list(TB, boardSeq, startRow, perPgLine);
		
		//ASSERT
		assertEquals(3, resultComts.size());
	}
	
	@Test
	public void testListWithShortPgLine() { 
		int boardSeq = projectDao.insert(ProjectVo.builder().build());
		int startRow = 0;
		int perPgLine= 2;
		
		sampleComtA.setBoardSeq(boardSeq);
		sampleComtB.setBoardSeq(boardSeq);
		sampleComtC.setBoardSeq(boardSeq);
		
		boardComtDao.insert(TB, sampleComtA);
		boardComtDao.insert(TB, sampleComtB);
		boardComtDao.insert(TB, sampleComtC);
		
		//ACT
		List<BoardComtVo> resultComts = boardComtDao.list(TB, boardSeq, startRow, perPgLine);
		
		//ASSERT
		assertEquals(2, resultComts.size());
	}
	
	@Test
	public void testListWithSortParentComt() { 
		int boardSeq = projectDao.insert(ProjectVo.builder().build());
		int startRow = 0;
		int perPgLine= 10;
		
		sampleComtA.setBoardSeq(boardSeq);
		sampleComtB.setBoardSeq(boardSeq);
		sampleComtC.setBoardSeq(boardSeq);
		
		boardComtDao.insert(TB, sampleComtA);
		boardComtDao.insert(TB, sampleComtB);
		
		sampleComtC.setParentComt(sampleComtA.getSeq());
		boardComtDao.insert(TB, sampleComtC);
		
		//ACT
		List<BoardComtVo> resultComts = boardComtDao.list(TB, boardSeq, startRow, perPgLine);
		
		//ASSERT
		assertEquals(3, resultComts.size());
		assertEquals(sampleComtA, resultComts.get(0));
		assertEquals(sampleComtC, resultComts.get(1));
		assertEquals(sampleComtB, resultComts.get(2));
	}
}
