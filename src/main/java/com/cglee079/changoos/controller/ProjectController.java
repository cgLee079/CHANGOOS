package com.cglee079.changoos.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
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
import com.cglee079.changoos.model.ProjectFileVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.service.ProjectService;
import com.cglee079.changoos.util.MyFilenameUtils;
import com.google.gson.Gson;

@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;

	/** 프로젝트 리스트 **/
	@RequestMapping(value = "/project")
	public String projectList(Model model) {
		List<ProjectVo> projects = projectService.list(null);
		
		model.addAttribute("projects", projects);
		return "project/project_list";
	}

	/** 프로젝트 보기 **/
	@RequestMapping(value = "/project/view")
	public String projectView(HttpSession session, Model model, int seq) {
		ProjectVo project = projectService.doView((List<Integer>) session.getAttribute("visitProjects"), seq);
		ProjectVo beforeProject = projectService.getBefore(project.getSeq());
		ProjectVo afterProject = projectService.getAfter(project.getSeq());

		model.addAttribute("project", project);
		model.addAttribute("beforeProject", beforeProject);
		model.addAttribute("afterProject", afterProject);
		model.addAttribute("files", project.getFiles());

		return "project/project_view";
	}

	/** 프로젝트 관리자 페이지 이동 **/
	@RequestMapping(value = "/mgnt/project")
	public String projectManage(Model model) {
		return "project/project_manage";
	}

	/** 프로젝트 관리자 페이지 리스트, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/project/list")
	public String projectManagePaging(@RequestParam Map<String, Object> map, Model model) {
		List<ProjectVo> projects = projectService.list(map);
		Gson gson = new Gson();
		return gson.toJson(projects).toString();
	}

	/** 프로젝트 업로드 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/project/upload", params = "!seq")
	public String projectUpload() {
		return "project/project_upload";
	}

	/** 프로젝트 수정 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/project/upload", params = "seq")
	public String projectModify(Model model, int seq) {
		ProjectVo project = projectService.get(seq);

		model.addAttribute("project", project);
		model.addAttribute("files", project.getFiles());
		model.addAttribute("images", project.getImages());
		
		return "project/project_upload";
	}

	/** 프로젝트 업로드 **/
	@RequestMapping(value = "/mgnt/project/upload.do", method = RequestMethod.POST, params = "!seq")
	public String projectDoUpload(HttpServletRequest request, ProjectVo project, MultipartFile snapshtFile, String imageValues,
			@RequestParam("file") List<MultipartFile> files) throws IllegalStateException, IOException {
		int seq = projectService.insert(project, snapshtFile, imageValues, files);
		return "redirect:" + "/project/view?seq=" + seq;
	}

	/** 프로젝트 수정 **/
	@RequestMapping(value = "/mgnt/project/upload.do", method = RequestMethod.POST, params = "seq")
	public String projectDoModify(HttpServletRequest request, ProjectVo project, MultipartFile snapshtFile, String imageValues,
			@RequestParam("file") List<MultipartFile> files) throws IllegalStateException, IOException {
		projectService.update(project, snapshtFile, imageValues, files);
		return "redirect:" + "/project/view?seq=" + project.getSeq();
	}

	/** 프로젝트 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/project/delete.do", method = RequestMethod.POST)
	public String projectDoDelete(HttpSession session, int seq) {
		boolean result = projectService.delete(seq);
		return new JSONObject().put("result", result).toString();
	}

	/** 프로젝트 파일 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/project/file/delete.do", method = RequestMethod.POST)
	public String projectDoDeleteFile(HttpSession session, int seq) {
		boolean result = projectService.deleteFile(seq);
		return new JSONObject().put("result", result).toString();
	}
}
