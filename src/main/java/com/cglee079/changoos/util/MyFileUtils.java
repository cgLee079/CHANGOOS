package com.cglee079.changoos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.RandomStringUtils;

public class MyFileUtils {
	public static String getExt(String filename) {
		String extension = "";
		int i = filename.lastIndexOf('.');
		if (i > 0) {
			extension = filename.substring(i + 1);
		}
		return extension;
	}

	public static String getRandomFilename(String ext) {
		String filename = "";
		filename += RandomStringUtils.randomAlphanumeric(6) + "_";
		filename += RandomStringUtils.randomAlphanumeric(6) + "_";
		filename += RandomStringUtils.randomAlphanumeric(6) + "_";
		filename += TimeStamper.stamp();
		filename += "." + ext;
		return filename.toUpperCase();
	}

	public static String sanitizeRealFilename(String name) {
		return name.replaceAll("[:\\\\/*?|<>\"]", "_");
	}

	
	/** -------------------------------------------- **/
	
	
	public static boolean delete(File file) {
		if (file.exists()) {
			return file.delete();
		} else {
			return true;
		}
	}

	public static boolean delete(String path) {
		File file = new File(path);
		return delete(file);
	}

	public static boolean delete(String path, String filename) {
		File file = new File(path, filename);
		return delete(file);
	}

	public static void copy(File existFile, File newFile) {
		try {

			FileInputStream fis = new FileInputStream(existFile);
			FileOutputStream fos = new FileOutputStream(newFile);

			int data = 0;

			while ((data = fis.read()) != -1) {
				fos.write(data);
			}
			fis.close();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void move(File existFile, File newFile) {
		copy(existFile, newFile);
		existFile.delete();
	}

}
