package com.cglee079.changoos.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.dao.ProjectDao;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.util.MyFileUtils;
import com.cglee079.changoos.util.AuthManager;
import com.cglee079.changoos.util.ImageManager;

@Service
public class ProjectService {
	@Autowired
	private ProjectDao projectDao;

	@Value("#{servletContext.getRealPath('/')}")
	private String realPath;

	public List<ProjectVo> list(Map<String, Object> map) {
		return projectDao.list(map);
	}

	public int insert(ProjectVo project) {
		project.setContents(project.getContents());
		project.setDesc(project.getDesc());

		project.setHits(0);
		return projectDao.insert(project);
	}

	public boolean update(ProjectVo project) {
		return projectDao.update(project);
	}

	public boolean delete(int seq) {
		return projectDao.delete(seq);
	}

	public ProjectVo get(int seq) {
		return projectDao.get(seq);
	}

	public ProjectVo doView(List<Integer> isVisitProject, int seq) {
		ProjectVo project = projectDao.get(seq);
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

}
