package com.cglee079.changoos.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamper {
	public synchronized static String stamp(String format){
		return new SimpleDateFormat(format).format(new Date());
	}
}
