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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.PhotoDao;
import com.cglee079.changoos.model.PhotoVo;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.MyFileUtils;
import com.cglee079.changoos.util.MyFilenameUtils;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;

@Service
public class PhotoService {
	@Value("#{servletContext.getRealPath('/')}")
	private String realPath;

	@Autowired
	private PhotoDao photoDao;
	
	@Value("#{location['temp.photo.dir.url']}")
	private String photoTempDir;
	
	@Value("#{location['photo.origin.dir.url']}")
	private String photoOriginDir;
	
	@Value("#{location['photo.thumb.dir.url']}")
	private String photoThumbDir;
	
	@Value("#{constant['photo.origin.max.width']}")
	private int originMaxWidth;
	
	@Value("#{constant['photo.thumb.max.width']}")
	private int thumbMaxWidth;
	
	
	public PhotoVo get(int seq) {
		return photoDao.get(seq);
	}

	public List<PhotoVo> list(Map<String, Object> map) {
		return photoDao.list(map);
	}

	public List<Integer> seqs() {
		return photoDao.seqs();
	}
	
	public boolean insert(PhotoVo photo) throws IllegalStateException, ImageProcessingException, MetadataException, IOException {
		photo.setLikeCnt(0);

		MyFileUtils myFileUtils = MyFileUtils.getInstance();

		// TEMP 폴더에서, 사진 파일 옮기기
		String photoFilePath = realPath + photoTempDir + photo.getPathname();
		String movedPhotoPath = realPath + photoOriginDir + photo.getPathname();
		myFileUtils.move(photoFilePath, movedPhotoPath);

		// TEMP 폴더에서, 스냅샷 파일 옮기기
		String snapshotFilePath = realPath + photoTempDir + photo.getThumbnail();
		String movedSnapshtFilePath = realPath + photoThumbDir + photo.getThumbnail();
		myFileUtils.move(snapshotFilePath, movedSnapshtFilePath);
		
		myFileUtils.emptyDir(realPath + photoTempDir);

		boolean result = photoDao.insert(photo);
		return result;
	}

	@Transactional
	public boolean update(PhotoVo photo)
			throws IllegalStateException, ImageProcessingException, MetadataException, IOException {
		// DB에 저장된 사진정보와 새로 수정된 사진 정보를 비교한다
		// 다르다면, 사진을 바꿨으므로, 기존 사진을 삭제한다.
		PhotoVo savedPhoto = photoDao.get(photo.getSeq());
		if (!savedPhoto.getPathname().equals(photo.getPathname())) {
			MyFileUtils myFileUtils = MyFileUtils.getInstance();

			// 기존에 수정이전 저장된 사진, 스냅샷 파일 삭제
			myFileUtils.delete(realPath + photoOriginDir + savedPhoto.getPathname());
			myFileUtils.delete(realPath + photoThumbDir + savedPhoto.getThumbnail());

			// TEMP 폴더에서, 사진 파일 옮기기
			String photoFilePath = realPath + photoTempDir + photo.getPathname();
			String movedPhotoPath = realPath + photoOriginDir + photo.getPathname();
			myFileUtils.move(photoFilePath, movedPhotoPath);

			// TEMP 폴더에서, 스냅샷 파일 옮기기
			String snapshotFilePath = realPath + photoTempDir + photo.getThumbnail();
			String movedSnapshtFilePath = realPath + photoThumbDir + photo.getThumbnail();
			myFileUtils.move(snapshotFilePath, movedSnapshtFilePath);
			
			myFileUtils.emptyDir(realPath + photoTempDir);
		}

		
		boolean result = photoDao.update(photo);
		return result;
	}

	@Transactional
	public boolean delete(int seq) {
		PhotoVo photo = photoDao.get(seq);
		// 기존에 수정이전 저장된 사진, 스냅샷 파일 삭제
		MyFileUtils myFileUtils = MyFileUtils.getInstance();

		myFileUtils.delete(realPath + photoThumbDir + photo.getThumbnail());
		myFileUtils.delete(realPath + photoOriginDir + photo.getPathname());
		boolean result = photoDao.delete(seq);
		return result;
	}

	@Transactional
	public int doLike(Map<Integer, Boolean> likePhotos, int seq, boolean like) {
		PhotoVo photo = photoDao.get(seq);

		int likeCnt = -1;
		if (like) {
			likePhotos.put(seq, true);
			likeCnt = photo.getLikeCnt() + 1;
		} else {
			likePhotos.put(seq, false);
			likeCnt = photo.getLikeCnt() - 1;
		}

		photo.setLikeCnt(likeCnt);
		photoDao.update(photo);

		return likeCnt;
	}

	public PhotoVo savePhoto(MultipartFile multipartFile)
			throws MetadataException, ImageProcessingException, IOException {
		PhotoVo photo = new PhotoVo();

		String filename = multipartFile.getOriginalFilename();
		String ext = MyFilenameUtils.getExt(filename);
		String pathname = MyFilenameUtils.getRandomImagename(ext);
		String photoPathname = "PHOTO." + pathname;
		String thumbPathname = "PHOTO.THUMB." + pathname;
		ImageManager imageManager = ImageManager.getInstance();

		// 사진 저장
		File photofile = new File(realPath + photoTempDir, photoPathname);
		multipartFile.transferTo(photofile);

		// 메타데이터 가져오기
		HashMap<String, String> metadata = this.getMetaData(photofile);
		photo.setDate(metadata.get("Date"));
		photo.setTime(metadata.get("Time"));
		photo.setDevice(metadata.get("Model"));

		// 저장된 사진 축소 및 회전
		int orientation = imageManager.getOrientation(photofile);
		BufferedImage photoImg = imageManager.getLowScaledImage(photofile, originMaxWidth, ext);
		if (orientation != 1) {
			photoImg = imageManager.rotateImageForMobile(photoImg, orientation);
		}
		ImageIO.write(photoImg, ext, photofile);

		// 저장된 사진으로, 사진 스냅샷 만들기
		BufferedImage shapshtImg = imageManager.getLowScaledImage(photofile, thumbMaxWidth, ext);
		File snapshtfile = new File(realPath + photoTempDir, thumbPathname);
		ImageIO.write(shapshtImg, ext, snapshtfile);

		photo.setFilename(filename);
		photo.setPathname(photoPathname);
		photo.setThumbnail(thumbPathname);

		return photo;
	}

	private HashMap<String, String> getMetaData(File file) throws ImageProcessingException, IOException {
		final String sep1 = "\\]";
		final String sep2 = "\\-";
		HashMap<String, String> map = new HashMap<String, String>();
		String sepeartor[] = null;
		String key;
		String value;

		Metadata metadata = ImageMetadataReader.readMetadata(file);
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
		return map;
	}
}
