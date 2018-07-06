package com.cglee079.changoos.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.dao.ProjectFileDao;
import com.cglee079.changoos.model.ProjectFileVo;
import com.cglee079.changoos.util.FileUtils;
import com.cglee079.changoos.util.TimeStamper;

@Service
public class ProjectFileService {
	@Autowired
	ProjectFileDao projectFileDao;
	
	@Value("#{servletContext.getRealPath('/')}")
    private String realPath;
	
	public boolean insert(ProjectFileVo projectFile) {
		return projectFileDao.insert(projectFile);
	}
	
	public List<ProjectFileVo> list(int seq) {
		return projectFileDao.list(seq);
	}

	public ProjectFileVo get(String pathNm) {
		return projectFileDao.get(pathNm);
	}
	
	public ProjectFileVo get(int seq) {
		return projectFileDao.get(seq);
	}

	public boolean delete(int seq) {
		return projectFileDao.delete(seq);
	}
	
	/** 파일 삭제 **/
	public boolean deleteFile(int fileSeq) {
		ProjectFileVo projectFile = this.get(fileSeq);
		if(FileUtils.delete(realPath + Path.PROJECT_FILE_PATH, projectFile.getPathNm())){
			if(this.delete(fileSeq)){
				return true;
			};
		}
		return false;
	}
	
	/** 한 프로젝트엔 종속된 파일 삭제 */
	public void deleteFiles(int projectSeq) {
		List<ProjectFileVo> files = this.list(projectSeq);
		ProjectFileVo file = null;
		int fileLength = files.size();
		for(int i = 0 ;  i < fileLength; i++){
			file = files.get(i);
			FileUtils.delete(realPath + Path.PROJECT_FILE_PATH, file.getPathNm());
		}
	}
	
	/** 여러 파일 저장 **/
	public void saveFiles(int projectSeq, List<MultipartFile> files) throws IllegalStateException, IOException {
		MultipartFile multipartFile = null;
		ProjectFileVo projectFile = null;
		File file = null;
		String realNm = null;
		String pathNm = null;
		long size = -1;
		int length = files.size();
		
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			realNm 	= FileUtils.sanitizeFilename(multipartFile.getOriginalFilename());
			pathNm	= "project" + projectSeq + "_" + TimeStamper.stamp() + "_" + realNm;
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(realPath + Path.PROJECT_FILE_PATH, pathNm);
				multipartFile.transferTo(file);
				
				projectFile = new ProjectFileVo();
				projectFile.setPathNm(pathNm);
				projectFile.setRealNm(realNm);
				projectFile.setSize(size);
				projectFile.setProjectSeq(projectSeq);
				this.insert(projectFile);
			}
		}
	}
}
