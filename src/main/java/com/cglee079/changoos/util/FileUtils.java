package com.cglee079.changoos.util;

public class FileUtils {

	 /**
	  * replace illegal characters in a filename with "_"
	  * illegal characters :
	  *           : \ / * ? | < >
	  * @param name
	  * @return
	  */
	  public static String sanitizeFilename(String name) {
	    return name.replaceAll("[:\\\\/*?|<>#]", "_");
	  }
}
