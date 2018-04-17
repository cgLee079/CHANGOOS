package com.cglee079.portfolio.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.portfolio.dao.BoardFileDao;
import com.cglee079.portfolio.model.FileVo;
import com.cglee079.portfolio.util.TimeStamper;

@Service
public class BoardFileService {
	public static final String FILE_PATH 		= "/uploaded/boards/files/";
	
	@Autowired
	BoardFileDao boardFileDao;
	
	public boolean insert(FileVo boardFile) {
		return boardFileDao.insert(boardFile);
	}

	public List<FileVo> list(int seq) {
		return boardFileDao.list(seq);
	}

	public FileVo get(String pathNm) {
		return boardFileDao.getFile(pathNm);
	}

	public FileVo get(int seq) {
		return boardFileDao.getFile(seq);
	}

	public boolean delete(int seq) {
		return boardFileDao.delete(seq);
	}
	
	public void saveFiles(String rootPath, int seq, List<MultipartFile> files) throws IllegalStateException, IOException {
		File file = null;
		MultipartFile multipartFile = null;
		FileVo boardFile = null;
		String realNm = null;
		String pathNm = null;
		long size = -1;
		int length = files.size();
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			realNm 	= multipartFile.getOriginalFilename();
			pathNm	= "board" + seq + "_" + TimeStamper.stamp() + "_" + realNm;
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(rootPath + FILE_PATH, pathNm);
				multipartFile.transferTo(file);
				
				boardFile = new FileVo();
				boardFile.setPathNm(pathNm);
				boardFile.setRealNm(realNm);
				boardFile.setSize(size);
				boardFile.setBoardSeq(seq);
				this.insert(boardFile);
			}
		}
	}
	
	public void deleteFiles(String rootPath, int boardSeq) {
		//File 삭제
		File existFile = null;
		List<FileVo> files = this.list(boardSeq);
		FileVo file = null;
		int fileLength = files.size();
		for(int i = 0 ;  i < fileLength; i++){
			file = files.get(i);
			existFile = new File(rootPath + FILE_PATH, file.getPathNm());
			if(existFile.exists()){
				existFile.delete();
			}
		}
	}
	
	public boolean deleteFile(String rootPath, int fileSeq) {
		FileVo boardFile = this.get(fileSeq);
		File file = new File(rootPath + FILE_PATH, boardFile.getPathNm());
		if(file.exists()){
			if(file.delete()){
				if(this.delete(fileSeq)){
					return true;
				};
			}
		} 
		return false;
	}
	

}
