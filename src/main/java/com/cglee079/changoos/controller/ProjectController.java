package com.cglee079.changoos.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
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
import com.cglee079.changoos.service.CommonService;
import com.cglee079.changoos.service.ProjectFileService;
import com.cglee079.changoos.service.ProjectService;
import com.google.gson.Gson;

@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ProjectFileService projectFileService;

	/** 프로젝트 리스트 **/
	@RequestMapping(value = "/project")
	public String projectList(Model model) {
		List<ProjectVo> projects = projectService.list(null);
		model.addAttribute("projects", projects);
		return "project/project_list";
	}
	
	/** 프로젝트 보기 **/
	@RequestMapping(value = "/project/view")
	public String projectView(HttpSession session, Model model, int seq){
		ProjectVo project = projectService.doView((List<Integer>)session.getAttribute("visitProjects"), seq);
		ProjectVo beforeProject = projectService.getBefore(project.getSeq());
		ProjectVo afterProject = projectService.getAfter(project.getSeq());
		
		model.addAttribute("project", project);
		model.addAttribute("beforeProject", beforeProject);
		model.addAttribute("afterProject", afterProject);
		
		List<ProjectFileVo> files = projectFileService.list(seq);
		model.addAttribute("files", files);
		
		return "project/project_view";
	}
	
	/** 프로젝트 파일 다운로드 **/
	@RequestMapping("/project/download.do")
	public void  download(HttpSession session, HttpServletResponse response, String filename) throws IOException{
		String realPath = session.getServletContext().getRealPath("");
		ProjectFileVo projectFile = projectFileService.get(filename);
		
		File file = new File(realPath + Path.PROJECT_FILE_PATH, projectFile.getPathNm());
		byte fileByte[] = FileUtils.readFileToByteArray(file);
		
		if(file.exists()){
			response.setContentType("application/octet-stream");
		    response.setContentLength(fileByte.length);
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(projectFile.getRealNm(),"UTF-8")+"\";");
		    response.setHeader("Content-Transfer-Encoding", "binary");
		    response.getOutputStream().write(fileByte);
		     
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		}
	}
	
	/** 프로젝트 관리자 페이지 이동 **/
	@RequestMapping(value = "/mgnt/project")
	public String projectManage(Model model) {
		return "project/project_manage";
	}
	
	/** 프로젝트 관리자 페이지 리스트, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/project/list.do")
	public String DoProjectManageList(@RequestParam Map<String,Object> map) {
		List<ProjectVo> projects = projectService.list(map);
		Gson gson = new Gson();
		return gson.toJson(projects).toString();
	}
	
	
	/** 프로젝트 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/project/delete.do")
	public String projectDelete(HttpSession session, int seq) {
		ProjectVo project = projectService.get(seq);
		List<ProjectFileVo> files = projectFileService.list(seq);
		
		boolean result = projectService.delete(seq);
		if(result) {
			projectService.removeSnapshtFile(project);
			commonService.removeContentImage(project.getContents());
			projectFileService.deleteFiles(files);
		}
		return new JSONObject().put("result", result).toString();
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
		
		if(project.getContents() != null){
			String contents = commonService.copyToTempPath(project.getContents(), Path.PROJECT_CONTENTS_PATH);
			project.setContents(contents.replace("&", "&amp;"));
		}
		
		model.addAttribute("project", project);
		
		List<ProjectFileVo> files = projectFileService.list(seq);
		model.addAttribute("files", files);
		
		return "project/project_upload";
	}
	
	/** 프로젝트 업로드 **/
	@RequestMapping(value = "/mgnt/project/upload.do", method = RequestMethod.POST, params = "!seq")
	public String projectDoUpload(HttpServletRequest request, ProjectVo project, MultipartFile snapshtFile, @RequestParam("file")List<MultipartFile> files) throws IllegalStateException, IOException {
		String snapshtPath = projectService.saveSnapsht(project, snapshtFile);
		project.setSnapsht(snapshtPath);
		
		String contents = commonService.moveToSavePath(project.getContents(), Path.PROJECT_CONTENTS_PATH);
		project.setContents(contents);
		
		int seq = projectService.insert(project);
		
		//파일 저장
		projectFileService.saveFiles(seq, files);
		
		return "redirect:" + "/mgnt/project";
	}
	
	/** 프로젝트 수정 **/
	@RequestMapping(value = "/mgnt/project/upload.do", method = RequestMethod.POST, params = "seq")
	public String projectDoModify(HttpServletRequest request, ProjectVo project, MultipartFile snapshtFile, @RequestParam("file")List<MultipartFile> files) throws IllegalStateException, IOException{
		String snapshtPath = projectService.saveSnapsht(project, snapshtFile);
		project.setSnapsht(snapshtPath);
		
		String contents = commonService.moveToSavePath(project.getContents(), Path.PROJECT_CONTENTS_PATH);
		project.setContents(contents);
		
		projectService.update(project);
		
		//파일 저장
		projectFileService.saveFiles(project.getSeq(), files);
		
		return "redirect:" + "/mgnt/project";
	}
	
	/** 프로젝트 파일 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/project/deleteFile.do")
	public String deleteFile(HttpSession session, int seq){
		boolean result = false;
		result = projectFileService.deleteFile(seq);	
		
		return new JSONObject().put("result", result).toString();
	}
}
