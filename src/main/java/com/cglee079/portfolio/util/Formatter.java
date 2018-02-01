package com.cglee079.portfolio.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {
	public synchronized static String toDate(String str) {
		try {
			if (str != null) {
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				Date dt;
				dt = transFormat.parse(str);
				return new SimpleDateFormat("yyyy.MM.dd").format(dt);
			} else {
				return "";
			}
		} catch (ParseException e) {
			return str;
		}
	}
	
	public static String toDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	public static String toDateTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public synchronized static String toTime(String str) {
		try {
			if (str != null) {
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				Date dt;
				dt = transFormat.parse(str);
				return new SimpleDateFormat("HH:mm:ss").format(dt);
			} else {
				return "";
			}
		} catch (ParseException e) {
			return str;
		}
	}

}
