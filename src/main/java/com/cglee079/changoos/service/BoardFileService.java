package com.cglee079.changoos.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.config.code.FileStatus;
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
	
	@Value("#{servletContext.getRealPath('/')}")private String realPath;
	@Value("#{location['temp.dir.url']}") 		private String tempDir;
	
	public String saveFile(String tempDirId, MultipartFile multipartFile) throws IllegalStateException, IOException {
		String filename = MyFilenameUtils.sanitizeRealFilename(multipartFile.getOriginalFilename());
		String pathname = MyFilenameUtils.getRandomFilename(MyFilenameUtils.getExt(filename));

		fileHandler.save(realPath + tempDir + tempDirId + pathname, multipartFile);

		return pathname;
		
	}
	
	public void insertFiles(String TB, String tempDirId, String dir, int boardSeq, String fileValues) throws JsonParseException, JsonMappingException, IOException{
		List<BoardFileVo> files = new ObjectMapper().readValue(fileValues, new TypeReference<List<BoardFileVo>>(){});
		
		BoardFileVo file;
		
		for (int i = 0; i < files.size(); i++) {
			file = files.get(i);
			file.setBoardSeq(boardSeq);
			
			String pathname = file.getPathname();
			FileStatus status = file.getStatus();
			
			switch(status){
			case NEW:
				if(boardFileDao.insert(TB, file)) {
					File existFile  = new File(realPath + tempDir + tempDirId, pathname);
					File newFile	= new File(realPath + dir, pathname);
					fileHandler.move(existFile, newFile);
				}
				break;
			case UNNEW:
				fileHandler.delete(realPath + dir, pathname);
				break;
			case REMOVE:
				if(boardFileDao.delete(TB, file.getSeq())) {
					fileHandler.delete(realPath + dir, pathname);
				}
				break;
			case BE: 
				break;
			}
		}
	}

}
