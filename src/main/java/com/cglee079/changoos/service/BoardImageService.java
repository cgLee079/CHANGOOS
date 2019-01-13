package com.cglee079.changoos.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.config.code.ImageStatus;
import com.cglee079.changoos.dao.BoardImageDao;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.util.FileHandler;
import com.cglee079.changoos.util.ImageHandler;
import com.cglee079.changoos.util.MyFilenameUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BoardImageService {
	@Autowired private BoardImageDao boardImageDao;
	@Autowired private ImageHandler imageHandler;
	@Autowired private FileHandler fileHandler; 
	
	@Value("#{servletContext.getRealPath('/')}") 	private String realPath;
	@Value("#{location['temp.dir.url']}")  			private String tempDir;
 	@Value("#{constant['image.max.width']}") 		private int maxWidth;
 	
	public String saveBase64(String tempDirId, String base64) throws IOException {
		String ImageExt = "PNG";
		String pathname = MyFilenameUtils.getRandomImagename(ImageExt);

		base64 = base64.split(",")[1];
		byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64);
		BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
		
		File file = fileHandler.save(realPath + tempDir + tempDirId + pathname, ImageExt, bufImg);
		imageHandler.saveLowscaleImage(file, maxWidth, ImageExt);

		return pathname;
	}

	public String saveImage(String tempDirId, MultipartFile multipartFile) throws IOException {
		String filename = multipartFile.getOriginalFilename();
		String ImageExt = MyFilenameUtils.getExt(filename);
		String pathname = MyFilenameUtils.getRandomImagename(ImageExt);

		File file = fileHandler.save(realPath + tempDir + tempDirId +  pathname, multipartFile);
		imageHandler.saveLowscaleImage(file, maxWidth, ImageExt);

		return pathname;
	}
	
	public String insertImages(String TB, String tempDirId, String dir, int boardSeq, String contents, String imageValues) throws JsonParseException, JsonMappingException, IOException {
		List<BoardImageVo> images = new ObjectMapper().readValue(imageValues, new TypeReference<List<BoardImageVo>>(){});
		
		BoardImageVo image;
		
		for (int i = 0; i < images.size(); i++) {
			image = images.get(i);
			image.setBoardSeq(boardSeq);
			
			String pathname = image.getPathname();
			ImageStatus status = image.getStatus();
			
			switch(status){
			case NEW:
				if(boardImageDao.insert(TB, image)) {
					//임시폴더에서 본 폴더로 이동
					File existFile  = new File(realPath + tempDir + tempDirId, pathname);
					File newFile	= new File(realPath + dir, pathname);
					if(fileHandler.move(existFile, newFile)) {
						contents = contents.replaceAll(tempDir + tempDirId + pathname, dir + pathname);
					}
				}
				break;
			case UNNEW:
				fileHandler.delete(realPath + tempDir + tempDirId, pathname);
				break;
			case REMOVE:
				if(boardImageDao.delete(TB, image.getSeq())) {
					fileHandler.delete(realPath + dir, pathname);
				}
				break;
			case BE: 
				break;
			}
		}
		
		return contents;
	}

}
