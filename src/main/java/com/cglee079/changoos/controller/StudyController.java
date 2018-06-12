package com.cglee079.changoos.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
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

import com.cglee079.changoos.model.StudyFileVo;
import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.service.StudyFileService;
import com.cglee079.changoos.service.StudyService;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.TimeStamper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

@Controller
public class StudyController {
	public static final String CONTENTS_PATH	= "/uploaded/studies/contents/";
	
	@Autowired
	private StudyService studyService;
	
	@Autowired
	private StudyFileService studyFileService;
	
	/** 공부 리스트로 이동 **/
	@RequestMapping("/study")
	public String studyList(Model model, @RequestParam Map<String, Object> params) throws SQLException, JsonProcessingException{
		List<String> sects = studyService.getSects();
		int count = studyService.count(params);
		
		model.addAttribute("count", count);
		model.addAttribute("sects", sects);
		return "study/study_list";
	}
		
	/** 공부 페이징 **/
	@ResponseBody
	@RequestMapping("/study/study_paging.do")
	public String doPaging(@RequestParam Map<String, Object> params) throws SQLException, JsonProcessingException{
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
	public String studyView(HttpSession session, Model model, int seq, String section, Integer page) throws SQLException, JsonProcessingException{
		StudyVo study 		= studyService.doView((List<Integer>)session.getAttribute("visitStudies"), seq);
		StudyVo beforeStudy = studyService.getBefore(seq, section);
		StudyVo afterStudy 	= studyService.getAfter(seq, section);
		
		model.addAttribute("section", section);
		model.addAttribute("page", page);
		model.addAttribute("beforeStudy", beforeStudy);
		model.addAttribute("study", study);
		model.addAttribute("afterStudy", afterStudy);
		
		List<StudyFileVo> files = studyFileService.list(seq);
		model.addAttribute("files", files);
		
		return "study/study_view";
	}
	
	/** 파일 다운로드 **/
	@RequestMapping("/study/download.do")
	public void  download(HttpSession session, HttpServletResponse response, String filename) throws IOException{
		String rootPath = session.getServletContext().getRealPath("");
		StudyFileVo studyFile = studyFileService.get(filename);
		
		File file = new File(rootPath + StudyFileService.FILE_PATH, studyFile.getPathNm());
		byte fileByte[] = FileUtils.readFileToByteArray(file);
		
		if(file.exists()){
			response.setContentType("application/octet-stream");
		    response.setContentLength(fileByte.length);
		    response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(studyFile.getRealNm(),"UTF-8")+"\";");
		    response.setHeader("Content-Transfer-Encoding", "binary");
		    response.getOutputStream().write(fileByte);
		     
		    response.getOutputStream().flush();
		    response.getOutputStream().close();
		}
	}
	
	/** 공부 관리 페이지로 이동 **/
	@RequestMapping(value = "/mgnt/study")
	public String photoManage(Model model) {
		return "study/study_manage";
	}
	
	/** 공부 관리 페이지 리스트, Ajax **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/study/list.do")
	public String DoPhotoManageList(@RequestParam Map<String, Object> map) {
		List<StudyVo> photos = studyService.list(map);
		Gson gson = new Gson();
		return gson.toJson(photos).toString();
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
		study.setContents(study.getContents().replace("&", "&amp;"));
		model.addAttribute("study", study);
		
		List<StudyFileVo> files = studyFileService.list(seq);
		model.addAttribute("files", files);
		
		return "study/study_upload";
	}
	
	 
	/** 공부 업로드  **/
	@RequestMapping(value = "/mgnt/study/upload.do", params = "!seq")
	public String studyDoUpload(HttpSession session, Model model, StudyVo study, @RequestParam("file")List<MultipartFile> files) throws SQLException, IllegalStateException, IOException{
		int seq = studyService.insert(study);
		String rootPath = session.getServletContext().getRealPath("");
		
		//파일저장
		studyFileService.saveFiles(rootPath, study.getSeq(), files);
		
		return "redirect:" + "/study/view?seq=" + seq;
	}
	
	/** 공부 수정 **/
	@RequestMapping(value = "/mgnt/study/upload.do", params = "seq")
	public String studyDoModify(HttpSession session, Model model, StudyVo study, @RequestParam("file")List<MultipartFile> files) throws SQLException, IllegalStateException, IOException{
		studyService.update(study);
		String rootPath = session.getServletContext().getRealPath("");
		
		//파일저장
		studyFileService.saveFiles(rootPath, study.getSeq(), files);
		
		return "redirect:" + "/study/view?seq=" + study.getSeq();
	}
	
	/** 공부 삭제 **/
	@ResponseBody
	@RequestMapping("/mgnt/study/delete.do")
	public String studyDoDelete(HttpSession session, Model model, int seq) throws SQLException, JsonProcessingException{
		String rootPath = session.getServletContext().getRealPath("");
		File existFile = null;
		
		//Content Img 삭제
		List<String> imgPaths = studyService.getContentImgPath(seq, CONTENTS_PATH);
		int imgPathsLength = imgPaths.size();
		for (int i = 0; i < imgPathsLength; i++){
			existFile = new File (rootPath + imgPaths.get(i));
			if(existFile.exists()){
				existFile.delete();
			}
		}
		
		studyFileService.deleteFiles(rootPath, seq);
		
		boolean result = studyService.delete(seq);
		return new JSONObject().put("result", result).toString();
	}
	
	/** 공부 파일 삭제 **/
	@ResponseBody
	@RequestMapping(value = "/mgnt/study/deleteFile.do")
	public String deleteFile(HttpSession session, int seq){
		boolean result = false;
		String rootPath = session.getServletContext().getRealPath("");
		
		result = studyFileService.deleteFile(rootPath, seq);
		JSONObject data = new JSONObject();
		data.put("result", result);
		
		return data.toString();
	}
	
	/** 공부 CKEditor 사진 업로드  **/
	@RequestMapping(value = "/mgnt/study/imgUpload.do")
	public String studyDoImgUpload(HttpServletRequest request, HttpServletResponse response, Model model,
			@RequestParam("upload")MultipartFile multiFile, String CKEditorFuncNum) throws IllegalStateException, IOException {
		HttpSession session = request.getSession();
		String rootPath = session.getServletContext().getRealPath("");
		String filename	= "content_" + TimeStamper.stamp() + "_";
		String imgExt 	= null;
		
		if(multiFile != null){
			filename += multiFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(rootPath + CONTENTS_PATH, filename);
			multiFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
		}
		
		response.setHeader("X-Frame-Options", "SAMEORIGIN");
		model.addAttribute("path", request.getContextPath() + CONTENTS_PATH + filename);
		model.addAttribute("CKEditorFuncNum", CKEditorFuncNum);
		
		return "study/study_imgupload";
	}
	
}
