package com.cglee079.changoos.service;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.util.MyFilenameUtils;

@Service
public class FileService {
	
	@Value("#{servletContext.getRealPath('/')}")
	private String realPath;
	
	@Value("#{location['temp.file.dir.url']}")
	private String tempDir;
	
	public String saveFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
		String filename = MyFilenameUtils.sanitizeRealFilename(multipartFile.getOriginalFilename());
		String pathname = MyFilenameUtils.getRandomFilename(MyFilenameUtils.getExt(filename));
		long size = multipartFile.getSize();

		
		if (size > 0) {
			File file = new File(realPath + tempDir, pathname);
			multipartFile.transferTo(file);
		}

		return pathname;
	}

}
