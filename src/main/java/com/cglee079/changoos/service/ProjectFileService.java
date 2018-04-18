package com.cglee079.changoos.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.ProjectFileDao;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.ProjectFileVo;
import com.cglee079.changoos.util.TimeStamper;

@Service
public class ProjectFileService {
	public static final String FILE_PATH = "/uploaded/projects/files/";
	
	@Autowired
	ProjectFileDao projectFileDao;
	
	public boolean insert(BoardFileVo boardFile) {
		return projectFileDao.insert(boardFile);
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
	public boolean deleteFile(String rootPath, int fileSeq) {
		ProjectFileVo projectFile = this.get(fileSeq);
		File file = new File(rootPath + FILE_PATH, projectFile.getPathNm());
		if(file.exists()){
			if(file.delete()){
				if(this.delete(fileSeq)){
					return true;
				};
			};
		}
		return false;
	}
	
	/** 한 프로젝트엔 종속된 파일 삭제 */
	public void deleteFiles(String rootPath, int projectSeq) {
		File existFile = null;
		
		List<ProjectFileVo> files = this.list(projectSeq);
		ProjectFileVo file = null;
		int fileLength = files.size();
		for(int i = 0 ;  i < fileLength; i++){
			file = files.get(i);
			existFile = new File(rootPath + FILE_PATH, file.getPathNm());
			if(existFile.exists()){
				existFile.delete();
			}
		}
	}
	
	/** 여러 파일 저장 **/
	public void saveFiles(String rootPath, int projectSeq, List<MultipartFile> files) throws IllegalStateException, IOException {
		MultipartFile multipartFile = null;
		BoardFileVo projectFile = null;
		File file = null;
		String realNm = null;
		String pathNm = null;
		long size = -1;
		int length = files.size();
		
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			realNm 	= multipartFile.getOriginalFilename();
			pathNm	= "project" + projectSeq + "_" + TimeStamper.stamp() + "_" + realNm;
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(rootPath + FILE_PATH, pathNm);
				multipartFile.transferTo(file);
				
				projectFile = new BoardFileVo();
				projectFile.setPathNm(pathNm);
				projectFile.setRealNm(realNm);
				projectFile.setSize(size);
				projectFile.setBoardSeq(projectSeq);
				this.insert(projectFile);
			}
		}
	}
}
