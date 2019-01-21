package com.cglee079.changoos.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;
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
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/appServlet/**-context.xml"})
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
		Map<String, Object> params = new HashMap<>();
		Set<String> totalTags = new HashSet<>();
		int count = 3;
		String tags = "SAMPLE_TAGS";
		
		params.put("enabled", true);
		params.put("tags", tags);
		
		when(blogService.getTags()).thenReturn(totalTags);
		when(blogService.count(params)).thenReturn(count);
		
		mockMvc.perform(get("/blogs")
				.param("tags", tags))
			.andExpect(status().isOk())
			.andExpect(view().name("blog/blog_list"))
			.andExpect(model().attribute("tags", tags))
			.andExpect(model().attribute("totalTags", totalTags))
			.andExpect(model().attribute("count", count));
	}
	
	@Test
	public void testBlogListPaging() throws Exception {
		Map<String, Object> params = new HashMap<>();
		List<BlogVo> blogs = new ArrayList<>();
		params.put("enabled", true);
		
		when(blogService.list(params)).thenReturn(blogs);
		
		mockMvc.perform(get("/blogs/records"))
			.andExpect(status().isOk())
			.andExpect(content().json(new Gson().toJson(blogs)));
	}
	
	@Test
	public void testBlogView() throws Exception {
		int seq = 3;
		List<BoardFileVo> files = new ArrayList<BoardFileVo>();
		Set<Integer> visitBlogs = new HashSet<>();
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("visitBlogs", visitBlogs);
		
		BlogVo blog = BlogVo.builder()
				.files(files)
				.build();
		
		when(blogService.doView((Set<Integer>) session.getAttribute("visitBlogs"), seq)).thenReturn(blog);
		
		mockMvc.perform(get("/blogs/" + seq)
				.session(session))
			.andExpect(status().isOk())
			.andExpect(view().name("blog/blog_view"))
			.andExpect(model().attribute("blog", blog))
			.andExpect(model().attribute("files", files));
	}
	
	@Test
	public void testBlogManage() throws Exception {
		mockMvc.perform(get("/mgnt/blogs"))
			.andExpect(status().isOk())
			.andExpect(view().name("blog/blog_manage"));
	}
	
	@Test
	public void testBlogManagePaging() throws Exception {
		Map<String, Object> params = new HashMap<>();
		List<BlogVo> blogs = new ArrayList<>();
				
		when(blogService.list(params)).thenReturn(blogs);
		
		mockMvc.perform(get("/mgnt/blogs/records"))
			.andExpect(status().isOk())
			.andExpect(content().json(new Gson().toJson(blogs).toString()));
	}
	
	@Test
	public void testBlogUpload() throws Exception {
		mockMvc.perform(get("/blogs/post"))
			.andExpect(status().isOk())
			.andExpect(view().name("blog/blog_upload"));
	}
	
	@Test
	public void testBlogModify() throws Exception {
		int seq = 3;
		List<BoardFileVo> files =  new ArrayList<>();
		List<BoardImageVo> images =  new ArrayList<>();
		
		BlogVo blog = BlogVo.builder()
				.images(images)
				.files(files)
				.build();
		
		when(blogService.get(seq)).thenReturn(blog);
		
		mockMvc.perform(get("/blogs/post/" + seq))
			.andExpect(status().isOk())
			.andExpect(view().name("blog/blog_upload"))
			.andExpect(model().attribute("blog", blog))
			.andExpect(model().attribute("files", files))
			.andExpect(model().attribute("images", images));
	}
	
	@Test
	public void testBlogDoUpload() throws Exception {
		String imageValues = "SAMPLE_IMAGEVALUES";
		String fileValues = "SAMPLE_FILEVALUES";
		String tempDirId = "SAMPLE_TEMPDIR_ID";
		int seq = 3;
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("tempDirId", tempDirId);
		
		when(blogService.insert(any(BlogVo.class), eq((String)session.getAttribute("tempDirId")), same(imageValues), same(fileValues))).thenReturn(seq);
		
		mockMvc.perform(post("/blogs/post")
				.session(session)
				.param("imageValues", imageValues)
				.param("fileValues", fileValues))
			.andExpect(redirectedUrl("/blogs/" + seq))
			.andExpect(status().isFound());
	}
	
	@Test
	public void testBlogDoModify() throws Exception {
		String imageValues = "SAMPLE_IMAGEVALUES";
		String fileValues = "SAMPLE_FILEVALUES";
		String tempDirId = "SAMPLE_TEMPDIR_ID";
		int seq = 3;
		
		MockHttpSession session = new MockHttpSession();
		session.setAttribute("tempDirId", tempDirId);
		
		mockMvc.perform(put("/blogs/post/" + seq)
				.session(session)
				.param("imageValues", imageValues)
				.param("fileValues", fileValues))
			.andExpect(redirectedUrl("/blogs/" + seq))
			.andExpect(status().isFound());
		
		verify(blogService).update(any(BlogVo.class), eq((String)session.getAttribute("tempDirId")), same(imageValues), same(fileValues));
	}
	
	@Test
	public void testBlogDoDelete() throws Exception {
		int seq = 3;
		boolean result = true;
		
		when(blogService.delete(seq)).thenReturn(result);
		
		mockMvc.perform(delete("/blogs/post/" + seq))
			.andExpect(status().isOk())
			.andExpect(content().json(new JSONObject().put("result", result).toString()));
		
	}
	
}
