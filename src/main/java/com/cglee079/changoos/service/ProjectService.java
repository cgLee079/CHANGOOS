package com.cglee079.changoos.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.ProjectDao;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.util.AuthManager;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.MyFileUtils;
import com.cglee079.changoos.util.MyFilenameUtils;

@Service
public class ProjectService {
	@Autowired private BoardImageService boardImageService;
	@Autowired private BoardFileService boardFileService;
	@Autowired private ProjectDao projectDao;
	
	@Value("#{servletContext.getRealPath('/')}") private String realPath;
	
	@Value("#{location['project.file.dir.url']}") 	private String fileDir;
	@Value("#{location['project.image.dir.url']}")	private String imageDir;
	@Value("#{location['project.thumb.dir.url']}") 	private String thumbDir;
	
	@Value("#{tb['project.file.tb.name']}") private String fileTB;
	@Value("#{tb['project.image.tb.name']}") private String imageTB;
	
	
	@Transactional
	public ProjectVo get(int seq) {
		ProjectVo project = projectDao.get(seq);
		project.setImages(boardImageService.list(imageTB, seq));
		project.setFiles(boardFileService.list(fileTB, seq));
		
		String contents = project.getContents();
		if (contents != null) {
			project.setContents(contents.replace("&", "&amp;"));
		}
		
		return project;
	}

	@Transactional
	public ProjectVo doView(List<Integer> isVisitProject, int seq) {
		ProjectVo project = projectDao.get(seq);
		project.setImages(boardImageService.list(imageTB, seq));
		project.setFiles(boardFileService.list(fileTB, seq));
		
		if (!isVisitProject.contains(seq) && !AuthManager.isAdmin()) {
			isVisitProject.add(seq);
			project.setHits(project.getHits() + 1);
			projectDao.update(project);
		}
		return project;
	}
	
	public List<ProjectVo> list(Map<String, Object> map) {
		return projectDao.list(map);
	}

	@Transactional(rollbackFor = {IllegalStateException.class, IOException.class})
	public int insert(ProjectVo project, MultipartFile thumbnailFile, String imageValues, String fileValues) throws IllegalStateException, IOException {
		String thumbnail = this.saveThumbnail(project, thumbnailFile);
		project.setThumbnail(thumbnail);
		project.setHits(0);
		
		int seq = projectDao.insert(project);
		
		boardFileService.insertFiles(fileTB, fileDir, seq, fileValues);
		
		String contents = boardImageService.insertImages(imageTB, imageDir, seq, project.getContents(), imageValues);
		project.setContents(contents);
		projectDao.update(project);
		
		return seq;
	}

	@Transactional(rollbackFor = {IllegalStateException.class, IOException.class})
	public boolean update(ProjectVo project, MultipartFile thumbnailFile, String imageValues, String fileValues) throws IllegalStateException, IOException {
		String thumbnail = this.saveThumbnail(project, thumbnailFile);
		project.setThumbnail(thumbnail);

		int seq = project.getSeq();
		boardFileService.insertFiles(fileTB, fileDir, seq, fileValues);
		
		String contents = boardImageService.insertImages(imageTB, imageDir, seq, project.getContents(), imageValues);
		project.setContents(contents);
		
		boolean result = projectDao.update(project);
		return result;
	}

	@Transactional
	public boolean delete(int seq) {
		ProjectVo project = projectDao.get(seq);
		List<BoardFileVo> files = boardFileService.list(fileTB, seq);
		List<BoardImageVo> images = boardImageService.list(imageTB, seq);
		
		boolean result = projectDao.delete(seq);
		if(result) {
			MyFileUtils fileUtils = MyFileUtils.getInstance();
			
			//스냅샷 삭제
			fileUtils.delete(realPath + thumbDir, project.getThumbnail());
			
			//첨부 파일 삭제
			for (int i = 0; i < files.size(); i++) {
				fileUtils.delete(realPath + fileDir, files.get(i).getPathname());
			}
			
			//첨부 이미지 삭제
			for (int i = 0; i < images.size(); i++) {
				fileUtils.delete(realPath + imageDir, images.get(i).getPathname());
			}
		}
		return result;
	}



	public ProjectVo getBefore(int seq) {
		return projectDao.getBefore(seq);
	}

	public ProjectVo getAfter(int seq) {
		return projectDao.getAfter(seq);
	}

	
	public String saveThumbnail(ProjectVo project, MultipartFile thumbnailFile) throws IllegalStateException, IOException {
		String filename = thumbnailFile.getOriginalFilename();
		String imgExt = MyFilenameUtils.getExt(filename);
		String pathname = null;
		
		if (thumbnailFile.getSize() != 0) {
			MyFileUtils fileUtils = MyFileUtils.getInstance();
			
			fileUtils.delete(realPath + thumbDir, project.getThumbnail());

			pathname = "PROJECT.THUMB." + MyFilenameUtils.getRandomImagename(imgExt);
			File file = new File(realPath + thumbDir, pathname);
			thumbnailFile.transferTo(file);
			
			if (!imgExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
				ImageManager imageManager = ImageManager.getInstance();
				BufferedImage image = imageManager.getLowScaledImage(file, 1080, imgExt);
				ImageIO.write(image, imgExt, file);
			}

		}

		return pathname;
	}

}
