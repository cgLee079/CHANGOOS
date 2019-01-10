package com.cglee079.changoos.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.ProjectDao;
import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.util.AuthManager;
import com.cglee079.changoos.util.FileHandler;
import com.cglee079.changoos.util.ImageHandler;
import com.cglee079.changoos.util.MyFilenameUtils;

@Service
public class ProjectService {
	@Autowired private BoardImageService boardImageService;
	@Autowired private BoardFileService boardFileService;
	@Autowired private ProjectDao projectDao;
	@Autowired private ImageHandler imageHandler;
	@Autowired private FileHandler fileHandler;
	
	@Value("#{servletContext.getRealPath('/')}") private String realPath;
	
	@Value("#{location['project.file.dir.url']}") 	private String fileDir;
	@Value("#{location['project.image.dir.url']}")	private String imageDir;
	@Value("#{location['project.thumb.dir.url']}") 	private String thumbDir;
	@Value("#{location['temp.thumb.dir.url']}")		private String tempThumbDir;
	
	@Value("#{db['project.file.tb.name']}") private String fileTB;
	@Value("#{db['project.image.tb.name']}") private String imageTB;
	
	@Value("#{constant['project.thumb.max.width']}")private int thumbMaxWidth;
	
	
	public ProjectVo get(int seq) {
		ProjectVo project = projectDao.get(seq);
		
		String contents = project.getContents();
		if (contents != null) {
			project.setContents(contents.replace("&", "&amp;"));
		}
		
		return project;
	}
	
	public ProjectVo getBefore(int seq) {
		return projectDao.getBefore(seq);
	}

	public ProjectVo getAfter(int seq) {
		return projectDao.getAfter(seq);
	}

	@Transactional
	public ProjectVo doView(Set<Integer> visitProjects, int seq) {
		ProjectVo project = projectDao.get(seq);
		
		if (!visitProjects.contains(seq) && !AuthManager.isAdmin()) {
			visitProjects.add(seq);
			project.setHits(project.getHits() + 1);
			projectDao.update(project);
		}
		return project;
	}
	
	public List<ProjectVo> list(Map<String, Object> map) {
		return projectDao.list(map);
	}

	@Transactional(rollbackFor = {IllegalStateException.class, IOException.class})
	public int insert(ProjectVo project, String imageValues, String fileValues) throws IllegalStateException, IOException {
		
		int seq = projectDao.insert(project);
		
		fileHandler.move(realPath + tempThumbDir + project.getThumbnail(), realPath + thumbDir + project.getThumbnail());
		
		boardFileService.insertFiles(fileTB, fileDir, seq, fileValues);
		
		String contents = boardImageService.insertImages(imageTB, imageDir, seq, project.getContents(), imageValues);
		project.setContents(contents);
		projectDao.update(project);
		
		return seq;
	}

	@Transactional(rollbackFor = {IllegalStateException.class, IOException.class})
	public boolean update(ProjectVo project, String imageValues, String fileValues) throws IllegalStateException, IOException {
		int seq = project.getSeq();
		
		ProjectVo savedProject = projectDao.get(seq);
		
		if(!project.getThumbnail().equals(savedProject.getThumbnail())) {
			fileHandler.delete(realPath + thumbDir + savedProject.getThumbnail());
		}
		
		fileHandler.move(realPath + tempThumbDir + project.getThumbnail(), realPath + thumbDir + project.getThumbnail());
		
		boardFileService.insertFiles(fileTB, fileDir, seq, fileValues);
		
		String contents = boardImageService.insertImages(imageTB, imageDir, seq, project.getContents(), imageValues);
		project.setContents(contents);
		
		boolean result = projectDao.update(project);
		return result;
	}

	@Transactional
	public boolean delete(int seq) {
		ProjectVo project = projectDao.get(seq);
		List<BoardFileVo> files = project.getFiles();
		List<BoardImageVo> images = project.getImages();
		
		boolean result = projectDao.delete(seq);
		if(result) {
			//스냅샷 삭제
			fileHandler.delete(realPath + thumbDir, project.getThumbnail());
			
			//첨부 파일 삭제
			for (int i = 0; i < files.size(); i++) {
				fileHandler.delete(realPath + fileDir, files.get(i).getPathname());
			}
			
			//첨부 이미지 삭제
			for (int i = 0; i < images.size(); i++) {
				fileHandler.delete(realPath + imageDir, images.get(i).getPathname());
			}
		}
		return result;
	}


	public String saveThumbnail(MultipartFile thumbnailFile) {
		String pathname = null;
		
		if(thumbnailFile.getSize() > 0){
			String filename = thumbnailFile.getOriginalFilename();
			String imgExt = MyFilenameUtils.getExt(filename);
			
			pathname = "PROJECT.THUMB." + MyFilenameUtils.getRandomImagename(imgExt);
			File file = fileHandler.save(realPath + tempThumbDir +  pathname, thumbnailFile);
			
			imageHandler.saveLowscaleImage(file, thumbMaxWidth, imgExt);
			
		} 
		
		return pathname;
	}
	
}
