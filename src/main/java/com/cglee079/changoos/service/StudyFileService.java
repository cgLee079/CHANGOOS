package com.cglee079.changoos.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.dao.StudyFileDao;
import com.cglee079.changoos.model.StudyFileVo;
import com.cglee079.changoos.util.FileUtils;
import com.cglee079.changoos.util.TimeStamper;

@Service
public class StudyFileService {
	@Autowired
	StudyFileDao studyFileDao;
	
	@Value("#{servletContext.getRealPath('/')}")
    private String realPath;
	
	public boolean insert(StudyFileVo studyFile) {
		return studyFileDao.insert(studyFile);
	}

	public List<StudyFileVo> list(int seq) {
		return studyFileDao.list(seq);
	}

	public StudyFileVo get(String pathNm) {
		return studyFileDao.getFile(pathNm);
	}

	public StudyFileVo get(int seq) {
		return studyFileDao.getFile(seq);
	}

	public boolean delete(int seq) {
		return studyFileDao.delete(seq);
	}
	
	/** 여러 파일 저장 */
	public void saveFiles(int seq, List<MultipartFile> files) throws IllegalStateException, IOException {
		File file = null;
		MultipartFile multipartFile = null;
		StudyFileVo studyFile = null;
		String realNm = null;
		String pathNm = null;
		long size = -1;
		int length = files.size();
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			realNm 	= FileUtils.sanitizeFilename(multipartFile.getOriginalFilename());
			pathNm	= "study" + seq + "_" + TimeStamper.stamp() + "_" + realNm;
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(realPath + Path.STUDY_FILE_PATH, pathNm);
				multipartFile.transferTo(file);
				
				studyFile = new StudyFileVo();
				studyFile.setPathNm(pathNm);
				studyFile.setRealNm(realNm);
				studyFile.setSize(size);
				studyFile.setStudySeq(seq);
				this.insert(studyFile);
			}
		}
	}
	
	/** 한 게시글에 종속된 파일 삭제 **/
	public void deleteFiles(int studySeq) {
		//File 삭제
		List<StudyFileVo> files = this.list(studySeq);
		StudyFileVo file = null;
		int fileLength = files.size();
		for(int i = 0 ;  i < fileLength; i++){
			file = files.get(i);
			FileUtils.delete(realPath + Path.STUDY_FILE_PATH, file.getPathNm());
		}
	}
	
	/** 파일 삭제 **/
	public boolean deleteFile(int fileSeq) {
		StudyFileVo studyFile = this.get(fileSeq);
		if(FileUtils.delete(realPath + Path.STUDY_FILE_PATH ,studyFile.getPathNm())) {
			if(this.delete(fileSeq)){
				return true;
			};
		}
		return false;
	}
	

}
