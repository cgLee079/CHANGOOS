package com.cglee079.changoos.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.service.ProjectService;
import com.google.gson.Gson;

@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;

	/** 프로젝트 리스트 **/
	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String projectList(Model model) {
		Map<String, Object> params = new HashMap<>();
		params.put("enabled", true);
		
		List<ProjectVo> projects = projectService.list(params);
		model.addAttribute("projects", projects);
		return "project/project_list";
	}

	/** 프로젝트 보기 **/
	@RequestMapping(value = "/projects/{seq}", method = RequestMethod.GET)
	public String projectView(HttpSession session, Model model, @PathVariable int seq) {
		ProjectVo project = projectService.doView((Set<Integer>) session.getAttribute("visitProjects"), seq);
		ProjectVo beforeProject = projectService.getBefore(project.getSeq());
		ProjectVo afterProject = projectService.getAfter(project.getSeq());

		model.addAttribute("project", project);
		model.addAttribute("beforeProject", beforeProject);
		model.addAttribute("afterProject", afterProject);
		model.addAttribute("files", project.getFiles());

		return "project/project_view";
	}

	/** 프로젝트 관리자 페이지 이동 **/
	@RequestMapping(value = "/mgnt/projects", method = RequestMethod.GET)
	public String projectManage(Model model) {
		return "project/project_manage";
	}

	/** 프로젝트 관리자 페이지 리스트, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/projects/records", method = RequestMethod.GET)
	public String projectManagePaging(@RequestParam Map<String, Object> map, Model model) {
		List<ProjectVo> projects = projectService.list(map);
		return new Gson().toJson(projects).toString();
	}

	/** 프로젝트 업로드 페이지로 이동 **/
	@RequestMapping(value = "/projects/post", method = RequestMethod.GET)
	public String projectUpload() {
		return "project/project_upload";
	}

	/** 프로젝트 수정 페이지로 이동 **/
	@RequestMapping(value = "/projects/post/{seq}", method = RequestMethod.GET)
	public String projectModify(Model model, @PathVariable int seq) {
		ProjectVo project = projectService.get(seq);

		model.addAttribute("project", project);
		model.addAttribute("files", project.getFiles());
		model.addAttribute("images", project.getImages());
		
		return "project/project_upload";
	}

	/** 프로젝트 업로드 **/
	@RequestMapping(value = "/projects/post", method = RequestMethod.POST)
	public String projectDoUpload(HttpSession session, ProjectVo project, String imageValues, String fileValues) throws IllegalStateException, IOException {
		int seq = projectService.insert(project, (String)session.getAttribute("tempDirId"), imageValues, fileValues);
		return "redirect:" + "/projects/" + seq;
	}

	@ResponseBody
	@RequestMapping(value = "/projects/post/thumbnail", method = RequestMethod.POST )
	public String projectDoThumbUpload(HttpSession session, MultipartFile thumbnailFile) throws SQLException, IllegalStateException, IOException {
		String pathname = projectService.saveThumbnail(thumbnailFile, (String)session.getAttribute("tempDirId"));
		
		JSONObject result = new JSONObject();
		result.put("pathname", pathname);
		
		return result.toString();
	}
	
	/** 프로젝트 수정 **/
	@RequestMapping(value = "/projects/post/{seq}", method = RequestMethod.PUT)
	public String projectDoModify(HttpSession session, ProjectVo project, String imageValues, String fileValues) throws IllegalStateException, IOException {
		projectService.update(project, (String)session.getAttribute("tempDirId"), imageValues, fileValues);
		return "redirect:" + "/projects/" + project.getSeq();
	}

	/** 프로젝트 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/projects/post/{seq}", method = RequestMethod.DELETE)
	public String projectDoDelete(@PathVariable int seq) {
		boolean result = projectService.delete(seq);
		return new JSONObject().put("result", result).toString();
	}
}
