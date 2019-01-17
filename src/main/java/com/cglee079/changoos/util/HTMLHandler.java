package com.cglee079.changoos.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HTMLHandler {
	public static String extractHTMLText(String contents) {
		String newContents = "";
		Document doc = null;
		Elements els = null;
		
		//내용중 텍스트만 뽑기
		newContents = "";
		doc 		= Jsoup.parse(contents);
		els 		= doc.select("*");
		if(els.eachText().size() > 0) {
			newContents = els.eachText().get(0);
		}
		
		newContents.replaceAll("(\r\n|\r|\n|\n\r)", " ");

		return newContents;
	}

	public static String removeHTML(String contents) {
		String newContents = "";
		Document doc = null;
		Elements body = null;
		
		doc 		= Jsoup.parse(contents);
		body 		= doc.select("body");
		body.select("p,img").remove();
		
		newContents = body.html();
		newContents.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		
		return newContents;
	}
	
}
