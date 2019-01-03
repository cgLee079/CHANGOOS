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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.service.BlogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

@Controller
public class BlogController {
	@Autowired
	private BlogService blogService;

	/** 블로그 리스트로 이동 **/
	@RequestMapping(value = "/blog")
	public String blogList(Model model) throws SQLException, JsonProcessingException {
		Set<String> tags = blogService.getTags();
		model.addAttribute("tags", tags);
		return "blog/blog_list";
	}

	/** 블로그 페이징 **/
	@ResponseBody
	@RequestMapping(value = "/blog/paging", method = RequestMethod.GET)
	public String blogPaging(@RequestParam Map<String, Object> params) throws SQLException, JsonProcessingException {
		params.put("enabled", true);
		
		List<BlogVo> blogs = blogService.paging(params);
		int count = blogService.count(params);

		String data = new Gson().toJson(blogs);
		JSONArray dataJson = new JSONArray(data);

		JSONObject result = new JSONObject();
		result.put("count", count);
		result.put("data", dataJson);
		return result.toString();
	}

	/** 블로그로 이동 **/
	@RequestMapping("/blog/view")
	public String blogView(HttpSession session, Model model, int seq) throws SQLException, JsonProcessingException {
		BlogVo blog = blogService.doView((Set<Integer>) session.getAttribute("visitBlogs"), seq);
		model.addAttribute("blog", blog);
		model.addAttribute("files", blog.getFiles());
		
		return "blog/blog_view";
	}

	/** 블로그 관리 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/blog")
	public String blogManage(Model model) {
		return "blog/blog_manage";
	}

	/** 블로그 관리 페이지 리스트, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/blog/paging")
	public String blogManagePaging(@RequestParam Map<String, Object> params) {
		List<BlogVo> blogs = blogService.list(params);
		return new Gson().toJson(blogs).toString();
	}

	/** 블로그 업로드 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/blog/upload", params = "!seq")
	public String blogUpload(Model model) throws SQLException, JsonProcessingException {
		return "blog/blog_upload";
	}

	/** 블로그 수정 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/blog/upload", params = "seq")
	public String blogModify(Model model, int seq) throws SQLException, JsonProcessingException {
		BlogVo blog = blogService.get(seq);

		model.addAttribute("blog", blog);
		model.addAttribute("files", blog.getFiles());
		model.addAttribute("images", blog.getImages());

		return "blog/blog_upload";
	}

	/** 블로그 업로드 **/
	@RequestMapping(value = "/mgnt/blog/upload.do", method = RequestMethod.POST, params = "!seq")
	public String blogDoUpload(Model model, BlogVo blog, MultipartFile thumbnailFile, String imageValues,
			String fileValues) throws SQLException, IllegalStateException, IOException {
		int seq = blogService.insert(blog, thumbnailFile, imageValues, fileValues);
		return "redirect:" + "/blog/view?seq=" + seq;
	}

	/** 블로그 수정 **/
	@RequestMapping(value = "/mgnt/blog/upload.do", method = RequestMethod.POST, params = "seq")
	public String blogDoModify(Model model, BlogVo blog, MultipartFile thumbnailFile, String imageValues,
			String fileValues) throws SQLException, IllegalStateException, IOException {
		blogService.update(blog, thumbnailFile, imageValues, fileValues);
		return "redirect:" + "/blog/view?seq=" + blog.getSeq();
	}

	/** 블로그 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/blog/delete.do", method = RequestMethod.POST)
	public String blogDoDelete(Model model, int seq) throws SQLException, JsonProcessingException {
		boolean result = blogService.delete(seq);
		return new JSONObject().put("result", result).toString();
	}
}
