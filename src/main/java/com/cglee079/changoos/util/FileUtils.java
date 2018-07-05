package com.cglee079.changoos.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

	/**
	 * replace illegal characters in a filename with "_" illegal characters : : \ /
	 * * ? | < >
	 * 
	 * @param name
	 * @return
	 */
	public static String sanitizeFilename(String name) {
		return name.replaceAll("[:\\\\/*?|<>#]", "_");
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
			return ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void move(File existFile, File newFile) {
		copy(existFile, newFile);
		existFile.delete();
	}
}
