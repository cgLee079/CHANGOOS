package com.cglee079.changoos.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.dao.PhotoDao;
import com.cglee079.changoos.model.PhotoVo;
import com.cglee079.changoos.util.FileUtils;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.TimeStamper;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;

@Service
public class PhotoService {
	@Value("#{servletContext.getRealPath('/')}")
    private String realPath;
	
	@Autowired
	private PhotoDao photoDao;
	
	public List<PhotoVo> list(Map<String, Object> map){
		return photoDao.list(map);
	}
	
	public boolean insert(PhotoVo photo){
		photo.setLikeCnt(0);
		return photoDao.insert(photo);
	}

	public boolean update(PhotoVo photo){
		return photoDao.update(photo);
	}
	
	public boolean delete(int seq) {
		return photoDao.delete(seq);
	}
	
	public PhotoVo get(int seq){
		return photoDao.get(seq);
	}
	
	public PhotoVo saveFile(PhotoVo photo, MultipartFile imageFile) throws IllegalStateException, IOException, ImageProcessingException, MetadataException {
		String timeStamp	= TimeStamper.stamp();
		String imgName		= "photo_" + timeStamp + "_" + FileUtils.sanitizeFilename(photo.getName());
		String snapshtName	= "photo_snapsht_" + timeStamp + "_" + FileUtils.sanitizeFilename(photo.getName());
		
		//naming
		String imgExt = ImageManager.getExt(imageFile.getOriginalFilename());
		imgName 	+= "." + imgExt;
		snapshtName += "." + imgExt;
		
		//multipartfile save;
		File photofile = new File(realPath + Path.PHOTO_PHOTO_PATH, imgName);
		imageFile.transferTo(photofile);
		HashMap<String, String> metadata = ImageManager.getImageMetaData(photofile);
		photo.setDate(Formatter.toDate(metadata.get("Date/Time Original")));
		photo.setTime(Formatter.toTime(metadata.get("Date/Time Original")));
		photo.setDevice(metadata.get("Model"));
		
		//photo resize , rotate;
		int orientation = ImageManager.getOrientation(photofile);
		BufferedImage photoImg = ImageManager.getLowScaledImage(photofile, 720 , imgExt); 
		if(orientation != 1){
			photoImg =  ImageManager.rotateImageForMobile(photoImg, orientation);
		}
		ImageIO.write(photoImg, imgExt, photofile);
		
		//make snapsht
		BufferedImage shapshtImg = ImageManager.getLowScaledImage(photofile, 100, imgExt);
		/*
		if(orientation != 1){
			shapshtImg = ImageManager.rotateImageForMobile(shapshtImg, orientation);
		}
		*/
		File snapshtfile = new File(realPath + Path.PHOTO_SNAPSHT_PATH, snapshtName);
		ImageIO.write(shapshtImg, imgExt, snapshtfile);
		
		photo.setSnapsht(Path.PHOTO_SNAPSHT_PATH + snapshtName);
		photo.setImage(Path.PHOTO_PHOTO_PATH + imgName);
		
		return photo;
	}
	
	public void deleteFile(String subPath){
		FileUtils.delete(realPath + subPath);
	}

	public List<Integer> seqs() {
		return photoDao.seqs();
	}
	
	public int doLike(Map<Integer, Boolean> likePhotos, int seq, boolean like) {
		PhotoVo photo = photoDao.get(seq);
		
		int likeCnt = -1;
		if(like) { 
			likePhotos.put(seq, true);
			likeCnt = photo.getLikeCnt() + 1; 
		}else {
			likePhotos.put(seq, false);
			likeCnt = photo.getLikeCnt() -1; 
		}
		
		photo.setLikeCnt(likeCnt);
		photoDao.update(photo);
		
		return likeCnt;
	}
}
