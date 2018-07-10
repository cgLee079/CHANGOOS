package com.cglee079.changoos.util;

import javax.servlet.http.HttpServletRequest;

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

}
