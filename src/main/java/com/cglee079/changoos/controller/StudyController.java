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

import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.service.StudyService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

@Controller
public class StudyController {
	
	@Autowired
	private StudyService studyService;
	
	/** 공부 리스트로 이동 **/
	@RequestMapping(value = "/studies", method = RequestMethod.GET)
	public String studyList(Model model, @RequestParam Map<String, Object> params) throws SQLException, JsonProcessingException{
		params.put("enabled", true);
		
		List<String> categories = studyService.getCategories();
		int totalCount = studyService.count(params);
		
		model.addAttribute("totalCount", totalCount);
		model.addAttribute("categories", categories);
		model.addAttribute("category", params.get("category"));
		model.addAttribute("title", params.get("title"));
		
		return "study/study_list";
	}
		
	/** 공부 페이징 **/
	@ResponseBody
	@RequestMapping(value = "/studies/records", method = RequestMethod.GET)
	public String studyListPaging(@RequestParam Map<String, Object> params) throws SQLException, JsonProcessingException{
		params.put("enabled", true);
		
		List<StudyVo> studies = studyService.paging(params);
		
		return new Gson().toJson(studies);
	}
	
	/** 게시글로 이동 **/
	@RequestMapping(value = "/studies/{seq}", method = RequestMethod.GET)
	public String studyView(HttpSession session, Model model, @PathVariable int seq, String category, String title) throws SQLException, JsonProcessingException{
		StudyVo study 		= studyService.doView((Set<Integer>)session.getAttribute("visitStudies"), seq);
		StudyVo beforeStudy = studyService.getBefore(seq, category);
		StudyVo afterStudy 	= studyService.getAfter(seq, category);
		
		model.addAttribute("category", category);
		model.addAttribute("title", title);
		model.addAttribute("study", study);
		model.addAttribute("afterStudy", afterStudy);
		model.addAttribute("beforeStudy", beforeStudy);
		model.addAttribute("files", study.getFiles());
		
		return "study/study_view";
	}
	
	/** 공부 관리 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/studies", method = RequestMethod.GET)
	public String studyManage(Model model) {
		return "study/study_manage";
	}
	
	/** 공부 관리 페이지 리스트, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/studies/records", method = RequestMethod.GET)
	public String studyMangePaging(@RequestParam Map<String, Object> params) {
		//관리자 페이지, JQuery 데이터그리드 페이징 파라미터 변경  page, rows --> offset, limit
		if(params.containsKey("page") && params.containsKey("rows")) {
			int page = Integer.valueOf((String)params.get("page"));
			int rows = Integer.valueOf((String)params.get("rows"));
			int offset = ((page - 1) * rows);
			params.put("offset", offset);
			params.put("limit", rows);	
		}
				
		int totalCount = studyService.count(params);
		
		List<StudyVo> studies = studyService.list(params);
		
		JSONObject resultMap = new JSONObject();
		resultMap.put("rows", new JSONArray(new Gson().toJson(studies)));
		resultMap.put("total", totalCount);

		
		return resultMap.toString();
	}
	
	/** 공부 업로드 페이지로 이동 **/
	@RequestMapping(value = "/studies/post", method = RequestMethod.GET)
	public String studyUpload(Model model)throws SQLException, JsonProcessingException{
		return "study/study_upload";
	}
	
	/** 공부 수정 페이지로 이동 **/
	@RequestMapping(value = "/studies/post/{seq}", method = RequestMethod.GET)
	public String studyModify(Model model, @PathVariable int seq)throws SQLException, JsonProcessingException{
		StudyVo study = studyService.get(seq);
		model.addAttribute("study", study);
		model.addAttribute("files", study.getFiles());
		model.addAttribute("images", study.getImages());
		return "study/study_upload";
	}
	
	 
	/** 공부 업로드  **/
	@RequestMapping(value = "/studies/post", method = RequestMethod.POST)
	public String studyDoUpload(HttpSession session, StudyVo study, String imageValues, String fileValues) throws SQLException, IllegalStateException, IOException{
		int seq = studyService.insert(study, (String)session.getAttribute("tempDirId"), imageValues, fileValues);
		return "redirect:" + "/studies/" + seq;
	}
	
	/** 공부 수정 **/
	@RequestMapping(value = "/studies/post/{seq}", method = RequestMethod.PUT)
	public String studyDoModify(HttpSession session, StudyVo study, String imageValues, String fileValues) throws SQLException, IllegalStateException, IOException{
		boolean result = studyService.update(study, (String)session.getAttribute("tempDirId"), imageValues, fileValues);
		return "redirect:" + "/studies/" + study.getSeq();
	}
	
	/** 공부 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/studies/post/{seq}", method = RequestMethod.DELETE)
	public String studyDoDelete(@PathVariable int seq) throws SQLException, JsonProcessingException{
		boolean result = studyService.delete(seq);
		return new JSONObject().put("result", result).toString();
	}
}
