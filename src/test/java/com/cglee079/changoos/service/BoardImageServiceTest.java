package com.cglee079.changoos.service;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.BoardImageDao;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.util.FileHandler;
import com.cglee079.changoos.util.ImageHandler;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class BoardImageServiceTest {
	private String realPath;
	
	@Mock private BoardImageDao boardImageDao;
	@Mock private ImageHandler imageHandler;
	@Mock private FileHandler fileHandler;

	@Value("#{location['temp.image.dir.url']}")  	private String tempDir;
 	@Value("#{constant['image.max.width']}") 		private int maxWidth;
 	
	@Value("#{constant['image.status.id.new']}")	private String statusNew;
	@Value("#{constant['image.status.id.unnew']}") 	private String statusUnnew;
	@Value("#{constant['image.status.id.remove']}") private String statusRemove;
	@Value("#{test['test.sample.dir.url']}")  		private String sampleDir;

	@Spy
	@InjectMocks
	private BoardImageService boardImageService;
	
	@Before
	public void setUp() throws FileNotFoundException{
		MockitoAnnotations.initMocks(this);
	
		realPath = ResourceUtils.getFile(this.getClass().getResource("/")).getAbsolutePath();
	
		ReflectionTestUtils.setField(boardImageService, "realPath", realPath);
		ReflectionTestUtils.setField(boardImageService, "tempDir", tempDir);
		ReflectionTestUtils.setField(boardImageService, "maxWidth", maxWidth);
		ReflectionTestUtils.setField(boardImageService, "statusNew", statusNew);
		ReflectionTestUtils.setField(boardImageService, "statusUnnew", statusUnnew);
		ReflectionTestUtils.setField(boardImageService, "statusRemove", statusRemove);
	}
	
	@Test
	public void testSaveBase64() throws IOException {
		File sampleImage = new File(realPath + sampleDir, "sample_image.jpg");
		String base64 = Base64.encodeBase64String(FileUtils.readFileToByteArray(sampleImage));
		
		//ACT
		String resultPathname = boardImageService.saveBase64("," + base64);
		
		//ASSERT
		File saveImage = new File(realPath + tempDir, resultPathname);
		byte[] expectByte = ((DataBufferByte) ImageIO.read(sampleImage).getData().getDataBuffer()).getData();
		byte[] resultByte = ((DataBufferByte) ImageIO.read(saveImage).getData().getDataBuffer()).getData();
		assertTrue(saveImage.exists());
		assertArrayEquals(expectByte, resultByte);
		
	}
	
	@Test
	public void testSaveImage() throws FileNotFoundException, IOException {
		File sampleImage = new File(realPath + sampleDir, "sample_image.jpg");
		MultipartFile multipartFile = new MockMultipartFile(sampleImage.getName(), sampleImage.getName(), null, new FileInputStream(sampleImage));

		//ACT
		String resultPathname = boardImageService.saveImage(multipartFile);
		
		//ASSERT
		File saveImage = new File(realPath + tempDir, resultPathname);
		assertTrue(saveImage.exists());
		assertArrayEquals(Files.readAllBytes(sampleImage.toPath()), Files.readAllBytes(saveImage.toPath()));
	}
	
	@Test
	public void testInsertImagesWithStatusNewImage() throws JsonParseException, JsonMappingException, IOException{
		String TB = "SAMPLE_TB";
		String dir = "SAMPLE_DIR";
		String pathname = "SAMPLE_PATHNAME.JPG";
		String contents = tempDir + pathname;
		String expectContents = dir + pathname;
		int boardSeq = 3;
		
		List<BoardImageVo> images = new ArrayList<>();
		BoardImageVo image = new BoardImageVo();
		image.setPathname(pathname);
		image.setStatus(statusNew);
		images.add(image);
		
		String imageValues = new JSONArray(new Gson().toJson(images)).toString();
		  
		when(boardImageDao.insert(eq(TB), any(BoardImageVo.class))).thenReturn(true);
		when(fileHandler.move(any(File.class),any(File.class))).thenReturn(true);
		
		//ACT
		String resultContents = boardImageService.insertImages(TB, dir, boardSeq, contents, imageValues);
		
		//ASSERT
		assertEquals(expectContents, resultContents);
		verify(fileHandler, times(1)).move(any(File.class), any(File.class));
		
	}
	
	@Test
	public void testInsertImagesWithStatusUnnewImage() throws JsonParseException, JsonMappingException, IOException{
		String TB = "SAMPLE_TB";
		String dir = "SAMPLE_DIR";
		String pathname = "SAMPLE_PATHNAME.JPG";
		String contents = tempDir + pathname;
		int boardSeq = 3;
		
		List<BoardImageVo> images = new ArrayList<>();
		BoardImageVo image = new BoardImageVo();
		image.setPathname(pathname);
		image.setStatus(statusUnnew);
		images.add(image);
		
		String imageValues = new JSONArray(new Gson().toJson(images)).toString();
		  
		//ACT
		String resultContents = boardImageService.insertImages(TB, dir, boardSeq, contents, imageValues);
		
		//ASSERT
		assertEquals(contents, resultContents);
		verify(fileHandler, times(1)).delete(anyString(), eq(pathname));
	}
	
	@Test
	public void testInsertImagesWithStatusRemoveImage() throws JsonParseException, JsonMappingException, IOException{
		String TB = "SAMPLE_TB";
		String dir = "SAMPLE_DIR";
		String pathname = "SAMPLE_PATHNAME.JPG";
		String contents = tempDir + pathname;
		int boardSeq = 3;
		int seq = 3;
		
		List<BoardImageVo> images = new ArrayList<>();
		BoardImageVo image = new BoardImageVo();
		image.setSeq(seq);
		image.setPathname(pathname);
		image.setStatus(statusRemove);
		images.add(image);
		
		String imageValues = new JSONArray(new Gson().toJson(images)).toString();
		  
		when(boardImageDao.delete(TB, seq)).thenReturn(true);
		
		//ACT
		String resultContents = boardImageService.insertImages(TB, dir, boardSeq, contents, imageValues);
		
		//ASSERT
		assertEquals(contents, resultContents);
		verify(fileHandler, times(1)).delete(realPath + dir, pathname);
		verify(boardImageDao, times(1)).delete(TB, seq);
		
	}
}
