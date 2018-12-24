package com.cglee079.changoos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class MyFileUtils {
	private static MyFileUtils instance;

	
	public synchronized static MyFileUtils getInstance() {
		if(instance == null) {
			instance = new MyFileUtils();
		}
		return instance;
	}
	
	/**********************/
	
	
	public synchronized void emptyFolder(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		File file = null;
		
		if(files != null) {
			for(int i = 0; i < files.length; i++) {
				file = files[i];
				this.delete(file);
			}
		}
	}
	
	public synchronized boolean delete(File file) {
		if (file.exists()) {
			return file.delete();
		} else {
			return true;
		}
	}

	public synchronized boolean delete(String path) {
		File file = new File(path);
		return delete(file);
	}

	public synchronized boolean delete(String path, String filename) {
		File file = new File(path, filename);
		return delete(file);
	}

	public synchronized void copy(File existFile, File newFile) {
		 try {
			Files.copy(existFile.toPath(), newFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	}

	public synchronized void move(File existFile, File newFile) {
		copy(existFile, newFile);
		existFile.delete();
	}
	
}
