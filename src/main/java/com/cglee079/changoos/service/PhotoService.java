package com.cglee079.changoos.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.PhotoDao;
import com.cglee079.changoos.model.PhotoVo;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.ImageHandler;
import com.cglee079.changoos.util.FileHandler;
import com.cglee079.changoos.util.MyFilenameUtils;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;

@Service
public class PhotoService {
	@Autowired private PhotoDao photoDao;
	@Autowired private ImageHandler imageHandler;
	@Autowired private FileHandler fileHandler; 
	
	@Value("#{servletContext.getRealPath('/')}") private String realPath;
	@Value("#{location['temp.photo.dir.url']}") 	private String tempDir;
	@Value("#{location['photo.origin.dir.url']}") 	private String originDir;
	@Value("#{location['photo.thumb.dir.url']}") 	private String thumbDir;
	@Value("#{constant['photo.origin.max.width']}") private int originMaxWidth;
	@Value("#{constant['photo.thumb.max.width']}") 	private int thumbMaxWidth;
	
	public PhotoVo get(int seq) {
		return photoDao.get(seq);
	}
	
	public PhotoVo get(Set<Integer> likePhotos, int seq) {
		PhotoVo photo = photoDao.get(seq);
		photo.setLike(likePhotos.contains(seq));
		
		return photo;
	}

	public List<PhotoVo> list(Map<String, Object> map) {
		return photoDao.list(map);
	}

	public List<Integer> seqs() {
		return photoDao.seqs();
	}
	
	public boolean insert(PhotoVo photo) throws IllegalStateException, ImageProcessingException, MetadataException, IOException {
		photo.setLikeCnt(0);

		// TEMP 폴더에서, 사진 파일 옮기기
		String photoFilePath = realPath + tempDir + photo.getPathname();
		String movedPhotoPath = realPath + originDir + photo.getPathname();
		fileHandler.move(photoFilePath, movedPhotoPath);

		// TEMP 폴더에서, 스냅샷 파일 옮기기
		String snapshotFilePath = realPath + tempDir + photo.getThumbnail();
		String movedSnapshtFilePath = realPath + thumbDir + photo.getThumbnail();
		fileHandler.move(snapshotFilePath, movedSnapshtFilePath);
		
		boolean result = photoDao.insert(photo);
		return result;
	}

	@Transactional
	public boolean update(PhotoVo photo) throws IllegalStateException, ImageProcessingException, MetadataException, IOException {
		// DB에 저장된 사진정보와 새로 수정된 사진 정보를 비교한다
		// 다르다면, 사진을 바꿨으므로, 기존 사진을 삭제한다.
		PhotoVo savedPhoto = photoDao.get(photo.getSeq());
		if (!savedPhoto.getPathname().equals(photo.getPathname())) {
			// 기존에 수정이전 저장된 사진, 스냅샷 파일 삭제
			fileHandler.delete(realPath + originDir, savedPhoto.getPathname());
			fileHandler.delete(realPath + thumbDir, savedPhoto.getThumbnail());

			// TEMP 폴더에서, 사진 파일 옮기기
			String photoFilePath = realPath + tempDir + photo.getPathname();
			String movedPhotoPath = realPath + originDir + photo.getPathname();
			fileHandler.move(photoFilePath, movedPhotoPath);

			// TEMP 폴더에서, 스냅샷 파일 옮기기
			String snapshotFilePath = realPath + tempDir + photo.getThumbnail();
			String movedSnapshtFilePath = realPath + thumbDir + photo.getThumbnail();
			fileHandler.move(snapshotFilePath, movedSnapshtFilePath);
		}

		
		boolean result = photoDao.update(photo);
		return result;
	}

	@Transactional
	public boolean delete(int seq) {
		PhotoVo photo = photoDao.get(seq);
		
		// 기존에 수정이전 저장된 사진, 스냅샷 파일 삭제
		boolean result = photoDao.delete(seq);
		if(result) {
			fileHandler.delete(realPath + thumbDir, photo.getThumbnail());
			fileHandler.delete(realPath + originDir, photo.getPathname());
		}
		return result;
	}

	@Transactional
	public PhotoVo doLike(Set<Integer> likePhotos, int seq, boolean like) {
		PhotoVo photo = photoDao.get(seq);

		int likeCnt;
		if (like) {
			likePhotos.add(seq);
			likeCnt = photo.getLikeCnt() + 1;
		} else {
			likePhotos.remove(seq);
			likeCnt = photo.getLikeCnt() - 1;
		}

		photo.setLikeCnt(likeCnt);
		photo.setLike(like);
		photoDao.update(photo);

		return photo;
	}

	public PhotoVo savePhoto(MultipartFile multipartFile) throws IllegalStateException, IOException {
		PhotoVo photo = new PhotoVo();

		String filename = multipartFile.getOriginalFilename();
		String ext = MyFilenameUtils.getExt(filename);
		String pathname = MyFilenameUtils.getRandomImagename(ext);
		String photoPathname = "PHOTO." + pathname;
		String thumbPathname = "PHOTO.THUMB." + pathname;

		// 사진 저장
		File photofile = new File(realPath + tempDir, photoPathname);
		multipartFile.transferTo(photofile);

		// 메타데이터 가져오기
		Map<String, String> metadata = this.getMetaData(photofile);
		photo.setDate(metadata.get("Date"));
		photo.setTime(metadata.get("Time"));
		photo.setDevice(metadata.get("Model"));

		// 저장된 사진 축소 및 회전
		imageHandler.saveLowscaleImage(photofile, originMaxWidth, ext);

		// 저장된 사진으로, 사진 스냅샷 만들기
		File thumbFile = new File(realPath + tempDir, thumbPathname);
		fileHandler.copy(photofile, thumbFile);
		imageHandler.saveLowscaleImage(thumbFile, thumbMaxWidth, ext);
		
		photo.setFilename(filename);
		photo.setPathname(photoPathname);
		photo.setThumbnail(thumbPathname);

		return photo;
	}

	public Map<String, String> getMetaData(File file){
		HashMap<String, String> map = new HashMap<String, String>();
		try {
			final String sep1 = "\\]";
			final String sep2 = "\\-";
		
			String sepeartor[] = null;
			String key;
			String value;
	
			Metadata metadata;
			metadata = ImageMetadataReader.readMetadata(file);
			for (Directory directory : metadata.getDirectories()) {
				for (Tag tag : directory.getTags()) {
					sepeartor = tag.toString().split(sep1);
					sepeartor = sepeartor[1].split(sep2);
					key = sepeartor[0].trim();
					value = sepeartor[1].trim();
					map.put(key, value);
				}
			}
	
			map.put("Date", Formatter.toDate(map.get("Date/Time Original")));
			map.put("Time", Formatter.toTime(map.get("Date/Time Original")));
		} catch (ImageProcessingException | IOException e) {
			e.printStackTrace();
			return map;
		} 
		
		return map;
	}
}
