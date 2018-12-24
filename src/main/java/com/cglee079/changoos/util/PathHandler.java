package com.cglee079.changoos.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cglee079.changoos.constants.Path;

public class PathHandler{
		
	/**
	 * 글 작성완료시 동작
	 * TEMP 폴더에 저장된 이미지 파일의 URL을, 업로드 폴더 URL로 변경
	 **/
	public static String changeImagePath(String contents, String fromPath, String toPath) {
		Document doc = Jsoup.parseBodyFragment(contents);
        doc.outputSettings().prettyPrint(false);
		
		Elements els = doc.select("img");
		Element el = null;
		
		String src = "";
		String newSrc = "";
		int index = -1;
		for(int i = 0; i < els.size(); i++) {
			el 		= els.get(i);
			src 	= el.attr("src");
			
			//이미지 경로 변경
			index = src.indexOf(Path.TEMP_PATH);
			if(index != -1) {
				newSrc = toPath  + src.substring(index + Path.TEMP_PATH.length(), src.length());
				el.attr("src", newSrc);
			}
			
			
		}
		
		return doc.select("body").html();
	}

	

}
