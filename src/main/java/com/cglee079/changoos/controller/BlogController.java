package com.cglee079.changoos.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.service.BlogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Controller
public class BlogController {
	@Autowired
	private BlogService blogService;

	/** 블로그 리스트로 이동 **/
	@RequestMapping(value = "/blogs", method = RequestMethod.GET)
	public String blogList(Model model, @RequestParam Map<String, Object> params){
		params.put("enabled", true);
		String tags = (String)params.get("tags");
		
		Set<String> totalTags = blogService.getTags();
		int count = blogService.count(params);
		
		model.addAttribute("totalTags", totalTags);
		model.addAttribute("count", count);
		model.addAttribute("tags", tags);
		
		
		return "blog/blog_list";
	}

	/** 블로그 페이징 **/
	@ResponseBody
	@RequestMapping(value = "/blogs/records", method = RequestMethod.GET)
	public String blogPaging(@RequestParam Map<String, Object> params) {
		params.put("enabled", true);
		
		List<BlogVo> blogs = blogService.list(params);
		
		return new Gson().toJson(blogs).toString();
	}

	/** 블로그로 이동 **/
	@RequestMapping(value = "/blogs/{seq}", method = RequestMethod.GET)
	public String blogView(HttpSession session, Model model, @PathVariable int seq){
		BlogVo blog = blogService.doView((Set<Integer>) session.getAttribute("visitBlogs"), seq);
		model.addAttribute("blog", blog);
		model.addAttribute("files", blog.getFiles());
		
		return "blog/blog_view";
	}

	@RequestMapping(value = "/mgnt/blogs", method = RequestMethod.GET)
	public String blogManage(Model model) {
		return "blog/blog_manage";
	}

	/** 블로그 관리 페이지 리스트, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/blogs/records", method = RequestMethod.GET)
	public String blogManagePaging(@RequestParam Map<String, Object> params) {
		List<BlogVo> blogs = blogService.list(params);
		return new Gson().toJson(blogs).toString();
	}

	/** 블로그 업로드 페이지로 이동 **/
	@RequestMapping(value = "/blogs/post", method = RequestMethod.GET)
	public String blogUpload(Model model) throws SQLException, JsonProcessingException {
		return "blog/blog_upload";
	}

	/** 블로그 수정 페이지로 이동 **/
	@RequestMapping(value = "/blogs/post/{seq}", method = RequestMethod.GET)
	public String blogModify(Model model, @PathVariable int seq){
		BlogVo blog = blogService.get(seq);

		model.addAttribute("blog", blog);
		model.addAttribute("files", blog.getFiles());
		model.addAttribute("images", blog.getImages());

		return "blog/blog_upload";
	}

	/** 블로그 업로드 
	 * @throws IOException 
	 * @throws IllegalStateException **/
	@RequestMapping(value = "/blogs/post", method = RequestMethod.POST )
	public String blogDoUpload(HttpSession session, BlogVo blog, String imageValues, String fileValues) throws IllegalStateException, IOException{
		int seq = blogService.insert(blog, (String)session.getAttribute("tempDirId"), imageValues, fileValues);
		return "redirect:" + "/blogs/" + seq;
	}
	
	@ResponseBody
	@RequestMapping(value = "/blogs/post/thumbnail", method = RequestMethod.POST )
	public String blogDoThumbUpload(HttpSession session, MultipartFile thumbnailFile){
		String pathname = blogService.saveThumbnail(thumbnailFile, (String)session.getAttribute("tempDirId"));
		
		JSONObject result = new JSONObject();
		result.put("pathname", pathname);
		
		return result.toString();
	}
	
	/** 블로그 수정 
	 * @throws IOException 
	 * @throws IllegalStateException **/
	@RequestMapping(value = "/blogs/post/{seq}", method = RequestMethod.PUT)
	public String blogDoModify(HttpSession session, BlogVo blog, String imageValues, String fileValues) throws IllegalStateException, IOException{
		blogService.update(blog, (String)session.getAttribute("tempDirId"), imageValues, fileValues);
		return "redirect:" + "/blogs/" + blog.getSeq();
	}

	/** 블로그 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/blogs/post/{seq}", method = RequestMethod.DELETE)
	public String blogDoDelete(@PathVariable int seq){
		boolean result = blogService.delete(seq);
		return new JSONObject().put("result", result).toString();
	}
}
