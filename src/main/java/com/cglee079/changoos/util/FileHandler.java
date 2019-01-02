package com.cglee079.changoos.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;

@Component
public class FileHandler {
	public void emptyDir(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		File file = null;
		
		if(files != null) {
			for(int i = 0; i < files.length; i++) {
				file = files[i];
				if(!file.isDirectory()) {
					this.delete(file);
				}
			}
		}
	}
	
	public boolean delete(File file) {
		try {
			Files.deleteIfExists(file.toPath());
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean delete(String path) {
		try {
			Files.deleteIfExists(Paths.get(path));
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean delete(String path, String filename) {
		return delete(path + filename);
	}

	public boolean move(File existFile, File newFile) {
		try {
			Files.move(existFile.toPath(), newFile.toPath());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean copy(File existFile, File newFile) {
		try {
			Files.copy(existFile.toPath(), newFile.toPath());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean move(String existFile, String newFile) {
		try {
			Files.move(Paths.get(existFile), Paths.get(newFile));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
