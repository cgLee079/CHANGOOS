package com.cglee079.changoos.util;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cglee079.changoos.dao.BlogImageDao;
import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.model.ImageVo;

public class CommonUtils {

	public static String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		} else if (header.indexOf("Trident/7.0") > -1) {
			return "MSIE";
		}
		return "Firefox";
	}

	public static String changeImagePath( String realPath, String photoPath, String fromPath, String toPath, String pathname) {
			String filename = "";
			// 이미지 경로 변경
			int index = photoPath.indexOf(fromPath);
			if (index != -1) {
				filename = photoPath.substring(index + fromPath.length(), photoPath.length());
				MyFileUtils fileUtils = MyFileUtils.getInstance();
				fileUtils.move(new File(realPath + fromPath + filename), new File(realPath + toPath + pathname));
			}

			return filename;
	}

}
