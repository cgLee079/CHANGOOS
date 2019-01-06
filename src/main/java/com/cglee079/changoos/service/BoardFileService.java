package com.cglee079.changoos.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.BoardFileDao;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.util.FileHandler;
import com.cglee079.changoos.util.MyFilenameUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BoardFileService {
	@Autowired private BoardFileDao boardFileDao;
	@Autowired private FileHandler fileHandler;
	
	@Value("#{servletContext.getRealPath('/')}") private String realPath;
	@Value("#{location['temp.file.dir.url']}") 	private String tempDir;
	
	@Value("#{constant['file.status.id.new']}")		private String statusNew;
	@Value("#{constant['file.status.id.unnew']}") 	private String statusUnnew;
	@Value("#{constant['file.status.id.remove']}") 	private String statusRemove;
	
	public String saveFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		String filename = MyFilenameUtils.sanitizeRealFilename(multipartFile.getOriginalFilename());
		String pathname = MyFilenameUtils.getRandomFilename(MyFilenameUtils.getExt(filename));

		fileHandler.save(realPath + tempDir + pathname, multipartFile);

		return pathname;
	}
	
	public void insertFiles(String TB, String dir, int boardSeq, String fileValues) throws JsonParseException, JsonMappingException, IOException{
		List<BoardFileVo> files = new ObjectMapper().readValue(fileValues, new TypeReference<List<BoardFileVo>>(){});
		BoardFileVo file;
		
		for (int i = 0; i < files.size(); i++) {
			file = files.get(i);
			file.setBoardSeq(boardSeq);
			
			String pathname = file.getPathname();
			String status = file.getStatus();
			
			if(status.equals(statusNew)) { //새롭게 추가된 파일
				if(boardFileDao.insert(TB, file)) {
					//임시폴더에서 본 폴더로 이동
					File existFile  = new File(realPath + tempDir, pathname);
					File newFile	= new File(realPath + dir, pathname);
					fileHandler.move(existFile, newFile);
				}
			}
			else if (status.equals(statusUnnew)) {
				fileHandler.delete(realPath + dir, pathname);
			}
			else if (status.equals(statusRemove)) { //기존에 있던 파일 중, 삭제된 파일
				if(boardFileDao.delete(TB, file.getSeq())) {
					fileHandler.delete(realPath + dir, pathname);
				}
			}
			
		}
	}

}
