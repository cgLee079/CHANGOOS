package com.cglee079.changoos.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.service.BlogService;
import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml")
public class BlogControllerTest {
	@Mock
	private BlogService blogService;

	@InjectMocks
	private BlogController blogController;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(blogController).build();
	}
	
	@Test
	public void testBlogList() throws Exception {
		List<String> tags = new ArrayList<>();
		
		when(blogService.getTags()).thenReturn(tags);
		
		mockMvc.perform(get("/blog"))
			.andExpect(status().isOk())
			.andExpect(view().name("blog/blog_list"))
			.andExpect(model().attribute("tags", tags));
	}
	
	@Test
	public void testBlogListPaging() throws Exception {
		Map<String, Object> params = new HashMap<>();
		List<BlogVo> blogs = new ArrayList<>();
		params.put("enabled", true);
		int count = 3;
		
		when(blogService.paging(params)).thenReturn(blogs);
		when(blogService.count(params)).thenReturn(count);
		
		String data = new Gson().toJson(blogs);
		JSONArray dataJson = new JSONArray(data);

		JSONObject result = new JSONObject();
		result.put("count", count);
		result.put("data", dataJson);
		
		mockMvc.perform(get("/blog/paging"))
			.andExpect(status().isOk())
			.andExpect(content().json(result.toString()));
	}
	
	@Test
	public void testBlogView() throws Exception {
		int seq = 3;
		BlogVo blog = new BlogVo();
		List<BoardFileVo> files = new ArrayList<BoardFileVo>();
		blog.setFiles(files);
		List<Integer> visitBlogs = new ArrayList<>();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("visitBlogs", visitBlogs);
		
		when(blogService.doView((List<Integer>) session.getAttribute("visitBlogs"), seq)).thenReturn(blog);
		
		mockMvc.perform(get("/blog/view")
				.session(session)
				.param("seq", String.valueOf(seq)))
			.andExpect(status().isOk())
			.andExpect(view().name("blog/blog_view"))
			.andExpect(model().attribute("blog", blog))
			.andExpect(model().attribute("files", files));
	}
	
	@Test
	public void testBlogManage() throws Exception {
		mockMvc.perform(get("/mgnt/blog"))
			.andExpect(status().isOk())
			.andExpect(view().name("blog/blog_manage"));
	}
	
	@Test
	public void testBlogManagePaging() throws Exception {
		Map<String, Object> params = new HashMap<>();
		List<BlogVo> blogs = new ArrayList<>();
				
		when(blogService.list(params)).thenReturn(blogs);
		
		mockMvc.perform(get("/mgnt/blog/paging"))
			.andExpect(status().isOk())
			.andExpect(content().json(new Gson().toJson(blogs).toString()));
	}
	
	@Test
	public void testBlogUpload() throws Exception {
		mockMvc.perform(get("/mgnt/blog/upload"))
			.andExpect(status().isOk())
			.andExpect(view().name("blog/blog_upload"));
	}
	
	@Test
	public void testBlogModify() throws Exception {
		int seq = 3;
		BlogVo blog = new BlogVo();
		List<BoardFileVo> files =  new ArrayList<>();
		List<BoardImageVo> images =  new ArrayList<>();
		blog.setFiles(files);
		blog.setImages(images);
		
		when(blogService.get(seq)).thenReturn(blog);
		
		mockMvc.perform(get("/mgnt/blog/upload")
				.param("seq", String.valueOf(seq)))
			.andExpect(status().isOk())
			.andExpect(view().name("blog/blog_upload"))
			.andExpect(model().attribute("blog", blog))
			.andExpect(model().attribute("files", files))
			.andExpect(model().attribute("images", images));
	}
	
	@Test
	public void testBlogDoUpload() throws Exception {
		MockMultipartFile thumbnailFile = new MockMultipartFile("thumbnailFile", new byte[1]);
		String imageValues = "SAMPLE_IMAGEVALUES";
		String fileValues = "SAMPLE_FILEVALUES";
		int seq = 3;
		
		when(blogService.insert(any(BlogVo.class), same(thumbnailFile), same(imageValues), same(fileValues))).thenReturn(seq);
		
		mockMvc.perform(fileUpload("/mgnt/blog/upload.do")
				.file(thumbnailFile)
				.param("imageValues", imageValues)
				.param("fileValues", fileValues))
			.andExpect(redirectedUrl("/blog/view?seq=" + String.valueOf(seq)))
			.andExpect(status().isFound());
	}
	
	@Test
	public void testBlogDoModify() throws Exception {
		MockMultipartFile thumbnailFile = new MockMultipartFile("thumbnailFile", new byte[1]);
		String imageValues = "SAMPLE_IMAGEVALUES";
		String fileValues = "SAMPLE_FILEVALUES";
		int seq = 3;
		boolean result = true;
		
		when(blogService.update(any(BlogVo.class), same(thumbnailFile), same(imageValues), same(fileValues))).thenReturn(result);
		
		mockMvc.perform(fileUpload("/mgnt/blog/upload.do")
				.file(thumbnailFile)
				.param("seq", String.valueOf(seq))
				.param("imageValues", imageValues)
				.param("fileValues", fileValues))
			.andExpect(redirectedUrl("/blog/view?seq=" + String.valueOf(seq)))
			.andExpect(status().isFound());
		
		verify(blogService).update(any(BlogVo.class), same(thumbnailFile), same(imageValues), same(fileValues));
	}
	
	@Test
	public void testBlogDoDelete() throws Exception {
		int seq = 3;
		boolean result = true;
		
		when(blogService.delete(seq)).thenReturn(result);
		
		mockMvc.perform(post("/mgnt/blog/delete.do")
				.param("seq", String.valueOf(seq)))
			.andExpect(status().isOk())
			.andExpect(content().json(new JSONObject().put("result", result).toString()));
		
	}
	
}
