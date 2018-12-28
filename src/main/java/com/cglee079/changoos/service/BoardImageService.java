package com.cglee079.changoos.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.BoardImageDao;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.MyFileUtils;
import com.cglee079.changoos.util.MyFilenameUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BoardImageService {
	
	@Autowired
	private BoardImageDao boardImageDao;
	
	@Value("#{servletContext.getRealPath('/')}")
	private String realPath;
	
	@Value("#{location['temp.image.dir.url']}")
	private String imageTempDir;
	
	@Value("#{constant['image.max.width']}")
	private int maxWidth;
	
	public List<BoardImageVo> list(String TB, int boardSeq) {
		return boardImageDao.list(TB, boardSeq);
	}
	
	public String saveBase64(String base64) throws IOException {
		String ImageExt = ".PNG";
		String pathname = MyFilenameUtils.getRandomImagename(ImageExt);

		base64 = base64.split(",")[1];
		byte[] imageBytes = DatatypeConverter.parseBase64Binary(base64);
		BufferedImage bufImg = ImageIO.read(new ByteArrayInputStream(imageBytes));
		File file = new File(realPath + imageTempDir, pathname);
		ImageIO.write(bufImg, ImageExt, file);

		if (!ImageExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
			ImageManager imageManager = ImageManager.getInstance();
			BufferedImage image = imageManager.getLowScaledImage(file, maxWidth, ImageExt);
			ImageIO.write(image, ImageExt, file);
		}

		return pathname;
	}
	
	public String saveImage(MultipartFile multipartFile) throws IOException {
		String filename = multipartFile.getOriginalFilename();
		String ImageExt = MyFilenameUtils.getExt(filename);
		String pathname = MyFilenameUtils.getRandomImagename(ImageExt);

		File file = new File(realPath + imageTempDir, pathname);
		multipartFile.transferTo(file);

		if (!ImageExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
			ImageManager imageManager = ImageManager.getInstance();
			BufferedImage image = imageManager.getLowScaledImage(file, maxWidth, ImageExt);
			ImageIO.write(image, ImageExt, file);
		}

		return pathname;
	}
	
	public String insertImages(String TB, String dir, int boardSeq, String contents, String imageValues) throws JsonParseException, JsonMappingException, IOException {
		List<BoardImageVo> images = new ObjectMapper().readValue(imageValues, new TypeReference<List<BoardImageVo>>(){});
		
		BoardImageVo image;
		MyFileUtils fileUtils = MyFileUtils.getInstance();
		
		for (int i = 0; i < images.size(); i++) {
			image = images.get(i);
			image.setBoardSeq(boardSeq);
			switch(image.getStatus()) {
			case "NEW" : //새롭게 추가된 이미지
				if(boardImageDao.insert(TB, image)) {
					//임시폴더에서 본 폴더로 이동
					String pathname = image.getPathname();
					File existFile  = new File(realPath + imageTempDir, pathname);
					File newFile	= new File(realPath + dir, pathname);
					if(fileUtils.move(existFile, newFile)) {
						contents = contents.replaceAll(imageTempDir + pathname, dir + pathname);
					}
				}
				break;
			case "REMOVE" : //기존에 있던 이미지 중, 삭제된 이미지
				if(boardImageDao.delete(TB, image.getSeq())) {
					fileUtils.delete(realPath + dir, image.getPathname());
				}
				break;
			}
		}
		
		//업로드 파일로 이동했음에도 불구하고, 남아있는 TEMP 폴더의 이미지 파일을 삭제.
		//즉, 이전에 글 작성 중 작성을 취소한 경우 업로드가 되었던 이미지파일들이 삭제됨.
		//TODO 여러사용자가 동시에 파일을 업로드한다면..?
		//fileUtils.emptyDir(realPath + imageTempDir);
		
		
		return contents;
	}

}
