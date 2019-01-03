package com.cglee079.changoos.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.PhotoDao;
import com.cglee079.changoos.dao.StudyDao;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.model.PhotoVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.util.FileHandler;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.ImageHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class PhotoServiceTest {
	
	private String realPath;
	
	@Mock private PhotoDao photoDao;
	@Mock private ImageHandler imageHandler;
	@Mock private FileHandler fileHandler;
	
	@Value("#{location['temp.photo.dir.url']}") 	private String tempDir;
	@Value("#{location['photo.origin.dir.url']}") 	private String originDir;
	@Value("#{location['photo.thumb.dir.url']}") 	private String thumbDir;
	@Value("#{constant['photo.origin.max.width']}") private int originMaxWidth;
	@Value("#{constant['photo.thumb.max.width']}") 	private int thumbMaxWidth;
	@Value("#{test['test.sample.dir.url']}")  		private String sampleDir;
	
	@Spy
	@InjectMocks
	private PhotoService photoService;
	
	@Before
	public void setUp() throws FileNotFoundException {
		MockitoAnnotations.initMocks(this);
		
		realPath = ResourceUtils.getFile(this.getClass().getResource("/")).getAbsolutePath();
		
		ReflectionTestUtils.setField(photoService, "realPath", realPath);
		ReflectionTestUtils.setField(photoService, "tempDir", tempDir);
		ReflectionTestUtils.setField(photoService, "originDir", originDir);
		ReflectionTestUtils.setField(photoService, "thumbDir", thumbDir);
		ReflectionTestUtils.setField(photoService, "originMaxWidth", originMaxWidth);
		ReflectionTestUtils.setField(photoService, "thumbMaxWidth", thumbMaxWidth);
	}

	@Test
	public void testGet() {
		int seq = 3;
		PhotoVo Photo = new PhotoVo();
		Photo.setSeq(seq);
		
		when(photoDao.get(seq)).thenReturn(Photo);
		
		//ACT
		PhotoVo resultPhoto = photoService.get(seq);
		
		//ASSERT
		assertEquals(Photo, resultPhoto);
	}
	
	@Test
	public void testGetWithLike() {
		int seq = 3;
		Set<Integer> likePhotos = new HashSet<>();
		likePhotos.add(seq);
		
		PhotoVo photo = new PhotoVo();
		photo.setSeq(seq);
		
		
		PhotoVo expectPhoto = new PhotoVo();
		expectPhoto.setSeq(seq);
		expectPhoto.setLike(true);
		
		when(photoDao.get(seq)).thenReturn(photo);
		
		//ACT
		PhotoVo resultPhoto = photoService.get(likePhotos, seq);
		
		//ASSERT
		assertEquals(expectPhoto, resultPhoto);
	}
	
	@Test
	public void testList() {
		HashMap<String, Object> map = new HashMap<>();
		List<PhotoVo> photos = new ArrayList<>();
		
		when(photoService.list(map)).thenReturn(photos);
		
		//ACT
		List<PhotoVo> resultStudies = photoService.list(map);
		
		//ASSERT
		assertEquals(photos, resultStudies);
	}
	
	
	@Test
	public void testSeqs() {
		List<Integer> seqs = new ArrayList<>();
		
		when(photoService.seqs()).thenReturn(seqs);
		
		//ACT
		List<Integer> resultSeq = photoService.seqs();
		
		//ASSERT
		assertEquals(seqs, resultSeq);
	}
	
	@Test
	public void testInsert() throws Exception {
		int seq = 3;
		String pathname = "SAMPLE_PATHNAME";
		String thumbnail = "SAMPLE_THUMB";
		
		PhotoVo photo = new PhotoVo();
		photo.setSeq(seq);
		photo.setPathname(pathname);
		photo.setThumbnail(thumbnail);
		
		boolean expect = true;
		
		when(photoDao.insert(photo)).thenReturn(expect);
		
		//ACT
		boolean result = photoService.insert(photo);
		
		//ASSERT
		assertEquals(expect, result);
		verify(fileHandler).move(realPath + tempDir + pathname, realPath + originDir + pathname);
		verify(fileHandler).move(realPath + tempDir + thumbnail, realPath + thumbDir + thumbnail);
	}
	
	@Test
	public void testUpdate() throws Exception {
		int seq = 3;
		boolean expect = true;
		String pathname = "SAMPLE_PATHNAME1";
		
		PhotoVo photo = new PhotoVo();
		photo.setSeq(seq);
		photo.setPathname(pathname);
		
		PhotoVo savedPhoto = new PhotoVo();
		savedPhoto.setSeq(seq);
		savedPhoto.setPathname(pathname);
		
		when(photoDao.get(seq)).thenReturn(savedPhoto);
		when(photoDao.update(photo)).thenReturn(expect);
		
		//ACT
		boolean result = photoService.update(photo);
		
		//ASSERT
		assertEquals(expect, result);
		verify(fileHandler, atMost(0)).delete(anyString(), anyString());
		verify(fileHandler, atMost(0)).move(anyString(), anyString());
	}
	
	@Test
	public void testUpdateWithNewPhoto() throws Exception {
		int seq = 3;
		String pathname1 = "SAMPLE_PATHNAME1";
		String pathname2 = "SAMPLE_PATHNAME2";
		String thumbnail1 = "SAMPLE_THUMB1";
		String thumbnail2 = "SAMPLE_THUMB2";
		
		PhotoVo photo = new PhotoVo();
		photo.setSeq(seq);
		photo.setPathname(pathname1);
		photo.setThumbnail(thumbnail1);
		
		PhotoVo savedPhoto = new PhotoVo();
		savedPhoto.setSeq(seq);
		savedPhoto.setPathname(pathname2);
		savedPhoto.setThumbnail(thumbnail2);
		
		boolean expect = true;
		
		when(photoDao.get(seq)).thenReturn(savedPhoto);
		when(photoDao.update(photo)).thenReturn(expect);
		
		//ACT
		boolean result = photoService.update(photo);
		
		//ASSERT
		assertEquals(expect, result);
		verify(fileHandler).delete(realPath + originDir, savedPhoto.getPathname());
		verify(fileHandler).delete(realPath + thumbDir, savedPhoto.getThumbnail());
		verify(fileHandler).move(realPath + tempDir + pathname1, realPath + originDir + pathname1);
		verify(fileHandler).move(realPath + tempDir + thumbnail1, realPath + thumbDir + thumbnail1);
	}
	
	@Test
	public void testDeleteResultTrue() {
		int seq = 3;
		boolean expected = true;
		String pathname = "SAMPLE_PATHNAME";
		String thumbnail = "SAMPLE_THUMB";
		
		PhotoVo photo = new PhotoVo();
		photo.setSeq(seq);
		photo.setPathname(pathname);
		photo.setThumbnail(thumbnail);
		
		when(photoDao.get(seq)).thenReturn(photo);
		when(photoDao.delete(seq)).thenReturn(expected);
		
		//ACT
		boolean result = photoService.delete(seq);
		
		//ASSERT
		assertEquals(expected, result);
		verify(fileHandler, times(1)).delete(eq(realPath + originDir), eq(pathname));
		verify(fileHandler, times(1)).delete(eq(realPath + thumbDir), eq(thumbnail));
		
	}
	
	@Test
	public void testDeleteResultFalse() {
		int seq = 3;
		boolean expected = false;
		
		PhotoVo photo = new PhotoVo();
		photo.setSeq(seq);
		
		when(photoDao.get(seq)).thenReturn(photo);
		when(photoDao.delete(seq)).thenReturn(expected);
		
		//ACT
		boolean result = photoService.delete(seq);
		
		//ASSERT
		assertEquals(expected, result);
		verify(fileHandler, atMost(0)).delete(anyString(), anyString());
	}
	
	@Test
	public void testDoLikeWithLikeTrue() {
		Set<Integer> likePhotos = new HashSet<>();
		int seq = 3;
		int likeCnt = 3;
		boolean like = true;
		
		PhotoVo photo = new PhotoVo();
		photo.setSeq(seq);
		photo.setLikeCnt(likeCnt);
		
		PhotoVo expectPhoto = new PhotoVo();
		expectPhoto.setSeq(seq);
		expectPhoto.setLikeCnt(likeCnt + 1);
		expectPhoto.setLike(like);
		
		when(photoDao.get(seq)).thenReturn(photo);
		
		//ACT
		PhotoVo resultPhoto = photoService.doLike(likePhotos, seq, like);
		
		//ASSERT
		assertEquals(expectPhoto, resultPhoto);
		verify(photoDao).update(photo);
	}
	
	@Test
	public void testDoLikeWithLikeFalse() {
		Set<Integer> likePhotos = new HashSet<>();
		int seq = 3;
		int likeCnt = 3;
		boolean like = false;
		
		PhotoVo photo = new PhotoVo();
		photo.setSeq(seq);
		photo.setLikeCnt(likeCnt);
		
		PhotoVo expectPhoto = new PhotoVo();
		expectPhoto.setSeq(seq);
		expectPhoto.setLikeCnt(likeCnt - 1);
		expectPhoto.setLike(like);
		
		when(photoDao.get(seq)).thenReturn(photo);
		
		//ACT
		PhotoVo resultPhoto = photoService.doLike(likePhotos, seq, like);
		
		//ASSERT
		assertEquals(expectPhoto, resultPhoto);
		verify(photoDao).update(photo);
	}
	
	@Test
	public void testSavePhoto() throws IllegalStateException, IOException {
		MultipartFile multipartFile = mock(MultipartFile.class);
		String filename = "SAMPE_FILENAME.JPG";
		String ext = "JPG";
		String date = "2019.01.01";
		String time = "15:01:01";
		String model = "SAMPLE_MODEL";
		
		Map<String, String> metadata = new HashMap<>();
		metadata.put("Date", date);
		metadata.put("Time", time);
		metadata.put("Model", model);
		
		when(multipartFile.getOriginalFilename()).thenReturn(filename);
		doReturn(metadata).when(photoService).getMetaData(any(File.class));
		
		//ACT
		PhotoVo resultPhoto = photoService.savePhoto(multipartFile);
		
		//ASSERT
		assertEquals(filename, resultPhoto.getFilename());
		assertEquals(date, resultPhoto.getDate());
		assertEquals(time, resultPhoto.getTime());
		assertEquals(model, resultPhoto.getDevice());
		verify(fileHandler).copy(any(File.class), any(File.class));
		verify(imageHandler).saveLowscaleImage(any(File.class), eq(originMaxWidth), eq(ext));
		verify(imageHandler).saveLowscaleImage(any(File.class), eq(thumbMaxWidth), eq(ext));
	}
	
	@Test
	public void testGetMetaData() throws IllegalStateException, IOException {
		File file = new File(realPath + sampleDir, "sample_image.jpg");
		
		String date = "2018.08.22";
		String time = "21:12:33";
		String model = "Canon EOS 70D";
		
		Map<String, String> metadata = new HashMap<>();
		metadata.put("Date", date);
		metadata.put("Time", time);
		metadata.put("Model", model);
		
		//ACT
		Map<String, String> resultMetadata = photoService.getMetaData(file);
		
		//ASSERT
		assertEquals(date, resultMetadata.get("Date"));
		assertEquals(time, resultMetadata.get("Time"));
		assertEquals(model, resultMetadata.get("Model"));
	}
}
