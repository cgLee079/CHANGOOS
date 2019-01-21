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
		
		String els = "div, span, applet, object, iframe,\r\n" + 
				"h1, h2, h3, h4, h5, h6, p, blockquote, pre,\r\n" + 
				"a, abbr, acronym, address, big, cite, code,\r\n" + 
				"del, dfn, em, img, ins, kbd, q, s, samp,\r\n" + 
				"small, strike, strong, sub, sup, tt, var,\r\n" + 
				"b, u, i, center,\r\n" + 
				"dl, dt, dd, ol, ul, li,\r\n" + 
				"fieldset, form, label, legend,\r\n" + 
				"table, caption, tbody, tfoot, thead, tr, th, td,\r\n" + 
				"article, aside, canvas, details, embed, \r\n" + 
				"figure, figcaption, footer, header, hgroup, \r\n" + 
				"menu, nav, output, ruby, section, summary,\r\n" + 
				"time, mark, audio, video";
		
		doc 		= Jsoup.parse(contents);
		body 		= doc.select("body");
		body.select(els).remove();
		
		newContents = body.html();
		newContents.replaceAll("(\r\n|\r|\n|\n\r)", " ");
		
		return newContents;
	}
	
}
