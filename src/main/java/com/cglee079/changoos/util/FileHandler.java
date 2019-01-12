package com.cglee079.changoos.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileHandler {
	
	public void emptyDir(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		File file = null;
		
		if(files != null) {
			for(int i = 0; i < files.length; i++) {
				file = files[i];
				if(file.isFile()) {
					this.delete(file);
				} 
				
				if(file.isDirectory()) {
					emptyDir(file.getPath());
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

	public boolean move(String existFile, String newFile) {
		try {
			Files.createDirectories(Paths.get(newFile).getParent());
			Files.move(Paths.get(existFile), Paths.get(newFile));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean move(File existFile, File newFile) {
		return this.move(existFile.getPath(), newFile.getPath());
	}

	
	public boolean copy(File existFile, File newFile) {
		try {
			Files.createDirectories(newFile.toPath().getParent());
			Files.copy(existFile.toPath(), newFile.toPath());
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public File save(String path, MultipartFile multipartFile) {
		File file = new File(path);
		try {
			Files.createDirectories(Paths.get(path).getParent());
			multipartFile.transferTo(file);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return file;
		
	}

	public File save(String path, String ext, BufferedImage bufImg) {
		File file = new File(path);
		try {
			Files.createDirectories(Paths.get(path).getParent());
			ImageIO.write(bufImg, ext, file);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return file;
	}
	
}
