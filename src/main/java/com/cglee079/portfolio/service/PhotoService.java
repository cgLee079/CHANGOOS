package com.cglee079.portfolio.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.portfolio.dao.PhotoDao;
import com.cglee079.portfolio.model.ProjectVo;
import com.cglee079.portfolio.util.Formatter;
import com.cglee079.portfolio.util.ImageManager;
import com.cglee079.portfolio.util.TimeStamper;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.MetadataException;
import com.cglee079.portfolio.model.PhotoVo;

@Service
public class PhotoService {
	public static final String PHOTO_PATH	 	= "/uploaded/photos/photos/";
	public static final String SNAPSHT_PATH		= "/uploaded/photos/snapshts/";
	
	
	@Autowired
	private PhotoDao photoDao;
	
	public List<PhotoVo> list(){
		return photoDao.list();
	}
	
	public boolean insert(PhotoVo photo){
		photo.setLike(0);
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
	
	public PhotoVo saveFile(PhotoVo photo, String rootPath, MultipartFile imageFile) throws IllegalStateException, IOException, ImageProcessingException, MetadataException {
		String timeStamp	= TimeStamper.stamp();
		String imgName		= "photo_" + timeStamp + "_" + photo.getName();
		String snapshtName	= "photo_snapsht_" + timeStamp + "_" + photo.getName();
		
		//naming
		String imgExt = ImageManager.getExt(imageFile.getOriginalFilename());
		imgName 	+= "." + imgExt;
		snapshtName += "." + imgExt;
		
		//multipartfile save;
		File photofile = new File(rootPath + PHOTO_PATH, imgName);
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
		File snapshtfile = new File(rootPath + SNAPSHT_PATH, snapshtName);
		ImageIO.write(shapshtImg, imgExt, snapshtfile);
		
		photo.setSnapsht(SNAPSHT_PATH + snapshtName);
		photo.setImage(PHOTO_PATH + imgName);
		
		return photo;
	}
	
	public void deleteFile(String rootPath, String subPath){
		File existFile = null;
		existFile = new File (rootPath + subPath);
		if(existFile.exists()){ existFile.delete(); }
	}
}
