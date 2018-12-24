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

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.dao.ProjectDao;
import com.cglee079.changoos.dao.ProjectFileDao;
import com.cglee079.changoos.dao.ProjectImageDao;
import com.cglee079.changoos.model.ProjectFileVo;
import com.cglee079.changoos.model.ProjectImageVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.util.AuthManager;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.MyFileUtils;
import com.cglee079.changoos.util.MyFilenameUtils;
import com.cglee079.changoos.util.PathHandler;
import com.cglee079.changoos.util.TempFolderManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProjectService {
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private ProjectFileDao projectFileDao;
	
	@Autowired
	ProjectImageDao projectImageDao;
	
	@Value("#{servletContext.getRealPath('/')}")
	private String realPath;

	@Transactional
	public ProjectVo get(int seq) {
		ProjectVo project = projectDao.get(seq);
		project.setImages(projectImageDao.list(seq));
		project.setFiles(projectFileDao.list(seq));
		
		String contents = project.getContents();
		if (contents != null) {
			project.setContents(contents.replace("&", "&amp;"));
		}
		
		return project;
	}

	@Transactional
	public ProjectVo doView(List<Integer> isVisitProject, int seq) {
		ProjectVo project = this.get(seq);
		
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
	public int insert(ProjectVo project, MultipartFile snapshtFile, String imageValues, List<MultipartFile> files) throws IllegalStateException, IOException {
		String snapshtPath = this.saveSnapsht(project, snapshtFile);
		project.setSnapsht(snapshtPath);

		String contents = PathHandler.changeImagePath(project.getContents(), Path.TEMP_PATH, Path.PROJECT_IMAGE_PATH);
		project.setContents(contents);
		project.setHits(0);
		
		int seq = projectDao.insert(project);
		List<ProjectImageVo> images = new ObjectMapper().readValue(imageValues, new TypeReference<List<ProjectImageVo>>(){});
		this.saveImages(seq, images);
		this.saveFiles(project, files);
		
		return seq;
	}

	@Transactional(rollbackFor = {IllegalStateException.class, IOException.class})
	public boolean update(ProjectVo project, MultipartFile snapshtFile, String imageValues, List<MultipartFile> files) throws IllegalStateException, IOException {
		String snapshtPath = this.saveSnapsht(project, snapshtFile);
		project.setSnapsht(snapshtPath);

		String contents = PathHandler.changeImagePath(project.getContents(), Path.TEMP_PATH, Path.PROJECT_IMAGE_PATH);
		project.setContents(contents);
		
		boolean result = projectDao.update(project);
		List<ProjectImageVo> images = new ObjectMapper().readValue(imageValues, new TypeReference<List<ProjectImageVo>>(){});
		this.saveImages(project.getSeq(), images);
		this.saveFiles(project, files);
		
		return result;
	}

	@Transactional
	public boolean delete(int seq) {
		ProjectVo project = projectDao.get(seq);
		List<ProjectFileVo> files = projectFileDao.list(seq);
		List<ProjectImageVo> images = projectImageDao.list(seq);
		
		boolean result = projectDao.delete(seq);
		if(result) {
			//스냅샷 삭제
			MyFileUtils.delete(realPath + project.getSnapsht());
			
			//첨부 파일 삭제
			for (int i = 0; i < files.size(); i++) {
				MyFileUtils.delete(realPath + Path.PROJECT_FILE_PATH, files.get(i).getPathname());
			}
			
			//첨부 이미지 삭제
			for (int i = 0; i < images.size(); i++) {
				MyFileUtils.delete(realPath + Path.PROJECT_IMAGE_PATH, images.get(i).getPathname());
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

	
	public String saveSnapsht(ProjectVo project, MultipartFile snapshtFile) throws IllegalStateException, IOException {
		String filename = "snapshot_" + project.getTitle() + "_";
		String imgExt = null;
		String path = project.getSnapsht();
		if (snapshtFile.getSize() != 0) {
			MyFileUtils.delete(realPath + project.getSnapsht());

			filename += MyFilenameUtils.sanitizeRealFilename(snapshtFile.getOriginalFilename());
			imgExt = ImageManager.getExt(filename);
			File file = new File(realPath + Path.PROJECT_SNAPSHT_PATH + filename);
			snapshtFile.transferTo(file);
			if (!imgExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
				BufferedImage image = ImageManager.getLowScaledImage(file, 1080, imgExt);
				ImageIO.write(image, imgExt, file);
			}

			path = Path.PROJECT_SNAPSHT_PATH + filename;
		}

		return path;
	}

	/***
	 * 내용 중 이미지 첨부 관련
	 ***/
	private void saveImages(int projectSeq, List<ProjectImageVo> images) {
		ProjectImageVo image;
		for (int i = 0; i < images.size(); i++) {
			image = images.get(i);
			image.setProjectSeq(projectSeq);
			switch(image.getStatus()) {
			case "NEW" : //새롭게 추가된 이미지
				String path = image.getPath();
				String movedPath = Path.PROJECT_IMAGE_PATH;
				image.setPath(movedPath);
				
				if(projectImageDao.insert(image)) {
					//임시폴더에서 본 폴더로 이동
					File existFile  = new File(realPath + path, image.getPathname());
					File newFile	= new File(realPath + movedPath, image.getPathname());
					MyFileUtils.move(existFile, newFile);
				}
				break;
			case "REMOVE" : //기존에 있던 이미지 중, 삭제된 이미지
				if(projectImageDao.delete(image.getSeq())) {
					MyFileUtils.delete(realPath + image.getPath(), image.getPathname());
				}
				break;
			}
		}
		
		//업로드 파일로 이동했음에도 불구하고, 남아있는 TEMP 폴더의 이미지 파일을 삭제.
		//즉, 이전에 글 작성 중 작성을 취소한 경우 업로드가 되었던 이미지파일들이 삭제됨.
		TempFolderManager.getInstance().removeAll();
	}
	
	/**
	 * 파일 첨부 관련 메소드
	 */
	public ProjectFileVo getFile(String pathNm) {
		return projectFileDao.get(pathNm);
	}
	
	public boolean deleteFile(int fileSeq) {
		ProjectFileVo projectFile = projectFileDao.get(fileSeq);
		if (projectFileDao.delete(projectFile.getSeq())) {
			if(MyFileUtils.delete(realPath + Path.PROJECT_FILE_PATH, projectFile.getPathname())){
				return true;
			}
		}
		return false;
	}
	
	private void saveFiles(ProjectVo project, List<MultipartFile> files) throws IllegalStateException, IOException {
		int seq = project.getSeq();
		
		MultipartFile multipartFile = null;
		ProjectFileVo projectFile = null;
		File file = null;
		String filename = null;
		String pathname = null;
		long size = -1;
		int length = files.size();
		
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			filename = MyFilenameUtils.sanitizeRealFilename(multipartFile.getOriginalFilename());
			pathname = MyFilenameUtils.getRandomFilename(MyFilenameUtils.getExt(filename));
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(realPath + Path.PROJECT_FILE_PATH, pathname);
				multipartFile.transferTo(file);
				
				projectFile = new ProjectFileVo();
				projectFile.setPathname(pathname);
				projectFile.setFilename(filename);
				projectFile.setSize(size);
				projectFile.setProjectSeq(seq);
				projectFileDao.insert(projectFile);
			}
		}
	}


}
