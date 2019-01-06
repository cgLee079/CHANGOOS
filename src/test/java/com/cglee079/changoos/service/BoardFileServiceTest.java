package com.cglee079.changoos.service;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

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

import com.cglee079.changoos.dao.BoardFileDao;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.util.FileHandler;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml"})
public class BoardFileServiceTest {
	private String realPath;
	
	@Mock private BoardFileDao boardFileDao;
	@Mock private FileHandler fileHandler;
	
	@Value("#{location['temp.file.dir.url']}") 	private String tempDir;
	
	@Value("#{constant['file.status.id.new']}")		private String statusNew;
	@Value("#{constant['file.status.id.unnew']}") 	private String statusUnnew;
	@Value("#{constant['file.status.id.remove']}") 	private String statusRemove;
	@Value("#{location['test.sample.dir.url']}")  		private String sampleDir;

	@Spy
	@InjectMocks
	private BoardFileService boardFileService;
	
	@Before
	public void setUp() throws FileNotFoundException{
		MockitoAnnotations.initMocks(this);
	
		realPath = ResourceUtils.getFile(this.getClass().getResource("/")).getAbsolutePath();
	
		ReflectionTestUtils.setField(boardFileService, "realPath", realPath);
		ReflectionTestUtils.setField(boardFileService, "tempDir", tempDir);
		ReflectionTestUtils.setField(boardFileService, "statusNew", statusNew);
		ReflectionTestUtils.setField(boardFileService, "statusUnnew", statusUnnew);
		ReflectionTestUtils.setField(boardFileService, "statusRemove", statusRemove);
	}
	
	@Test
	public void testSaveFile() throws FileNotFoundException, IOException {
		File sampleFile = new File(realPath + sampleDir, "sample_file.pdf");
		MultipartFile multipartFile = new MockMultipartFile(sampleFile.getName(), sampleFile.getName(), null, new FileInputStream(sampleFile));

		//ACT
		String resultPathname = boardFileService.saveFile(multipartFile);
		
		//ASSERT
		File saveFile = new File(realPath + tempDir, resultPathname);
		assertTrue(saveFile.exists());
		assertArrayEquals(Files.readAllBytes(sampleFile.toPath()), Files.readAllBytes(saveFile.toPath()));
	}
	
	@Test
	public void testInsertFilesWithStatusNewFile() throws JsonParseException, JsonMappingException, IOException{
		String TB = "SAMPLE_TB";
		String dir = "SAMPLE_DIR";
		String pathname = "SAMPLE_PATHNAME.JPG";
		int boardSeq = 3;
		
		List<BoardFileVo> files = new ArrayList<>();
		BoardFileVo file = BoardFileVo.builder()
				.pathname(pathname)
				.status(statusNew)
				.build();
		files.add(file);
		
		String fileValues = new JSONArray(new Gson().toJson(files)).toString();
		  
		when(boardFileDao.insert(eq(TB), any(BoardFileVo.class))).thenReturn(true);
		when(fileHandler.move(any(File.class),any(File.class))).thenReturn(true);
		
		//ACT
		boardFileService.insertFiles(TB, dir, boardSeq, fileValues);
		
		//ASSERT
		verify(fileHandler, times(1)).move(any(File.class), any(File.class));
		
	}
	
	@Test
	public void testInsertFilessWithStatusUnnewFile() throws JsonParseException, JsonMappingException, IOException{
		String TB = "SAMPLE_TB";
		String dir = "SAMPLE_DIR";
		String pathname = "SAMPLE_PATHNAME.JPG";
		int boardSeq = 3;
		
		List<BoardFileVo> files = new ArrayList<>();
		BoardFileVo file = BoardFileVo.builder()
				.pathname(pathname)
				.status(statusUnnew)
				.build();
		files.add(file);
		
		String fileValues = new JSONArray(new Gson().toJson(files)).toString();
		  
		//ACT
		boardFileService.insertFiles(TB, dir, boardSeq, fileValues);
		
		//ASSERT
		verify(fileHandler, times(1)).delete(realPath + dir, pathname);
		
	}
	
	@Test
	public void testInsertFilessWithStatusRemoveFile() throws JsonParseException, JsonMappingException, IOException{
		String TB = "SAMPLE_TB";
		String dir = "SAMPLE_DIR";
		String pathname = "SAMPLE_PATHNAME.JPG";
		int boardSeq = 3;
		int seq = 3;
		
		List<BoardFileVo> files = new ArrayList<>();
		BoardFileVo file = BoardFileVo.builder()
				.seq(seq)
				.pathname(pathname)
				.status(statusRemove)
				.build();
		files.add(file);
		
		String fileValues = new JSONArray(new Gson().toJson(files)).toString();
		  
		when(boardFileDao.delete(TB, seq)).thenReturn(true);
		
		//ACT
		boardFileService.insertFiles(TB, dir, boardSeq, fileValues);
		
		//ASSERT
		verify(fileHandler, times(1)).delete(realPath + dir, pathname);
		verify(boardFileDao, times(1)).delete(TB, seq);
		
	}
	
}
