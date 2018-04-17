package com.cglee079.portfolio.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.imageio.ImageIO;
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

import com.cglee079.portfolio.model.FileVo;
import com.cglee079.portfolio.model.ProjectVo;
import com.cglee079.portfolio.service.PComtService;
import com.cglee079.portfolio.service.ProjectFileService;
import com.cglee079.portfolio.service.ProjectService;
import com.cglee079.portfolio.util.ImageManager;
import com.cglee079.portfolio.util.TimeStamper;

@Controller
public class ProjectController {
	final static String SNAPSHT_PATH	= "/uploaded/projects/snapshts/";
	final static String CONTENTS_PATH	= "/uploaded/projects/contents/";
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private ProjectFileService projectFileService;

	/** 프로젝트 리스트 **/
	@RequestMapping(value = "/project")
	public String projectList(Model model) {
		List<ProjectVo> projects = projectService.list();
		model.addAttribute("projects", projects);
		return "project/project_list";
	}
	
	/** 프로젝트 보기 **/
	@RequestMapping(value = "/project/view")
	public String projectView(Model model, int seq){
		ProjectVo project = projectService.get(seq);
		ProjectVo beforeProject = projectService.getBefore(project.getSort());
		ProjectVo afterProject = projectService.getAfter(project.getSort());
		
		int hits = project.getHits();
		project.setHits(hits + 1);
		projectService.update(project);
				
		model.addAttribute("project", project);
		model.addAttribute("beforeProject", beforeProject);
		model.addAttribute("afterProject", afterProject);
		
		List<FileVo> files = projectFileService.list(seq);
		model.addAttribute("files", files);
		
		return "project/project_view";
	}
	
	/** 프로젝트 파일 다운로드 **/
	@RequestMapping("/project/download.do")
	public void  download(HttpSession session, HttpServletResponse response, String filename) throws IOException{
		String rootPath = session.getServletContext().getRealPath("");
		FileVo projectFile = projectFileService.get(filename);
		
		File file = new File(rootPath + ProjectFileService.FILE_PATH, projectFile.getPathNm());
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
	@RequestMapping(value = "/admin/project/manage")
	public String projectManage(Model model) {
		List<ProjectVo> projects = projectService.list();
		model.addAttribute("projects", projects);
		return "project/project_manage";
	}
	
	/** 프로젝트 삭제 **/
	@RequestMapping(value = "/admin/project/delete.do")
	public String projectDelete(HttpSession session, int seq) {
		String rootPath = session.getServletContext().getRealPath("");
		
		ProjectVo project = projectService.get(seq);
		File existFile = null;
		
		existFile = new File (rootPath + project.getSnapsht());
		if(existFile.exists()){
			existFile.delete();
		}
		
		List<String> imgPaths = projectService.getContentImgPath(seq, CONTENTS_PATH);
		int imgPathsLength = imgPaths.size();
		existFile = null;
		
		for (int i = 0; i < imgPathsLength; i++){
			existFile = new File (rootPath + imgPaths.get(i));
			if(existFile.exists()){
				existFile.delete();
			}
		}
		
		//프로젝트 파일 삭제
		projectFileService.deleteFiles(rootPath, seq);
		
		projectService.delete(seq);
		return "redirect:" + "/admin/project/manage";
	}
	
	/** 프로젝트 업로드 페이지로 이동 **/
	@RequestMapping(value = "/admin/project/upload", params = "!seq")
	public String projectUpload() {
		return "project/project_upload";
	}
	
	/** 프로젝트 수정 페이지로 이동 **/
	@RequestMapping(value = "/admin/project/upload", params = "seq")
	public String projectModify(Model model, int seq) {
		ProjectVo project = projectService.get(seq);
		if(project.getContents() != null){
			project.setContents(project.getContents().replace("&", "&amp;"));
		}
		
		model.addAttribute("project", project);
		
		List<FileVo> files = projectFileService.list(seq);
		model.addAttribute("files", files);
		
		return "project/project_upload";
	}
	
	/** 프로젝트 업로드 **/
	@RequestMapping(value = "/admin/project/upload.do", method = RequestMethod.POST, params = "!seq")
	public String projectDoUpload(HttpServletRequest request, ProjectVo project, MultipartFile snapshtFile, @RequestParam("file")List<MultipartFile> files) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String filename	= "snapshot_" + project.getTitle() + "_";
		String imgExt	= null;
		int seq = -1;
		
		if(snapshtFile.getSize() != 0){
			filename += snapshtFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(rootPath + SNAPSHT_PATH + filename);
			snapshtFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
			
			project.setSnapsht(SNAPSHT_PATH + filename);
		} else{
			project.setSnapsht(SNAPSHT_PATH + "default.jpg");
		}
		
		seq = projectService.insert(project);
		
		//파일 저장
		projectFileService.saveFiles(rootPath, seq, files);
		
		return "redirect:" + "/admin/project/manage";
	}
	
	/** 프로젝트 수정 **/
	@RequestMapping(value = "/admin/project/upload.do", method = RequestMethod.POST, params = "seq")
	public String projectDoModify(HttpServletRequest request, ProjectVo project, MultipartFile snapshtFile, @RequestParam("file")List<MultipartFile> files) throws IllegalStateException, IOException{
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String filename	= "snapshot_" + project.getTitle() + "_";
		String imgExt	= null;
		int seq = project.getSeq();
		
		if(snapshtFile.getSize() != 0){
			File existFile = new File (rootPath + project.getSnapsht());
			if(existFile.exists()){
				existFile.delete();
			}
			
			filename += snapshtFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(rootPath + SNAPSHT_PATH, filename);
			snapshtFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
			
			project.setSnapsht(SNAPSHT_PATH + filename);
		} 
		
		projectService.update(project);
		
		//파일 저장
		projectFileService.saveFiles(rootPath, seq, files);
		
		return "redirect:" + "/admin/project/manage";
	}
	
	/** 프로젝트 파일 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/admin/project/deleteFile.do")
	public String deleteFile(HttpSession session, int seq){
		String rootPath = session.getServletContext().getRealPath("");
		
		boolean result = false;
		result = projectFileService.deleteFile(rootPath, seq);	
		
		JSONObject data = new JSONObject();
		data.put("result", result);
		return data.toString();
	}
	
	/** 프로젝츠 CKEditor 사진 업로드 **/
	@RequestMapping(value = "/admin/project/imgUpload.do")
	public String projectImgUpload(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("upload")MultipartFile multiFile, String CKEditorFuncNum) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String filename	= "content_" + TimeStamper.stamp() + "_";
		String imgExt 	= null;
		
		if(multiFile != null){
			filename += multiFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(rootPath + CONTENTS_PATH + filename);
			multiFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
		}
		
		response.setHeader("x-frame-options", "SAMEORIGIN");
		model.addAttribute("path", request.getContextPath() + CONTENTS_PATH + filename);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
		
		return "project/project_imgupload";
	}

}
