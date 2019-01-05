package com.cglee079.changoos.dao;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.model.StudyVo;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/test_config/dao-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/property-context.xml"})
public class StudyDaoTest {
	
	@Autowired private StudyDao studyDao;
	@Autowired private BoardComtDao boardComtDao;
	@Autowired private BoardFileDao boardFileDao;
	@Autowired private BoardImageDao boardImageDao;
	
	@Value("#{db['study.file.tb.name']}") private String fileTB;
	@Value("#{db['study.image.tb.name']}") private String imageTB;
	@Value("#{db['study.comt.tb.name']}")	private String comtTB;
	
	private StudyVo sampleStudyA;
	private StudyVo sampleStudyB;
	private StudyVo sampleStudyC;

	@Before
	public void setUp() {
		sampleStudyA = StudyVo.builder()
				.title("studyA")
				.contents("studyA 내용")
				.enabled(true)
				.files(new ArrayList<BoardFileVo>())
				.images(new ArrayList<BoardImageVo>())
				.build();
		
		sampleStudyB = StudyVo.builder()
				.title("studyB")
				.contents("studyB 내용")
				.enabled(true)
				.files(new ArrayList<BoardFileVo>())
				.images(new ArrayList<BoardImageVo>())
				.build();
		
		sampleStudyC = StudyVo.builder()
				.title("studyC")
				.contents("studyC 내용")
				.enabled(true)
				.files(new ArrayList<BoardFileVo>())
				.images(new ArrayList<BoardImageVo>())
				.build();
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGet() {
		int seq = studyDao.insert(sampleStudyA);
		
		BoardFileVo sampleFileA = BoardFileVo.builder()
				.boardSeq(seq)
				.pathname("fileA_이름.PDF")
				.build();
		boardFileDao.insert(fileTB, sampleFileA);
		
		BoardImageVo sampleImageA = BoardImageVo.builder()
				.boardSeq(seq)
				.pathname("imageA_이름.JPG")
				.build();
		boardImageDao.insert(imageTB, sampleImageA);
		
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seq).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seq).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seq).build());
		
		//ACT
		StudyVo resultProject = studyDao.get(seq);
		
		//ASSERT
		sampleFileA.setBoardSeq(0);
		sampleImageA.setBoardSeq(0);
		sampleStudyA.getFiles().add(sampleFileA);
		sampleStudyA.getImages().add(sampleImageA);
		sampleStudyA.setComtCnt(3);
		assertEquals(sampleStudyA, resultProject);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetBeforeWithSameCategory() {
		sampleStudyA.setCategory("categoryA");
		sampleStudyB.setCategory("categoryB");
		sampleStudyC.setCategory("categoryB");
		int seqA = studyDao.insert(sampleStudyA);
		int seqB = studyDao.insert(sampleStudyB);
		int seqC = studyDao.insert(sampleStudyC);
		
		StudyVo resultProject = studyDao.getBefore(seqB, "categoryB");
		
		assertEquals(sampleStudyC, resultProject);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetBeforeWithoutSameCategory() {
		sampleStudyA.setCategory("categoryA");
		sampleStudyB.setCategory("categoryB");
		sampleStudyC.setCategory("categoryC");
		int seqA = studyDao.insert(sampleStudyA);
		int seqB = studyDao.insert(sampleStudyB);
		int seqC = studyDao.insert(sampleStudyC);
		
		StudyVo resultProject = studyDao.getBefore(seqB, "categoryB");
		
		assertEquals(null, resultProject);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAfterWithSameCategory() {
		sampleStudyA.setCategory("categoryB");
		sampleStudyB.setCategory("categoryB");
		sampleStudyC.setCategory("categoryC");
		int seqA = studyDao.insert(sampleStudyA);
		int seqB = studyDao.insert(sampleStudyB);
		int seqC = studyDao.insert(sampleStudyC);
		
		StudyVo resultProject = studyDao.getAfter(seqB, "categoryB");
		
		assertEquals(sampleStudyA, resultProject);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAfterWithoutSameCategory() {
		sampleStudyA.setCategory("categoryA");
		sampleStudyB.setCategory("categoryB");
		sampleStudyC.setCategory("categoryC");
		int seqA = studyDao.insert(sampleStudyA);
		int seqB = studyDao.insert(sampleStudyB);
		int seqC = studyDao.insert(sampleStudyC);
		
		StudyVo resultProject = studyDao.getAfter(seqB, "categoryB");
		
		assertEquals(null, resultProject);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate() {
		int seq = studyDao.insert(sampleStudyA);
		sampleStudyB.setSeq(seq);
		
		//ACT
		studyDao.update(sampleStudyB);

		//ASSERT
		StudyVo resultProject = studyDao.get(seq);
		
		assertEquals(sampleStudyB, resultProject);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDelete() {
		int seq = studyDao.insert(sampleStudyA);
		
		//ACT
		studyDao.delete(seq);

		//ASSERT
		StudyVo resultProject = studyDao.get(seq);
		assertEquals(null, resultProject);
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testList() {
		studyDao.insert(sampleStudyA);
		studyDao.insert(sampleStudyB);
		studyDao.insert(sampleStudyC);
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(null);
		
		//ASSERT
		assertEquals(3, resultStudies.size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithEnabledTrue() {
		sampleStudyA.setEnabled(true);
		sampleStudyB.setEnabled(false);
		sampleStudyC.setEnabled(false);
		studyDao.insert(sampleStudyA);
		studyDao.insert(sampleStudyB);
		studyDao.insert(sampleStudyC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("enabled", true);
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(params);
		
		//ASSERT
		assertEquals(1, resultStudies.size());
		assertEquals(sampleStudyA, resultStudies.get(0));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithCategory() {
		sampleStudyA.setCategory("categoryA");
		sampleStudyB.setCategory("categoryB");
		sampleStudyC.setCategory("categoryC");
		
		studyDao.insert(sampleStudyA);
		studyDao.insert(sampleStudyB);
		studyDao.insert(sampleStudyC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("category", "categoryA");
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(params);
		
		//ASSERT
		assertEquals(1, resultStudies.size());
		assertEquals(sampleStudyA, resultStudies.get(0));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSearchValueA() {
		sampleStudyA.setTitle("A");
		sampleStudyB.setTitle("B");
		sampleStudyC.setTitle("B");
		
		studyDao.insert(sampleStudyA);
		studyDao.insert(sampleStudyB);
		studyDao.insert(sampleStudyC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("searchValue", "a");
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(params);
		
		//ASSERT
		assertEquals(1, resultStudies.size());
		assertEquals(sampleStudyA, resultStudies.get(0));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSearchValueB() {
		sampleStudyA.setTitle("A");
		sampleStudyA.setTitle("B");
		sampleStudyA.setTitle("B");
		
		studyDao.insert(sampleStudyA);
		studyDao.insert(sampleStudyB);
		studyDao.insert(sampleStudyC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("searchValue", "B");
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(params);
		
		//ASSERT
		assertEquals(2, resultStudies.size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithPaging() {
		sampleStudyA.setDate("2019-01-03");
		sampleStudyB.setDate("2019-01-02");
		sampleStudyC.setDate("2019-01-01");
		
		studyDao.insert(sampleStudyA);
		studyDao.insert(sampleStudyB);
		studyDao.insert(sampleStudyC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("startRow", 1);
		params.put("perPgLine", 2);
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(params);
		
		//ASSERT
		assertEquals(2, resultStudies.size());
		assertEquals(sampleStudyB, resultStudies.get(0));
		assertEquals(sampleStudyC, resultStudies.get(1));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortSeq() {
		studyDao.insert(sampleStudyA);
		studyDao.insert(sampleStudyC);
		studyDao.insert(sampleStudyB);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "seq");
		params.put("order", "ASC");
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(params);
		
		//ASSERT
		assertEquals(sampleStudyA, resultStudies.get(0));
		assertEquals(sampleStudyC, resultStudies.get(1));
		assertEquals(sampleStudyB, resultStudies.get(2));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortTitle() {
		sampleStudyA.setTitle("1");
		sampleStudyB.setTitle("3");
		sampleStudyC.setTitle("2");
		
		studyDao.insert(sampleStudyA);
		studyDao.insert(sampleStudyB);
		studyDao.insert(sampleStudyC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "title");
		params.put("order", "ASC");
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(params);
		
		//ASSERT
		assertEquals(sampleStudyA, resultStudies.get(0));
		assertEquals(sampleStudyC, resultStudies.get(1));
		assertEquals(sampleStudyB, resultStudies.get(2));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortCategory() {
		sampleStudyA.setCategory("categoryA");
		sampleStudyB.setCategory("categoryC");
		sampleStudyC.setCategory("categoryB");
		
		studyDao.insert(sampleStudyA);
		studyDao.insert(sampleStudyB);
		studyDao.insert(sampleStudyC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "category");
		params.put("order", "asc");
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(params);
		
		//ASSERT
		assertEquals(sampleStudyA, resultStudies.get(0));
		assertEquals(sampleStudyC, resultStudies.get(1));
		assertEquals(sampleStudyB, resultStudies.get(2));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortCodeLang() {
		sampleStudyA.setCodeLang("langA");
		sampleStudyB.setCodeLang("langC");
		sampleStudyC.setCodeLang("langB");
		
		studyDao.insert(sampleStudyA);
		studyDao.insert(sampleStudyB);
		studyDao.insert(sampleStudyC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "codeLang");
		params.put("order", "asc");
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(params);
		
		//ASSERT
		assertEquals(sampleStudyA, resultStudies.get(0));
		assertEquals(sampleStudyC, resultStudies.get(1));
		assertEquals(sampleStudyB, resultStudies.get(2));
	}
	
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortDate() {
		sampleStudyA.setDate("2019-01-01");
		sampleStudyB.setDate("2019-01-03");
		sampleStudyC.setDate("2019-01-02");
		
		studyDao.insert(sampleStudyA);
		studyDao.insert(sampleStudyB);
		studyDao.insert(sampleStudyC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "date");
		params.put("order", "asc");
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(params);
		
		//ASSERT
		assertEquals(sampleStudyA, resultStudies.get(0));
		assertEquals(sampleStudyC, resultStudies.get(1));
		assertEquals(sampleStudyB, resultStudies.get(2));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortHits() {
		sampleStudyA.setHits(1);
		sampleStudyB.setHits(3);
		sampleStudyC.setHits(2);
		
		studyDao.insert(sampleStudyA);
		studyDao.insert(sampleStudyB);
		studyDao.insert(sampleStudyC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "hits");
		params.put("order", "ASC");
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(params);
		
		//ASSERT
		assertEquals(sampleStudyA, resultStudies.get(0));
		assertEquals(sampleStudyC, resultStudies.get(1));
		assertEquals(sampleStudyB, resultStudies.get(2));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortComtCnt() {
		int seqA = studyDao.insert(sampleStudyA);
		int seqB = studyDao.insert(sampleStudyB);
		int seqC = studyDao.insert(sampleStudyC);
		
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqA).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqB).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqB).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqB).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqC).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqC).build());
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "comtCnt");
		params.put("order", "ASC");
		
		//ACT
		List<StudyVo> resultStudies = studyDao.list(params);
		
		//ASSERT
		sampleStudyA.setComtCnt(1);
		sampleStudyB.setComtCnt(3);
		sampleStudyC.setComtCnt(2);
		assertEquals(sampleStudyA, resultStudies.get(0));
		assertEquals(sampleStudyC, resultStudies.get(1));
		assertEquals(sampleStudyB, resultStudies.get(2));
	}
}
