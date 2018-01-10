package com.cglee079.portfolio.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamper {
	public synchronized static String stamp(){
		return new SimpleDateFormat("YYMMdd_HHmmss").format(new Date());
	}
}
