package com.cglee079.changoos.service;
import static org.junit.Assert.assertArrayEquals;

import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.BoardImageDao;
import com.cglee079.changoos.util.FileHandler;
import com.cglee079.changoos.util.ImageHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class BoardImageServiceTest {
	private String realPath;
	
	@Autowired private FileHandler realFileHandler;
	
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
		assertArrayEquals(expectByte, resultByte);
		
	}
	
	@Test
	public void testSaveImage() throws FileNotFoundException, IOException {
		File sampleImage = new File(realPath, "sample_image.jpg");
		MultipartFile multipartFile = new MockMultipartFile(sampleImage.getName(), sampleImage.getName(), null, new FileInputStream(sampleImage));

		//ACT
		String resultPathname = boardImageService.saveImage(multipartFile);
		
		//ASSERT
		File saveImage = new File(realPath + tempDir, resultPathname);
		byte[] expectByte = ((DataBufferByte) ImageIO.read(sampleImage).getData().getDataBuffer()).getData();
		byte[] resultByte = ((DataBufferByte) ImageIO.read(saveImage).getData().getDataBuffer()).getData();
		assertArrayEquals(expectByte, resultByte);
		
	}
}
