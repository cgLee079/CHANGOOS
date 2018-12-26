package com.cglee079.changoos.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formatter {
	public synchronized static String toDate(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	public synchronized static String toDateTime(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public synchronized static String toDate(String str) {
		try {
			if (str != null) {
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				return new SimpleDateFormat("yyyy.MM.dd").format(transFormat.parse(str));
			} else {
				return "";
			}
		} catch (ParseException e) {
			return "";
		}
	}
	
	public synchronized static String toTime(String str) {
		try {
			if (str != null) {
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				return new SimpleDateFormat("HH:mm:ss").format(transFormat.parse(str));
			} else {
				return "";
			}
		} catch (ParseException e) {
			return "";
		}
	}

}
