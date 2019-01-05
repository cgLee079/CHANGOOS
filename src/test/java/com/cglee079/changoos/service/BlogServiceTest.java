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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.BlogDao;
import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.util.FileHandler;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.ImageHandler;
import com.google.gson.JsonArray;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class BlogServiceTest {
	
	@Mock private BoardImageService boardImageService;
	@Mock private BoardFileService boardFileService;
	@Mock private BlogDao blogDao;
	@Mock private ImageHandler imageHandler;
	@Mock private FileHandler fileHandler;
	
	@Value("#{servletContext.getRealPath('/')}") private String realPath;
	
	@Value("#{location['blog.file.dir.url']}") 	private String fileDir;
	@Value("#{location['blog.image.dir.url']}")	private String imageDir;
	@Value("#{location['blog.thumb.dir.url']}") 	private String thumbDir;
	
	@Value("#{db['blog.file.tb.name']}") private String fileTB;
	@Value("#{db['blog.image.tb.name']}") private String imageTB;
	
	@Value("#{constant['blog.thumb.max.width']}")private int thumbMaxWidth;
	
	@Spy
	@InjectMocks
	private BlogService blogService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		ReflectionTestUtils.setField(blogService, "realPath", realPath);
		ReflectionTestUtils.setField(blogService, "fileDir", fileDir);
		ReflectionTestUtils.setField(blogService, "imageDir", imageDir);
		ReflectionTestUtils.setField(blogService, "thumbDir", thumbDir);
		ReflectionTestUtils.setField(blogService, "fileTB", fileTB);
		ReflectionTestUtils.setField(blogService, "imageTB", imageTB);
		ReflectionTestUtils.setField(blogService, "thumbMaxWidth", thumbMaxWidth);
	}

	@Test
	public void testGetTags() {
		List<String> tagDummys = new ArrayList<>();
		tagDummys.add("A B C ");
		tagDummys.add("D E F");
		Set<String> expectTags = new HashSet<>();
		expectTags.add("A");
		expectTags.add("B");
		expectTags.add("C");
		expectTags.add("D");
		expectTags.add("E");
		expectTags.add("F");
		
		when(blogDao.getTags()).thenReturn(tagDummys);
		
		//ACT
		Set<String> resultTags = blogService.getTags();
		
		//ASSERT
		assertEquals(expectTags, resultTags);
		
	}
	
	@Test
	public void testGetWithoutContent() {
		int seq = 3;
		BlogVo blog = BlogVo.builder()
				.seq(seq)
				.build();
		
		BlogVo expectBlog = BlogVo.builder()
				.seq(seq)
				.build();
		
		when(blogDao.get(seq)).thenReturn(blog);
		
		//ACT
		BlogVo resultBLog = blogService.get(seq);
		
		//ASSERT
		assertEquals(expectBlog, resultBLog);
	}
	
	@Test
	public void testGetWithContents() {
		int seq = 3;
		String contents = "&";
		String newContents = "&amp;";
		
		BlogVo blog = BlogVo.builder()
				.seq(seq)
				.contents(contents)
				.build();
		
		BlogVo expectBlog = BlogVo.builder()
				.seq(seq)
				.contents(newContents)
				.build();
		
		when(blogDao.get(seq)).thenReturn(blog);
		
		//ACT
		BlogVo resultBLog = blogService.get(seq);
		
		//ASSERT
		assertEquals(expectBlog, resultBLog);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDoViewVisitedByAdmin() {
		int seq = 3;
		Set<Integer> visitBlogs = new HashSet<>();
		visitBlogs.add(seq);
		
		BlogVo blog = BlogVo.builder()
				.seq(seq)
				.hits(0)
				.build();
		
		BlogVo expectedBlog = BlogVo.builder()
				.seq(seq)
				.hits(0)
				.build();
		
		when(blogDao.get(seq)).thenReturn(blog);
		
		//ACT
		BlogVo resultBlog = blogService.doView(visitBlogs, seq);
		
		//ASSERT
		assertEquals(expectedBlog, resultBlog);
	}
	
	@Test
	@WithMockUser(roles = "ADMIN")
	public void testDoViewNoneVisitedByAdmin() {
		int seq = 3;
		Set<Integer> visitBlogs = new HashSet<>();
		
		BlogVo blog = BlogVo.builder()
				.seq(seq)
				.hits(0)
				.build();
		
		BlogVo expectedBlog = BlogVo.builder()
				.seq(seq)
				.hits(0)
				.build();
		
		when(blogDao.get(seq)).thenReturn(blog);
		
		//ACT
		BlogVo resultBlog = blogService.doView(visitBlogs, seq);
		
		//ASSERT
		assertFalse(visitBlogs.contains(seq));
		assertEquals(expectedBlog, resultBlog);
	}
	
	@Test
	public void testDoViewVisitedByUser() {
		int seq = 3;
		Set<Integer> visitBlogs = new HashSet<>();
		visitBlogs.add(seq);
		
		BlogVo blog = BlogVo.builder()
				.seq(seq)
				.hits(0)
				.build();
		
		BlogVo expectedBlog = BlogVo.builder()
				.seq(seq)
				.hits(0)
				.build();
		
		when(blogDao.get(seq)).thenReturn(blog);
		
		
		//ACT
		BlogVo resultBlog = blogService.doView(visitBlogs, seq);
		
		//ASSERT
		assertEquals(expectedBlog, resultBlog);
	}
	
	@Test
	public void testDoViewNoneVisitedByUser() {
		int seq = 3;
		Set<Integer> visitBlogs = new HashSet<>();
		
		BlogVo blog = BlogVo.builder()
				.seq(seq)
				.hits(0)
				.build();
		
		BlogVo expectedBlog = BlogVo.builder()
				.seq(seq)
				.hits(1)
				.build();
		
		when(blogDao.get(seq)).thenReturn(blog);
		
		//ACT
		BlogVo resultBlog = blogService.doView(visitBlogs, seq);
		
		//ASSERT
		assertTrue(visitBlogs.contains(seq));
		assertEquals(expectedBlog, resultBlog);
	}
	
	
	
	@Test
	public void testList() {
		int seq = 3;
		HashMap<String, Object> params = new HashMap<>();
		List<BlogVo> blogs = new ArrayList<>();
		
		BlogVo blog = BlogVo.builder()
				.seq(seq)
				.build();
		
		BlogVo expectedBlog = BlogVo.builder()
				.seq(seq)
				.build();
		
		blogs.add(blog);
		
		when(blogDao.list(params)).thenReturn(blogs);
		
		//ACT
		List<BlogVo> resultBlogs = blogService.list(params);
		
		//ASSERT
		assertEquals(expectedBlog, resultBlogs.get(0));
	}
	
	@Test
	public void testPaging() {
		String page = "1";
		String perPgLine = "10";
		String contents = "<body>SAMPLE_CONTENTS\nNN<body>";
		String newContents = "SAMPLE_CONTENTS NN";
		JsonArray tags = new JsonArray();
		String tag1 = "A";
		String tag2 = "B";
		tags.add(tag1);
		tags.add(tag2);
		
		Map<String, Object> params = new HashMap<>();
		params.put("page", page);
		params.put("perPgLine", perPgLine);
		params.put("tags", tags.toString());
		
		List<BlogVo> blogs = new ArrayList<>();
		BlogVo blog = BlogVo.builder()
				.contents(contents)
				.build();
		blogs.add(blog);
		
		BlogVo expectedBlog = BlogVo.builder()
				.contents(newContents)
				.build();
		
		when(blogDao.list(params)).thenReturn(blogs);
		
		//ACT
		List<BlogVo> resultBlogs = blogService.paging(params);
		
		//ASSERT
		assertEquals(0, params.get("startRow"));
		assertEquals(tag1, ((List<String>)params.get("tags")).get(0));
		assertEquals(tag2, ((List<String>)params.get("tags")).get(1));
		assertEquals(expectedBlog, resultBlogs.get(0));
	}
	
	@Test
	public void testInsert() throws Exception {
		int seq = 3;
		MockMultipartFile thumbnailFile = new MockMultipartFile("thumbnailFile", new byte[1]);
		String contents = "SAMPLE_CONTENTS";
		String fileValues = "SAMPLE_FILEVALUES";
		String imageValues = "SAMPLE_IMAGEVALUES";
		String thumbnail = "SAMPLE_THUMBNAIL";
		String newContents = "SAMPLE_NEWCONTENTS";
		
		BlogVo blog = BlogVo.builder()
				.seq(seq)
				.contents(contents)
				.build();
		
		BlogVo expectBlog = BlogVo.builder()
				.seq(seq)
				.contents(newContents)
				.thumbnail(thumbnail)
				.date(Formatter.toDate(new Date()))
				.hits(0)
				.build();
		
		doReturn(thumbnail).when(blogService).saveThumbnail(blog, thumbnailFile);
		when(blogDao.insert(blog)).thenReturn(seq);
		when(boardImageService.insertImages(imageTB, imageDir, seq, contents, imageValues)).thenReturn(newContents);
		
		//ACT
		int result = blogService.insert(blog, thumbnailFile, imageValues, fileValues);
		
		//ASSERT
		assertEquals(seq, result);
		assertEquals(expectBlog, blog);
		verify(boardFileService).insertFiles(fileTB, fileDir, seq, fileValues);
		verify(blogDao).update(blog);
	}
	
	@Test
	public void testUpdate() throws Exception {
		int seq = 3;
		MockMultipartFile thumbnailFile = new MockMultipartFile("thumbnailFile", new byte[1]);
		String fileValues = "SAMPLE_FILEVALUES";
		String imageValues = "SAMPLE_IMAGEVALUES";
		String thumbnail = "SAMPLE_THUMBNAIL";
		String contents = "SAMPLE_CONTENTS";
		String newContents = "SAMPLE_NEWCONTENTS";
		boolean expect  = true;

		BlogVo blog = BlogVo.builder()
				.seq(seq)
				.contents(contents)
				.build();
		
		BlogVo expectBlog = BlogVo.builder()
				.seq(seq)
				.contents(newContents)
				.thumbnail(thumbnail)
				.build();
		
		when(blogDao.update(blog)).thenReturn(expect);
		doReturn(thumbnail).when(blogService).saveThumbnail(blog, thumbnailFile);
		when(boardImageService.insertImages(imageTB, imageDir, seq, contents, imageValues)).thenReturn(newContents);
		
		//ACT
		boolean result = blogService.update(blog, thumbnailFile, imageValues, fileValues);
		
		//ASSERT
		assertEquals(expect, result);
		assertEquals(expectBlog, blog);
		verify(boardFileService).insertFiles(fileTB, fileDir, seq, fileValues);
	}
	
	@Test
	public void testDeleteResultTrue() {
		int seq = 3;
		boolean expected = true;
		String thumbnail = "SAMPLE_THUMBNAIL";
		List<BoardFileVo> files = new ArrayList<>();
		List<BoardImageVo> images = new ArrayList<>();
		files.add(mock(BoardFileVo.class));
		files.add(mock(BoardFileVo.class));
		images.add(mock(BoardImageVo.class));
		images.add(mock(BoardImageVo.class));
		
		BlogVo blog = BlogVo.builder()
				.seq(seq)
				.thumbnail(thumbnail)
				.images(images)
				.files(files)
				.build();
		
		when(blogDao.get(seq)).thenReturn(blog);
		when(blogDao.delete(seq)).thenReturn(expected);
		
		//ACT
		boolean result = blogService.delete(seq);
		
		//ASSERT
		assertEquals(expected, result);
		verify(fileHandler).delete(realPath + thumbDir, blog.getThumbnail());
		verify(fileHandler, times(2)).delete(eq(realPath + fileDir), anyString());
		verify(fileHandler, times(2)).delete(eq(realPath + imageDir), anyString());
	}
	
	@Test
	public void testDeleteResultFalse() {
		int seq = 3;
		boolean expected = false;
		BlogVo blog = BlogVo.builder()
				.seq(seq)
				.build();
		
		when(blogDao.get(seq)).thenReturn(blog);
		when(blogDao.delete(seq)).thenReturn(expected);
		
		//ACT
		boolean result = blogService.delete(seq);
		
		//ASSERT
		assertEquals(expected, result);
		verify(fileHandler, atMost(0)).delete(anyString(), anyString());
	}
	
	@Test
	public void testSaveThumbnailWithFile() throws IllegalStateException, IOException {
		BlogVo blog = mock(BlogVo.class);
		MultipartFile thumbnailFile = mock(MultipartFile.class);
		
		long filesize = 1024;
		String filename = "SAMPLE_FILENAME.PNG";
		
		when(thumbnailFile.getSize()).thenReturn(filesize);
		when(thumbnailFile.getOriginalFilename()).thenReturn(filename);
		
		//ACT
		String resultPathname = blogService.saveThumbnail(blog, thumbnailFile);
		
		//ASSERT
		verify(fileHandler).delete(realPath + thumbDir, blog.getThumbnail());
		verify(thumbnailFile).transferTo(any(File.class));
		verify(imageHandler).saveLowscaleImage(any(File.class), eq(thumbMaxWidth), eq("PNG"));
	}
	
	@Test
	public void testSaveThumbnailWithoutFile() throws IllegalStateException, IOException {
		BlogVo blog = mock(BlogVo.class);
		MultipartFile thumbnailFile = spy(MultipartFile.class);
		
		long filesize = 0;
		
		doReturn(filesize).when(thumbnailFile).getSize();
		
		//ACT
		String resultPathname = blogService.saveThumbnail(blog, thumbnailFile);
		
		//ASSERT
		assertEquals(null, resultPathname);
		verify(fileHandler, atMost(0)).delete(anyString(), anyString());
		verify(thumbnailFile, atMost(0)).transferTo(any(File.class));
		verify(imageHandler, atMost(0)).saveLowscaleImage(any(File.class), eq(thumbMaxWidth), anyString());
	}
	
}
