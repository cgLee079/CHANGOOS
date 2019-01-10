package com.cglee079.changoos.dao;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import com.cglee079.changoos.model.PhotoVo;
import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;

@Transactional
@Rollback(true)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/test_config/dao-context.xml",
	"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class PhotoDaoTest {
	
	@Autowired private PhotoDao photoDao;
	
	private PhotoVo samplePhotoA;
	private PhotoVo samplePhotoB;
	private PhotoVo samplePhotoC;

	@Before
	public void setUp() {
		samplePhotoA = PhotoVo.builder()
				.name("photoA 이름")
				.thumbnail("photoA 썸네일")
				.filename("photoA 파일이름")
				.pathname("photoA 저장이름")
				.desc("photoA 설명")
				.date("2018-01-01")
				.time("00:00:01")
				.location("photoA 촬영장소")
				.tag("photoA 태그")
				.device("photoA 촬영기기")
				.enabled(true)
				.likeCnt(1)
				
				.build();
		
		samplePhotoB = PhotoVo.builder()
				.name("photoB 이름")
				.thumbnail("photoB 썸네일")
				.filename("photoB 파일이름")
				.pathname("photoB 저장이름")
				.desc("photoB 설명")
				.date("2018-01-02")
				.time("00:00:02")
				.location("photoB 촬영장소")
				.tag("photoB 태그")
				.device("photoB 촬영기기")
				.likeCnt(2)
				.enabled(true)
				.build();
		
		samplePhotoC = PhotoVo.builder()
				.name("photoC 이름")
				.thumbnail("photoC 썸네일")
				.filename("photoC 파일이름")
				.pathname("photoC 저장이름")
				.desc("photoC 설명")
				.date("2018-01-03")
				.time("00:00:03")
				.location("photoC 촬영장소")
				.tag("photoC 태그")
				.device("photoC 촬영기기")
				.likeCnt(3)
				.enabled(true)
				.build();
	}
	
	@Test
	public void testGet() {
		photoDao.insert(samplePhotoA);
		int seq = samplePhotoA.getSeq();
		
		//ACT
		PhotoVo resultPhoto = photoDao.get(seq);
		
		//ASSERT
		assertEquals(samplePhotoA, resultPhoto);
	}
	
	@Test
	public void testSeqs() {
		samplePhotoA.setEnabled(true);
		samplePhotoB.setEnabled(false);
		samplePhotoC.setEnabled(false);
		
		photoDao.insert(samplePhotoA);
		photoDao.insert(samplePhotoB);
		photoDao.insert(samplePhotoC);
		
		int seqA = samplePhotoA.getSeq();
		int seqB = samplePhotoB.getSeq();
		int seqC = samplePhotoC.getSeq();
		
		//ACT
		List<Integer> resultSeqs = photoDao.seqs();
		
		//ASSERT
		assertEquals(1, resultSeqs.size());
		assertTrue(resultSeqs.contains(seqA));
	}
	
	@Test
	public void testUpdate() {
		photoDao.insert(samplePhotoA);
		int seq = samplePhotoA.getSeq();
		samplePhotoB.setSeq(seq);
		
		//ACT
		photoDao.update(samplePhotoB);

		//ASSERT
		PhotoVo resultPhoto = photoDao.get(seq);
		assertEquals(samplePhotoB, resultPhoto);
	}
	
	@Test
	public void testDelete() {
		photoDao.insert(samplePhotoA);
		int seq = samplePhotoA.getSeq();
		
		//ACT
		photoDao.delete(seq);

		//ASSERT
		PhotoVo resultPhoto = photoDao.get(seq);
		assertEquals(null, resultPhoto);
	}
	
	
	@Test
	public void testList() {
		photoDao.insert(samplePhotoA);
		photoDao.insert(samplePhotoB);
		photoDao.insert(samplePhotoC);
		
		//ACT
		List<PhotoVo> resultPhotos = photoDao.list(null);
		
		//ASSERT
		assertEquals(3, resultPhotos.size());
	}
	
	@Test
	public void testListWithEnabledTrue() {
		Map<String, Object> params = new HashMap<>();
		params.put("enabled", true);
		
		samplePhotoA.setEnabled(true);
		samplePhotoB.setEnabled(false);
		samplePhotoC.setEnabled(false);
		
		photoDao.insert(samplePhotoA);
		photoDao.insert(samplePhotoB);
		photoDao.insert(samplePhotoC);
		
		//ACT
		List<PhotoVo> resultPhotos = photoDao.list(null);
		
		//ASSERT
		assertEquals(1, resultPhotos.size());
	}
	
	@Test
	public void testListWithEnabledFalse() {
		Map<String, Object> params = new HashMap<>();
		params.put("enabled", false);
		
		samplePhotoA.setEnabled(true);
		samplePhotoB.setEnabled(false);
		samplePhotoC.setEnabled(false);
		
		photoDao.insert(samplePhotoA);
		photoDao.insert(samplePhotoB);
		photoDao.insert(samplePhotoC);
		
		//ACT
		List<PhotoVo> resultPhotos = photoDao.list(params);
		
		//ASSERT
		assertEquals(2, resultPhotos.size());
	}
	
	
	@Test
	public void testListWithSortSeq() {
		photoDao.insert(samplePhotoA);
		photoDao.insert(samplePhotoC);
		photoDao.insert(samplePhotoB);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "seq");
		params.put("order", "ASC");
		
		//ACT
		List<PhotoVo> resultPhotos = photoDao.list(params);
		
		//ASSERT
		assertEquals(samplePhotoA, resultPhotos.get(0));
		assertEquals(samplePhotoC, resultPhotos.get(1));
		assertEquals(samplePhotoB, resultPhotos.get(2));
	}
	
	@Test
	public void testListWithSortName() {
		samplePhotoA.setName("1");
		samplePhotoB.setName("3");
		samplePhotoC.setName("2");
		
		photoDao.insert(samplePhotoA);
		photoDao.insert(samplePhotoB);
		photoDao.insert(samplePhotoC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "name");
		params.put("order", "ASC");
		
		//ACT
		List<PhotoVo> resultPhotos = photoDao.list(params);
		
		//ASSERT
		assertEquals(samplePhotoA, resultPhotos.get(0));
		assertEquals(samplePhotoC, resultPhotos.get(1));
		assertEquals(samplePhotoB, resultPhotos.get(2));
	}
	
	
	@Test
	public void testListWithSortLikeCnt() {
		samplePhotoA.setLikeCnt(1);
		samplePhotoB.setLikeCnt(3);
		samplePhotoC.setLikeCnt(2);
		
		photoDao.insert(samplePhotoA);
		photoDao.insert(samplePhotoB);
		photoDao.insert(samplePhotoC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "likeCnt");
		params.put("order", "ASC");
		
		//ACT
		List<PhotoVo> resultPhotos = photoDao.list(params);
		
		//ASSERT
		assertEquals(samplePhotoA, resultPhotos.get(0));
		assertEquals(samplePhotoC, resultPhotos.get(1));
		assertEquals(samplePhotoB, resultPhotos.get(2));
	}
	
	@Test
	public void testListWithSortDate() {
		samplePhotoA.setDate("2019-01-01");
		samplePhotoB.setDate("2019-01-03");
		samplePhotoC.setDate("2019-01-02");
		
		photoDao.insert(samplePhotoA);
		photoDao.insert(samplePhotoB);
		photoDao.insert(samplePhotoC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "date");
		params.put("order", "ASC");
		
		//ACT
		List<PhotoVo> resultPhotos = photoDao.list(params);
		
		//ASSERT
		assertEquals(samplePhotoA, resultPhotos.get(0));
		assertEquals(samplePhotoC, resultPhotos.get(1));
		assertEquals(samplePhotoB, resultPhotos.get(2));
	}
	
	@Test
	public void testListWithSortTime() {
		samplePhotoA.setTime("00:00:01");
		samplePhotoB.setTime("00:00:03");
		samplePhotoC.setTime("00:00:02");
		
		photoDao.insert(samplePhotoA);
		photoDao.insert(samplePhotoB);
		photoDao.insert(samplePhotoC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "time");
		params.put("order", "ASC");
		
		//ACT
		List<PhotoVo> resultPhotos = photoDao.list(params);
		
		//ASSERT
		assertEquals(samplePhotoA, resultPhotos.get(0));
		assertEquals(samplePhotoC, resultPhotos.get(1));
		assertEquals(samplePhotoB, resultPhotos.get(2));
	}
	
	@Test
	public void testListWithSortDevice() {
		samplePhotoA.setDevice("deviceA");
		samplePhotoB.setDevice("deviceC");
		samplePhotoC.setDevice("deviceB");
		
		photoDao.insert(samplePhotoA);
		photoDao.insert(samplePhotoB);
		photoDao.insert(samplePhotoC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "device");
		params.put("order", "ASC");
		
		//ACT
		List<PhotoVo> resultPhotos = photoDao.list(params);
		
		//ASSERT
		assertEquals(samplePhotoA, resultPhotos.get(0));
		assertEquals(samplePhotoC, resultPhotos.get(1));
		assertEquals(samplePhotoB, resultPhotos.get(2));
	}
	
	@Test
	public void testListWithSortLocation() {
		samplePhotoA.setLocation("locationA");
		samplePhotoB.setLocation("locationC");
		samplePhotoC.setLocation("locationB");
		
		photoDao.insert(samplePhotoA);
		photoDao.insert(samplePhotoB);
		photoDao.insert(samplePhotoC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "location");
		params.put("order", "ASC");
		
		//ACT
		List<PhotoVo> resultPhotos = photoDao.list(params);
		
		//ASSERT
		assertEquals(samplePhotoA, resultPhotos.get(0));
		assertEquals(samplePhotoC, resultPhotos.get(1));
		assertEquals(samplePhotoB, resultPhotos.get(2));
	}
	
	@Test
	public void testListWithSortTag() {
		samplePhotoA.setTag("tagA");
		samplePhotoB.setTag("tagC");
		samplePhotoC.setTag("tagB");
		
		photoDao.insert(samplePhotoA);
		photoDao.insert(samplePhotoB);
		photoDao.insert(samplePhotoC);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("sort", "tag");
		params.put("order", "ASC");
		
		//ACT
		List<PhotoVo> resultPhotos = photoDao.list(params);
		
		//ASSERT
		assertEquals(samplePhotoA, resultPhotos.get(0));
		assertEquals(samplePhotoC, resultPhotos.get(1));
		assertEquals(samplePhotoB, resultPhotos.get(2));
	}
}
