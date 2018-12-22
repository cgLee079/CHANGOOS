package com.cglee079.changoos.controller;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.model.StudyFileVo;
import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.service.StudyService;
import com.cglee079.changoos.util.MyFileUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

@Controller
public class StudyController {
	
	@Autowired
	private StudyService studyService;
	
	/** 공부 리스트로 이동 **/
	@RequestMapping("/study")
	public String studyList(Model model, @RequestParam Map<String, Object> params) throws SQLException, JsonProcessingException{
		List<String> categories = studyService.getCategories();
		int count = studyService.count(params);
		
		model.addAttribute("count", count);
		model.addAttribute("categories", categories);
		return "study/study_list";
	}
		
	/** 공부 페이징 **/
	@ResponseBody
	@RequestMapping("/study/paging")
	public String studyListPaging(@RequestParam Map<String, Object> params) throws SQLException, JsonProcessingException{
		List<StudyVo> studys = studyService.paging(params);
		int count = studyService.count(params);
		
		String data = new Gson().toJson(studys);
		JSONArray dataJson = new JSONArray(data);
			
		JSONObject result = new JSONObject();
		result.put("count", count);
		result.put("data", dataJson);
		
		return result.toString();
	}
	
	/** 게시글로 이동 **/
	@RequestMapping("/study/view")
	public String studyView(HttpSession session, Model model, int seq, String category, Integer page) throws SQLException, JsonProcessingException{
		StudyVo study 		= studyService.doView((List<Integer>)session.getAttribute("visitStudies"), seq);
		StudyVo beforeStudy = studyService.getBefore(seq, category);
		StudyVo afterStudy 	= studyService.getAfter(seq, category);
		
		model.addAttribute("category", category);
		model.addAttribute("page", page);
		model.addAttribute("study", study);
		model.addAttribute("afterStudy", afterStudy);
		model.addAttribute("beforeStudy", beforeStudy);
		model.addAttribute("files", study.getFiles());
		
		return "study/study_view";
	}
	
	/** 파일 다운로드 **/
	@RequestMapping("/study/download.do")
	public void  download(HttpSession session, HttpServletRequest request, HttpServletResponse response, String filename) throws IOException{
		String rootPath = session.getServletContext().getRealPath("");
		StudyFileVo studyFile = studyService.getFile(filename);
		
		File file = new File(rootPath + Path.STUDY_FILE_PATH, studyFile.getPathNm());
		byte fileByte[] = FileUtils.readFileToByteArray(file);
		
		if(file.exists()){
			response.setContentType("application/octet-stream");
		    response.setContentLength(fileByte.length);
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + MyFileUtils.encodeFilename(request, studyFile.getRealNm()) + "\";");
		    response.setHeader("Content-Transfer-Encoding", "binary");
		    response.getOutputStream().write(fileByte);
		     
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		}
	}
	
	/** 공부 관리 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/study")
	public String studyManage(Model model) {
		return "study/study_manage";
	}
	
	/** 공부 관리 페이지 리스트, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/study/paging")
	public String studyMangePaging(@RequestParam Map<String, Object> map) {
		List<StudyVo> studies = studyService.list(map);
		Gson gson = new Gson();
		return gson.toJson(studies).toString();
	}
	
	/** 공부 업로드 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/study/upload", params = "!seq")
	public String studyUpload(Model model)throws SQLException, JsonProcessingException{
		return "study/study_upload";
	}
	
	/** 공부 수정 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/study/upload", params = "seq")
	public String studyModify(Model model, int seq)throws SQLException, JsonProcessingException{
		StudyVo study = studyService.get(seq);
		model.addAttribute("study", study);
		model.addAttribute("files", study.getFiles());
		return "study/study_upload";
	}
	
	 
	/** 공부 업로드  **/
	@RequestMapping(value = "/mgnt/study/upload.do", method = RequestMethod.POST, params = "!seq")
	public String studyDoUpload(HttpSession session, Model model, StudyVo study, @RequestParam("file")List<MultipartFile> files) throws SQLException, IllegalStateException, IOException{
		int seq = studyService.insert(study, files);
		return "redirect:" + "/study/view?seq=" + seq;
	}
	
	/** 공부 수정 **/
	@RequestMapping(value = "/mgnt/study/upload.do", method = RequestMethod.POST, params = "seq")
	public String studyDoModify(HttpSession session, Model model, StudyVo study, @RequestParam("file")List<MultipartFile> files) throws SQLException, IllegalStateException, IOException{
		boolean result = studyService.update(study, files);
		return "redirect:" + "/study/view?seq=" + study.getSeq();
	}
	
	/** 공부 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/study/delete.do", method = RequestMethod.POST)
	public String studyDoDelete(HttpSession session, Model model, int seq) throws SQLException, JsonProcessingException{
		boolean result = studyService.delete(seq);
		return new JSONObject().put("result", result).toString();
	}
	
	/** 공부 파일 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/study/file/delete.do", method = RequestMethod.POST)
	public String studyDoDeleteFile(int seq){
		boolean result = studyService.deleteFile(seq);
		return new JSONObject().put("result", result).toString();
	}
	
}
