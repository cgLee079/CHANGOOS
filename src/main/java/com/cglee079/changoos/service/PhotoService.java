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
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.MyFileUtils;
import com.cglee079.changoos.util.MyFilenameUtils;
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
	
	public boolean insert(PhotoVo photo, MultipartFile imageFile) throws IllegalStateException, ImageProcessingException, MetadataException, IOException{
		photo.setLikeCnt(0);
		
		if(imageFile.getSize() != 0){
			photo = this.savePhoto(photo, imageFile);
		}
		
		return photoDao.insert(photo);
	}

	public boolean update(PhotoVo photo, MultipartFile imageFile) throws IllegalStateException, ImageProcessingException, MetadataException, IOException{
		boolean result = photoDao.update(photo);
		
		if(result) {
			if(imageFile.getSize() != 0){
				MyFileUtils fileUtils = MyFileUtils.getInstance();
				
				fileUtils.delete(realPath + photo.getImage());
				fileUtils.delete(realPath + photo.getSnapsht());
				
				photo = this.savePhoto(photo, imageFile);
			} 
		}
		return result;
	}
	
	public boolean delete(int seq) {
		PhotoVo photo = this.get(seq);
		boolean result = photoDao.delete(seq);
		
		if(result) {
			MyFileUtils fileUtils = MyFileUtils.getInstance();
			
			fileUtils.delete(realPath + photo.getImage());
			fileUtils.delete(realPath + photo.getSnapsht());
		}
		
		return result;
	}
	
	public PhotoVo get(int seq){
		return photoDao.get(seq);
	}
	
	public PhotoVo savePhoto(PhotoVo photo, MultipartFile imageFile) throws IllegalStateException, IOException, ImageProcessingException, MetadataException {
		String timeStamp	= TimeStamper.stamp("YYMMdd_HHmmss");
		String imgName		= "photo_" + timeStamp + "_" + MyFilenameUtils.sanitizeRealFilename(photo.getName());
		String snapshtName	= "photo_snapsht_" + timeStamp + "_" + MyFilenameUtils.sanitizeRealFilename(photo.getName());
		
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
