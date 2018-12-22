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
import com.cglee079.changoos.model.ProjectFileVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.util.AuthManager;
import com.cglee079.changoos.util.ContentImageManager;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.MyFileUtils;

@Service
public class ProjectService {
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private ProjectFileDao projectFileDao;

	@Value("#{servletContext.getRealPath('/')}")
	private String realPath;

	public List<ProjectVo> list(Map<String, Object> map) {
		return projectDao.list(map);
	}

	@Transactional(rollbackFor = {IllegalStateException.class, IOException.class})
	public int insert(ProjectVo project, MultipartFile snapshtFile, List<MultipartFile> files) throws IllegalStateException, IOException {
		String snapshtPath = this.saveSnapsht(project, snapshtFile);
		project.setSnapsht(snapshtPath);

		String contents = ContentImageManager.moveToSavePath(project.getContents(), Path.PROJECT_CONTENTS_PATH);
		project.setContents(contents);
		project.setDesc(project.getDesc());
		project.setHits(0);
		
		int seq = projectDao.insert(project);
		this.saveFiles(project, files);
		
		return seq;
	}

	@Transactional(rollbackFor = {IllegalStateException.class, IOException.class})
	public boolean update(ProjectVo project, MultipartFile snapshtFile, List<MultipartFile> files) throws IllegalStateException, IOException {
		String snapshtPath = this.saveSnapsht(project, snapshtFile);
		project.setSnapsht(snapshtPath);

		String contents = ContentImageManager.moveToSavePath(project.getContents(), Path.PROJECT_CONTENTS_PATH);
		project.setContents(contents);
		
		boolean result = projectDao.update(project);
		this.saveFiles(project, files);
		
		return result;
	}

	@Transactional
	public boolean delete(int seq) {
		boolean result = projectDao.delete(seq);
		
		List<ProjectFileVo> files = projectFileDao.list(seq);
		int fileLength = files.size();
		for (int i = 0; i < fileLength; i++) {
			this.deleteFile(files.get(i));
		}
		return result;
	}

	@Transactional
	public ProjectVo get(int seq) {
		ProjectVo project = projectDao.get(seq);
		List<ProjectFileVo> files = projectFileDao.list(seq);
		project.setFiles(files);
		
		if (project.getContents() != null) {
			String contents = ContentImageManager.copyToTempPath(project.getContents(), Path.PROJECT_CONTENTS_PATH);
			project.setContents(contents.replace("&", "&amp;"));
		}
		
		return project;
	}

	@Transactional
	public ProjectVo doView(List<Integer> isVisitProject, int seq) {
		ProjectVo project = projectDao.get(seq);
		List<ProjectFileVo> files = projectFileDao.list(seq);
		project.setFiles(files);
		
		if (!isVisitProject.contains(seq) && !AuthManager.isAdmin()) {
			isVisitProject.add(seq);
			project.setHits(project.getHits() + 1);
			projectDao.update(project);
		}
		return project;
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

			filename += MyFileUtils.sanitizeRealFilename(snapshtFile.getOriginalFilename());
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

	public void removeSnapshtFile(ProjectVo project) {
		MyFileUtils.delete(realPath + project.getSnapsht());
	}
	
	/**
	 * 파일 첨부 관련 메소드
	 */
	public ProjectFileVo getFile(String pathNm) {
		return projectFileDao.get(pathNm);
	}
	
	public boolean deleteFile(int fileSeq) {
		ProjectFileVo projectFile = projectFileDao.get(fileSeq);
		return this.deleteFile(projectFile);
	}
	
	private boolean deleteFile(ProjectFileVo projectFile) {
		if (projectFileDao.delete(projectFile.getSeq())) {
			if(MyFileUtils.delete(realPath + Path.PROJECT_FILE_PATH, projectFile.getPathNm())){
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
		String realNm = null;
		String pathNm = null;
		long size = -1;
		int length = files.size();
		
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			realNm = MyFileUtils.sanitizeRealFilename(multipartFile.getOriginalFilename());
			pathNm = MyFileUtils.getRandomFilename(MyFileUtils.getExt(realNm));
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(realPath + Path.PROJECT_FILE_PATH, pathNm);
				multipartFile.transferTo(file);
				
				projectFile = new ProjectFileVo();
				projectFile.setPathNm(pathNm);
				projectFile.setRealNm(realNm);
				projectFile.setSize(size);
				projectFile.setProjectSeq(seq);
				projectFileDao.insert(projectFile);
			}
		}
	}


}
