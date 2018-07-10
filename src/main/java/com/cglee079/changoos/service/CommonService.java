package com.cglee079.changoos.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.util.MyFileUtils;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.TimeStamper;

@Service
public class CommonService{
	
	@Value("#{servletContext.getRealPath('/')}")
    private String realPath;
	
	public String saveContentImage(MultipartFile multiFile) throws IllegalStateException, IOException {
		String filename	= "content_" + TimeStamper.stamp() + "_";
		String imgExt 	= null;
		
		if(multiFile != null){
			filename += MyFileUtils.sanitizeRealFilename(multiFile.getOriginalFilename());
			imgExt = ImageManager.getExt(filename);
			File file = new File(realPath + Path.TEMP_CONTENTS_PATH , filename);
			multiFile.transferTo(file);
			if(!imgExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
				BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
				ImageIO.write(image, imgExt, file);
			}
		}
		
		return Path.TEMP_CONTENTS_PATH + filename;
	}
	
	public String saveContentImage(String base64) throws IOException {
		String filename	= "content_" + TimeStamper.stamp() + "_pasteImage.png";
		String imgExt = "png";
		
		base64 = base64.split(",")[1];
		byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64);
		BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
		File file =  new File(realPath + Path.TEMP_CONTENTS_PATH, filename);
		ImageIO.write(bufImg, imgExt, file);
		
		if(!imgExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
		}
		
		return Path.TEMP_CONTENTS_PATH + filename;
	}
	
	public String copyToTempPath(String contents, String fromPath) {
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
			
			//파일 이동
			File existFile  = new File(realPath + src);
			File newFile	= new File(realPath + newSrc);
			MyFileUtils.copy(existFile, newFile);
		}
		
		return doc.select("body").html();
	}
	
	public String moveToSavePath(String contents, String toPath) {
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
			File existFile  = new File(realPath + src);
			File newFile	= new File(realPath + newSrc);
			MyFileUtils.move(existFile, newFile);
		}
		
		File tempDir = new File(realPath + Path.TEMP_CONTENTS_PATH);
		File[] tempFiles = tempDir.listFiles();
		File tempFile = null;
		String tempFilePath = null;
		String filename = null;
		for(int i = 0; i < tempFiles.length; i++) {
			tempFile = tempFiles[i];
			tempFilePath = tempFile.getPath();
			filename = tempFilePath.substring(tempFilePath.indexOf(realPath + Path.TEMP_CONTENTS_PATH) + (realPath + Path.TEMP_CONTENTS_PATH).length(), tempFilePath.length());
			MyFileUtils.delete(realPath + toPath, filename);
			MyFileUtils.delete(tempFile);
		}
		
		return doc.select("body").html();
	}
	
	public void removeContentImage(String content) {
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
			MyFileUtils.delete(realPath + imgPaths.get(i));
		}

	}

}
