package com.cglee079.changoos.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.BoardFileDao;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.util.TimeStamper;

@Service
public class BoardFileService {
	public static final String FILE_PATH 		= "/uploaded/boards/files/";
	
	@Autowired
	BoardFileDao boardFileDao;
	
	public boolean insert(BoardFileVo boardFile) {
		return boardFileDao.insert(boardFile);
	}

	public List<BoardFileVo> list(int seq) {
		return boardFileDao.list(seq);
	}

	public BoardFileVo get(String pathNm) {
		return boardFileDao.getFile(pathNm);
	}

	public BoardFileVo get(int seq) {
		return boardFileDao.getFile(seq);
	}

	public boolean delete(int seq) {
		return boardFileDao.delete(seq);
	}
	
	/** 여러 파일 저장 */
	public void saveFiles(String rootPath, int seq, List<MultipartFile> files) throws IllegalStateException, IOException {
		File file = null;
		MultipartFile multipartFile = null;
		BoardFileVo boardFile = null;
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
				
				boardFile = new BoardFileVo();
				boardFile.setPathNm(pathNm);
				boardFile.setRealNm(realNm);
				boardFile.setSize(size);
				boardFile.setBoardSeq(seq);
				this.insert(boardFile);
			}
		}
	}
	
	/** 한 게시글에 종속된 파일 삭제 **/
	public void deleteFiles(String rootPath, int boardSeq) {
		//File 삭제
		File existFile = null;
		List<BoardFileVo> files = this.list(boardSeq);
		BoardFileVo file = null;
		int fileLength = files.size();
		for(int i = 0 ;  i < fileLength; i++){
			file = files.get(i);
			existFile = new File(rootPath + FILE_PATH, file.getPathNm());
			if(existFile.exists()){
				existFile.delete();
			}
		}
	}
	
	/** 파일 삭제 **/
	public boolean deleteFile(String rootPath, int fileSeq) {
		BoardFileVo boardFile = this.get(fileSeq);
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
