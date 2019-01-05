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

import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.model.ProjectVo;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/test_config/dao-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/property-context.xml"})
public class ProjectDaoTest {
	
	@Autowired private ProjectDao projectDao;
	@Autowired private BoardComtDao boardComtDao;
	@Autowired private BoardFileDao boardFileDao;
	@Autowired private BoardImageDao boardImageDao;
	
	@Value("#{db['project.file.tb.name']}") private String fileTB;
	@Value("#{db['project.image.tb.name']}") private String imageTB;
	@Value("#{db['project.comt.tb.name']}")	private String comtTB;
	
	private ProjectVo sampleProjectA;
	private ProjectVo sampleProjectB;
	private ProjectVo sampleProjectC;

	@Before
	public void setUp() {
		sampleProjectA = ProjectVo.builder()
				.title("projectA")
				.subtitle("projectA 입니다")
				.thumbnail("projectA_썸네일.jpg")
				.contents("projectA_내용")
				.desc("projectA_개요")
				.developer("projectA개발자")
				.sourcecode("projectA 소스코드")
				.enabled(true)
				.files(new ArrayList<BoardFileVo>())
				.images(new ArrayList<BoardImageVo>())
				.build();
		
		sampleProjectB = ProjectVo.builder()
				.title("projectB")
				.subtitle("projectB 입니다")
				.thumbnail("projectB_썸네일.jpg")
				.contents("projectB_내용")
				.desc("projectB_개요")
				.developer("projectB 개발자")
				.sourcecode("projectB 소스코드")
				.enabled(true)
				.files(new ArrayList<BoardFileVo>())
				.images(new ArrayList<BoardImageVo>())
				.build();
		
		sampleProjectC = ProjectVo.builder()
				.title("projectC")
				.subtitle("projectC 입니다")
				.thumbnail("projectC_썸네일.jpg")
				.contents("projectC_내용")
				.desc("projectC_개요")
				.developer("projectC 개발자")
				.sourcecode("projectC 소스코드")
				.enabled(true)
				.files(new ArrayList<BoardFileVo>())
				.images(new ArrayList<BoardImageVo>())
				.build();
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGet() {
		int seq = projectDao.insert(sampleProjectA);
		
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
		ProjectVo resultProject = projectDao.get(seq);
		
		//ASSERT
		sampleFileA.setBoardSeq(0);
		sampleImageA.setBoardSeq(0);
		sampleProjectA.getFiles().add(sampleFileA);
		sampleProjectA.getImages().add(sampleImageA);
		sampleProjectA.setComtCnt(3);
		assertEquals(sampleProjectA, resultProject);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetBefore() {
		int seqA = projectDao.insert(sampleProjectA);
		int seqB = projectDao.insert(sampleProjectB);
		int seqC = projectDao.insert(sampleProjectC);
		
		ProjectVo resultProject = projectDao.getBefore(seqB);
		
		assertEquals(sampleProjectC, resultProject);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testGetAfter() {
		int seqA = projectDao.insert(sampleProjectA);
		int seqB = projectDao.insert(sampleProjectB);
		int seqC = projectDao.insert(sampleProjectC);
		
		ProjectVo resultProject = projectDao.getAfter(seqB);
		
		assertEquals(sampleProjectA, resultProject);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testUpdate() {
		int seq = projectDao.insert(sampleProjectA);
		sampleProjectB.setSeq(seq);
		
		//ACT
		projectDao.update(sampleProjectB);

		//ASSERT
		ProjectVo resultProject = projectDao.get(seq);
		assertEquals(sampleProjectB, resultProject);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testDelete() {
		int seq = projectDao.insert(sampleProjectA);
		
		//ACT
		projectDao.delete(seq);

		//ASSERT
		ProjectVo resultProject = projectDao.get(seq);
		assertEquals(null, resultProject);
	}
	
	
	@Test
	@Transactional
	@Rollback(true)
	public void testList() {
		projectDao.insert(sampleProjectA);
		projectDao.insert(sampleProjectB);
		projectDao.insert(sampleProjectC);
		
		//ACT
		List<ProjectVo> resultProjects = projectDao.list(null);
		
		//ASSERT
		assertEquals(3, resultProjects.size());
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithEnabledTrue() {
		sampleProjectA.setEnabled(true);
		sampleProjectB.setEnabled(false);
		sampleProjectC.setEnabled(false);
		projectDao.insert(sampleProjectA);
		projectDao.insert(sampleProjectB);
		projectDao.insert(sampleProjectC);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("enabled", true);
		
		//ACT
		List<ProjectVo> resultProjects = projectDao.list(param);
		
		//ASSERT
		assertEquals(1, resultProjects.size());
		assertEquals(sampleProjectA, resultProjects.get(0));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortSeq() {
		projectDao.insert(sampleProjectA);
		projectDao.insert(sampleProjectC);
		projectDao.insert(sampleProjectB);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sort", "seq");
		param.put("order", "ASC");
		
		//ACT
		List<ProjectVo> resultProjects = projectDao.list(param);
		
		//ASSERT
		assertEquals(sampleProjectA, resultProjects.get(0));
		assertEquals(sampleProjectC, resultProjects.get(1));
		assertEquals(sampleProjectB, resultProjects.get(2));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortTitle() {
		sampleProjectA.setTitle("1");
		sampleProjectB.setTitle("3");
		sampleProjectC.setTitle("2");
		
		projectDao.insert(sampleProjectA);
		projectDao.insert(sampleProjectB);
		projectDao.insert(sampleProjectC);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sort", "title");
		param.put("order", "ASC");
		
		//ACT
		List<ProjectVo> resultProjects = projectDao.list(param);
		
		//ASSERT
		assertEquals(sampleProjectA, resultProjects.get(0));
		assertEquals(sampleProjectC, resultProjects.get(1));
		assertEquals(sampleProjectB, resultProjects.get(2));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortSubtitle() {
		sampleProjectA.setSubtitle("1");
		sampleProjectB.setSubtitle("3");
		sampleProjectC.setSubtitle("2");
		
		projectDao.insert(sampleProjectA);
		projectDao.insert(sampleProjectB);
		projectDao.insert(sampleProjectC);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sort", "subtitle");
		param.put("order", "ASC");
		
		//ACT
		List<ProjectVo> resultProjects = projectDao.list(param);
		
		//ASSERT
		assertEquals(sampleProjectA, resultProjects.get(0));
		assertEquals(sampleProjectC, resultProjects.get(1));
		assertEquals(sampleProjectB, resultProjects.get(2));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortDeveloper() {
		sampleProjectA.setDeveloper("1");
		sampleProjectB.setDeveloper("3");
		sampleProjectC.setDeveloper("2");
		
		projectDao.insert(sampleProjectA);
		projectDao.insert(sampleProjectB);
		projectDao.insert(sampleProjectC);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sort", "developer");
		param.put("order", "ASC");
		
		//ACT
		List<ProjectVo> resultProjects = projectDao.list(param);
		
		//ASSERT
		assertEquals(sampleProjectA, resultProjects.get(0));
		assertEquals(sampleProjectC, resultProjects.get(1));
		assertEquals(sampleProjectB, resultProjects.get(2));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortSourcecode() {
		sampleProjectA.setSourcecode("1");
		sampleProjectB.setSourcecode("3");
		sampleProjectC.setSourcecode("2");
		
		projectDao.insert(sampleProjectA);
		projectDao.insert(sampleProjectB);
		projectDao.insert(sampleProjectC);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sort", "sourcecode");
		param.put("order", "ASC");
		
		//ACT
		List<ProjectVo> resultProjects = projectDao.list(param);
		
		//ASSERT
		assertEquals(sampleProjectA, resultProjects.get(0));
		assertEquals(sampleProjectC, resultProjects.get(1));
		assertEquals(sampleProjectB, resultProjects.get(2));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortHits() {
		sampleProjectA.setHits(1);
		sampleProjectB.setHits(3);
		sampleProjectC.setHits(2);
		
		projectDao.insert(sampleProjectA);
		projectDao.insert(sampleProjectB);
		projectDao.insert(sampleProjectC);
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sort", "hits");
		param.put("order", "ASC");
		
		//ACT
		List<ProjectVo> resultProjects = projectDao.list(param);
		
		//ASSERT
		assertEquals(sampleProjectA, resultProjects.get(0));
		assertEquals(sampleProjectC, resultProjects.get(1));
		assertEquals(sampleProjectB, resultProjects.get(2));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testListWithSortComtCnt() {
		int seqA = projectDao.insert(sampleProjectA);
		int seqB = projectDao.insert(sampleProjectB);
		int seqC = projectDao.insert(sampleProjectC);
		
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqA).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqB).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqB).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqB).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqC).build());
		boardComtDao.insert(comtTB, BoardComtVo.builder().boardSeq(seqC).build());
		

		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("sort", "comtCnt");
		param.put("order", "ASC");
		
		//ACT
		List<ProjectVo> resultProjects = projectDao.list(param);
		
		//ASSERT
		sampleProjectA.setComtCnt(1);
		sampleProjectB.setComtCnt(3);
		sampleProjectC.setComtCnt(2);
		assertEquals(sampleProjectA, resultProjects.get(0));
		assertEquals(sampleProjectC, resultProjects.get(1));
		assertEquals(sampleProjectB, resultProjects.get(2));
	}
}
