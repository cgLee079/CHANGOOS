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
import com.cglee079.portfolio.service.ProjectService;
import com.cglee079.portfolio.util.ImageManager;
import com.cglee079.portfolio.util.TimeStamper;

@Controller
public class ProjectController {
	final static String FILE_PATH 		= "/uploaded/projects/files/";
	final static String SNAPSHT_PATH	= "/uploaded/projects/snapshts/";
	final static String CONTENTS_PATH	= "/uploaded/projects/contents/";
	
	@Autowired
	private ProjectService projectService;

	@Autowired 
	private PComtService pcomtService;
	
	@RequestMapping(value = "/project")
	public String projectList(Model model) {
		List<ProjectVo> projects = projectService.list();
		model.addAttribute("projects", projects);
		return "project/project_list";
	}
	
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
		
		int comtCnt = pcomtService.count(seq);
		model.addAttribute("comtCnt", comtCnt);
		
		List<FileVo> files = projectService.getFiles(seq);
		model.addAttribute("files", files);
		
		return "project/project_view";
	}
	
	@RequestMapping("/project/download.do")
	public void  download(HttpSession session, HttpServletResponse response, String filename) throws IOException{
		String rootPath = session.getServletContext().getRealPath("");
		FileVo projectFile = projectService.getFile(filename);
		
		File file = new File(rootPath + FILE_PATH, projectFile.getPathNm());
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
	
	@RequestMapping(value = "/admin/project/manage")
	public String projectManage(Model model) {
		List<ProjectVo> projects = projectService.list();
		model.addAttribute("projects", projects);
		return "project/project_manage";
	}
	
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
		
		//File 삭제
		List<FileVo> files = projectService.getFiles(seq);
		FileVo file = null;
		int fileLength = files.size();
		for(int i = 0 ;  i < fileLength; i++){
			file = files.get(i);
			existFile = new File(rootPath + FILE_PATH, file.getPathNm());
			if(existFile.exists()){
				existFile.delete();
			}
		}
				
		projectService.delete(seq);
		return "redirect:" + "/admin/project/manage";
	}
	
	@RequestMapping(value = "/admin/project/upload", params = "!seq")
	public String projectUpload() {
		return "project/project_upload";
	}
	
	@RequestMapping(value = "/admin/project/upload", params = "seq")
	public String projectModify(Model model, int seq) {
		ProjectVo project = projectService.get(seq);
		if(project.getContents() != null){
			project.setContents(project.getContents().replace("&", "&amp;"));
		}
		
		model.addAttribute("project", project);
		
		List<FileVo> files = projectService.getFiles(seq);
		model.addAttribute("files", files);
		
		return "project/project_upload";
	}
	
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
		
		//파일 업로드
		File file = null;
		MultipartFile multipartFile = null;
		FileVo projectFile = null;
		String realNm = null;
		String pathNm = null;
		long size = -1;
		
		int length = files.size();
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			realNm 	= multipartFile.getOriginalFilename();
			pathNm	= "project" + seq + "_" + TimeStamper.stamp() + "_" + realNm;
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(rootPath + FILE_PATH, pathNm);
				multipartFile.transferTo(file);
				
				projectFile = new FileVo();
				projectFile.setPathNm(pathNm);
				projectFile.setRealNm(realNm);
				projectFile.setSize(size);
				projectFile.setBoardSeq(seq);
				projectService.saveFile(projectFile);
			}
		}
		
		return "redirect:" + "/admin/project/manage";
	}
	
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
		
		File file = null;
		MultipartFile multipartFile = null;
		FileVo projectFile = null;
		String realNm = null;
		String pathNm = null;
		long size = -1;
		int length = files.size();
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			realNm 	= multipartFile.getOriginalFilename();
			pathNm	= "project" + seq + "_" + TimeStamper.stamp() + "_" + realNm;
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(rootPath + FILE_PATH, pathNm);
				multipartFile.transferTo(file);
				
				projectFile = new FileVo();
				projectFile.setPathNm(pathNm);
				projectFile.setRealNm(realNm);
				projectFile.setSize(size);
				projectFile.setBoardSeq(seq);
				projectService.saveFile(projectFile);
			}
		}
		
		return "redirect:" + "/admin/project/manage";
	}
	
	@ResponseBody
	@RequestMapping(value = "/admin/project/deleteFile.do")
	public String deleteFile(HttpSession session, int seq){
		JSONObject data = new JSONObject();
		data.put("result", false);
		
		String rootPath = session.getServletContext().getRealPath("");
		
		FileVo projectFile = projectService.getFile(seq);
		File file = new File(rootPath + FILE_PATH, projectFile.getPathNm());
		if(file.exists()){
			if(file.delete()){
				if(projectService.deleteFile(seq)){
					data.put("result", true);
				};
			};
		}
		
		return data.toString();
	}
	
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
