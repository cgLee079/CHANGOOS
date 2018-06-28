package com.cglee079.changoos.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.model.BlogFileVo;
import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.model.ProjectFileVo;
import com.cglee079.changoos.service.BlogFileService;
import com.cglee079.changoos.service.BlogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

@Controller
public class BlogController {
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private BlogFileService blogFileService;
	
	/** 블로그 리스트로 이동 **/
	@RequestMapping("/blog")
	public String blogList(Model model) throws SQLException, JsonProcessingException{
		List<String> tags = blogService.getTags();
		model.addAttribute("tags", tags);
		return "blog/blog_list";
	}
		
	/** 블로그 페이징 **/
	@ResponseBody
	@RequestMapping("/blog/blog_paging.do")
	public String doPaging(@RequestParam Map<String, Object> params) throws SQLException, JsonProcessingException{
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
	public String blogView(HttpSession session, Model model, int seq) throws SQLException, JsonProcessingException{
		BlogVo blog	= blogService.doView((List<Integer>)session.getAttribute("visitBlogs"), seq);
		model.addAttribute("blog", blog);
		
		List<BlogFileVo> files = blogFileService.list(seq);
		model.addAttribute("files", files);
		return "blog/blog_view";
	}
	
	/** 파일 다운로드 **/
	@RequestMapping("/blog/download.do")
	public void  download(HttpSession session, HttpServletResponse response, String filename) throws IOException{
		String realPath = session.getServletContext().getRealPath("");
		BlogFileVo blogFile = blogFileService.get(filename);
		
		File file = new File(realPath + BlogFileService.FILE_PATH, blogFile.getPathNm());
		byte fileByte[] = FileUtils.readFileToByteArray(file);
		
		if(file.exists()){
			response.setContentType("application/octet-stream");
		    response.setContentLength(fileByte.length);
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(blogFile.getRealNm(),"UTF-8")+"\";");
		    response.setHeader("Content-Transfer-Encoding", "binary");
		    response.getOutputStream().write(fileByte);
		     
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		}
	}
	
	/** 블로그 관리 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/blog")
	public String photoManage(Model model) {
		return "blog/blog_manage";
	}
	
	/** 블로그 관리 페이지 리스트, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/blog/list.do")
	public String DoBlogManageList(@RequestParam Map<String, Object> params) {
		List<BlogVo> photos = blogService.list(params);
		Gson gson = new Gson();
		return gson.toJson(photos).toString();
	}
	
	/** 블로그 업로드 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/blog/upload", params = "!seq")
	public String blogUpload(Model model)throws SQLException, JsonProcessingException{
		return "blog/blog_upload";
	}
	
	/** 블로그 수정 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/blog/upload", params = "seq")
	public String blogModify(Model model, int seq)throws SQLException, JsonProcessingException{
		BlogVo blog = blogService.get(seq);
		if(blog.getContents() != null){
			blog.setContents(blog.getContents().replace("&", "&amp;"));
		}
		
		model.addAttribute("blog", blog);
		
		List<BlogFileVo> files = blogFileService.list(seq);
		model.addAttribute("files", files);
		
		return "blog/blog_upload";
	}
	
	 
	/** 블로그 업로드  **/
	@RequestMapping(value = "/mgnt/blog/upload.do", params = "!seq")
	public String blogDoUpload(Model model, BlogVo blog, MultipartFile snapshtFile, @RequestParam("file")List<MultipartFile> files) throws SQLException, IllegalStateException, IOException{
		String snapshtPath = blogService.saveSnapsht(blog, snapshtFile);
		blog.setSnapsht(snapshtPath);
		int seq = blogService.insert(blog);
		
		//파일저장
		blogFileService.saveFiles(blog.getSeq(), files);
		
		return "redirect:" + "/blog/view?seq=" + seq;
	}
	
	/** 블로그 수정 **/
	@RequestMapping(value = "/mgnt/blog/upload.do", params = "seq")
	public String blogDoModify(Model model, BlogVo blog, MultipartFile snapshtFile, @RequestParam("file")List<MultipartFile> files) throws SQLException, IllegalStateException, IOException{
		String snapshtPath = blogService.saveSnapsht(blog, snapshtFile);
		blog.setSnapsht(snapshtPath);
		blogService.update(blog);
		
		//파일저장
		blogFileService.saveFiles(blog.getSeq(), files);
		
		return "redirect:" + "/blog/view?seq=" + blog.getSeq();
	}
	
	/** 블로그 삭제 **/
	@ResponseBody
	@RequestMapping("/mgnt/blog/delete.do")
	public String blogDoDelete(Model model, int seq) throws SQLException, JsonProcessingException{
		
		BlogVo blog = blogService.get(seq);
		
		//Content Img 삭제
		blogService.removeSnapshtFile(blog);
		blogService.removeContentImageFile(blog);
		blogFileService.deleteFiles(seq);
		
		boolean result = blogService.delete(seq);
		return new JSONObject().put("result", result).toString();
	}
	
	/** 블로그 파일 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/blog/deleteFile.do")
	public String deleteFile(int seq){
		boolean result = false;
		
		result = blogFileService.deleteFile(seq);
		
		return new JSONObject().put("result", result).toString();
	}
	
	
	/** 블로그 CKEditor 사진 업로드  **/
	@RequestMapping(value = "/mgnt/blog/imgUpload.do")
	public String blogDoImgUpload(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("upload")MultipartFile multiFile, String CKEditorFuncNum) throws IllegalStateException, IOException {
	
		String path = blogService.saveContentImage(multiFile);
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		model.addAttribute("path", request.getContextPath() + path);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
		
		return "blog/blog_imgupload";
	}
	
}
