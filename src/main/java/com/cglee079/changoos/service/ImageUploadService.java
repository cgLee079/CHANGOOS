package com.cglee079.changoos.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.MyFilenameUtils;

@Service
public class ImageUploadService {
	
	@Value("#{servletContext.getRealPath('/')}")
	private String realPath;
	
	@Value("#{location['temp.image.dir.url']}")
	private String imageTempDir;
	
	public String saveContentImage(String base64) throws IOException {
		String ImageExt = ".PNG";
		String pathname = MyFilenameUtils.getRandomImagename(ImageExt);

		base64 = base64.split(",")[1];
		byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64);
		BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
		File file = new File(realPath + imageTempDir, pathname);
		ImageIO.write(bufImg, ImageExt, file);

		if (!ImageExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
			ImageManager imageManager = ImageManager.getInstance();
			BufferedImage image = imageManager.getLowScaledImage(file, 720, ImageExt);
			ImageIO.write(image, ImageExt, file);
		}

		return pathname;
	}
	
	public String saveContentImage(MultipartFile multipartFile) throws IOException {
		String filename = multipartFile.getOriginalFilename();
		String ImageExt = MyFilenameUtils.getExt(filename);
		String pathname = MyFilenameUtils.getRandomImagename(ImageExt);

		File file = new File(realPath + imageTempDir, pathname);
		multipartFile.transferTo(file);

		if (!ImageExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
			ImageManager imageManager = ImageManager.getInstance();
			BufferedImage image = imageManager.getLowScaledImage(file, 720, ImageExt);
			ImageIO.write(image, ImageExt, file);
		}

		return pathname;
	}
	
}
