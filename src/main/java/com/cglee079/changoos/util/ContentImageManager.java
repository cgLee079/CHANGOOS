package com.cglee079.changoos.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.constants.Path;

public class ContentImageManager{
	
	public static String getRealPath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getSession().getServletContext().getRealPath("/");
	}

	public static String saveContentImage(MultipartFile multiFile) throws IllegalStateException, IOException {
		String filename	= "content_" + TimeStamper.stamp() + "_";
		String imgExt 	= null;
		
		if(multiFile != null){
			filename += MyFileUtils.sanitizeRealFilename(multiFile.getOriginalFilename());
			imgExt = ImageManager.getExt(filename);
			File file = new File(getRealPath() + Path.TEMP_CONTENTS_PATH , filename);
			multiFile.transferTo(file);
			if(!imgExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
				BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
				ImageIO.write(image, imgExt, file);
			}
		}
		
		return Path.TEMP_CONTENTS_PATH + filename;
	}
	
	public static String saveContentImage(String base64) throws IOException {
		String filename	= "content_" + TimeStamper.stamp() + "_pasteImage.png";
		String imgExt = "png";
		
		base64 = base64.split(",")[1];
		byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64);
		BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
		File file =  new File(getRealPath() + Path.TEMP_CONTENTS_PATH, filename);
		ImageIO.write(bufImg, imgExt, file);
		
		if(!imgExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
		}
		
		return Path.TEMP_CONTENTS_PATH + filename;
	}
	
	/** 내용에 포함된 이미지파일을, 업로드 폴더에서 삭제 **/
	public static void removeContentImage(String content) {
		List<String> imgPaths = new ArrayList<String>();

		Document doc = Jsoup.parse(content);
		Elements els = doc.select("img");
		Element el = null;

		for (int i = 0; i < els.size(); i++) {
			el = els.get(i);
			imgPaths.add(el.attr("src"));
		}

		int imgPathsLength = imgPaths.size();
		for (int i = 0; i < imgPathsLength; i++) {
			MyFileUtils.delete(getRealPath() + imgPaths.get(i));
		}

	}
	
	
	/**
	 * 글 수정시 동작
	 * 업로드 폴더 이미지 파일을 TEMP 폴더에 복사.
	 * 업로드 폴더 URL을, TEMP폴더 URL로 변경.
	 **/
	public static String copyToTempPath(String contents, String fromPath) {
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
			index = src.indexOf(fromPath);
			if(index != -1) {
				newSrc = Path.TEMP_CONTENTS_PATH  + src.substring(index + fromPath.length(), src.length());
				el.attr("src", newSrc);
			}
			
			//파일 복사
			File existFile  = new File(getRealPath() + src);
			File newFile	= new File(getRealPath() + newSrc);
			MyFileUtils.copy(existFile, newFile);
		}
		
		return doc.select("body").html();
	}
	
	/**
	 * 글 작성완료시 동작
	 * TEMP 폴더에 저장된 이미지 파일을, 업로드 폴더로 파일 이동
	 * TEMP 폴더에 저장된 이미지 파일의 URL을, 업로드 폴더 URL로 변경
	 **/
	public static String moveToSavePath(String contents, String toPath) {
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
			index = src.indexOf(Path.TEMP_CONTENTS_PATH);
			if(index != -1) {
				newSrc = toPath  + src.substring(index + Path.TEMP_CONTENTS_PATH.length(), src.length());
				el.attr("src", newSrc);
			}
			
			//파일 이동
			File existFile  = new File(getRealPath() + src);
			File newFile	= new File(getRealPath() + newSrc);
			MyFileUtils.move(existFile, newFile);
		}
		
		File tempDir = new File(getRealPath() + Path.TEMP_CONTENTS_PATH);
		File[] tempFiles = tempDir.listFiles();
		File tempFile = null;
		
		//업로드 파일로 이동했음에도 불구하고, 남아있는 TEMP 폴더의 이미지 파일을 삭제.
		for(int i = 0; i < tempFiles.length; i++) {
			tempFile = tempFiles[i];
			MyFileUtils.delete(tempFile);
		}
		
		return doc.select("body").html();
	}
	

}
