package com.cglee079.changoos.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cglee079.changoos.constants.Path;

public class TempFolderManager {
	private static TempFolderManager instance;

	
	public synchronized static TempFolderManager getInstance() {
		if(instance == null) {
			instance = new TempFolderManager();
		}
		return instance;
	}
	
	/**********************/
	
	private String realPath;
	
	private TempFolderManager() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		realPath = request.getSession().getServletContext().getRealPath("/");
	}
	
	public synchronized void removeAll() {
		File tempDir = new File(realPath + Path.TEMP_PATH);
		File[] tempFiles = tempDir.listFiles();
		File tempFile = null;
		
		for(int i = 0; i < tempFiles.length; i++) {
			tempFile = tempFiles[i];
			MyFileUtils.delete(tempFile);
		}
	}
	
}
