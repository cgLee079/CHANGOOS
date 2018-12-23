package com.cglee079.changoos.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.MyFileUtils;

@Service
public class ImageUploadService {
	
	@Value("#{servletContext.getRealPath('/')}")
	private String realPath;
	
	public String saveContentImage(String filename, String base64) throws IOException {
		String ImageExt = MyFileUtils.getExt(filename);
		String pathname = MyFileUtils.getRandomFilename(ImageExt);

		base64 = base64.split(",")[1];
		byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64);
		BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
		File file = new File(realPath + Path.TEMP_CONTENTS_PATH, pathname);
		ImageIO.write(bufImg, ImageExt, file);

		if (!ImageExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, ImageExt);
			ImageIO.write(image, ImageExt, file);
		}

		return pathname;
	}
	
	public String saveContentImage(String base64) throws IOException {
		return this.saveContentImage("TEMP.PNG", base64);
	}

}
