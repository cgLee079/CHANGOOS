package com.cglee079.changoos.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.dao.StudyDao;
import com.cglee079.changoos.dao.StudyFileDao;
import com.cglee079.changoos.dao.StudyImageDao;
import com.cglee079.changoos.model.FileVo;
import com.cglee079.changoos.model.ImageVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.util.AuthManager;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.MyFileUtils;
import com.cglee079.changoos.util.PathHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class StudyService {
	
	@Autowired
	StudyDao studyDao;
	
	@Autowired
	StudyFileDao studyFileDao;
	
	@Autowired
	StudyImageDao studyImageDao;

	@Value("#{servletContext.getRealPath('/')}")
	private String realPath;

	@Value("#{location['file.temp.dir.url']}")
	private String fileTempDir;
	
	@Value("#{location['file.blog.dir.url']}")
	private String fileDir;
	
	@Value("#{location['image.temp.dir.url']}")
	private String imageTempDir;
	
	@Value("#{location['image.project.dir.url']}")
	private String imageDir;
	
	public int count(Map<String, Object> params) {
		return studyDao.count(params);
	}
	
	@Transactional
	public StudyVo get(int seq) {
		StudyVo study = studyDao.get(seq);
		study.setImages(studyImageDao.list(seq));
		study.setFiles(studyFileDao.list(seq));
		
		if(study.getContents() != null) {
			study.setContents(study.getContents().replace("&", "&amp;"));
		}
		
		return study;
	}
	
	
	@Transactional
	public StudyVo doView(List<Integer> isVisitStudies, int seq) {
		StudyVo study = studyDao.get(seq);
		study.setImages(studyImageDao.list(seq));
		study.setFiles(studyFileDao.list(seq));

		if (!isVisitStudies.contains(seq) && !AuthManager.isAdmin()) {
			isVisitStudies.add(seq);
			study.setHits(study.getHits() + 1);
			studyDao.update(study);
		}
		
		return study;
	}
	

	public StudyVo getBefore(int seq, String category) {
		return studyDao.getBefore(seq, category);
	}

	public StudyVo getAfter(int seq, String category) {
		return studyDao.getAfter(seq, category);
	}
	
	public List<String> getCategories() {
		return studyDao.getCategories();
	}

	public List<StudyVo> list(Map<String, Object> map) {
		return studyDao.list(map);
	}

	@Transactional(rollbackFor = {IllegalStateException.class, IOException.class })
	public int insert(StudyVo study, String imageValues, String fileValues) throws IllegalStateException, IOException {
		String contents = PathHandler.changeImagePath(study.getContents(), imageTempDir, imageDir);
		study.setContents(contents);
		study.setDate(Formatter.toDate(new Date()));
		study.setHits(0);
		
		int seq = studyDao.insert(study);
		List<ImageVo> images = new ObjectMapper().readValue(imageValues, new TypeReference<List<ImageVo>>(){});
		this.saveImages(seq, images);
		
		List<FileVo> files = new ObjectMapper().readValue(fileValues, new TypeReference<List<FileVo>>(){});
		this.saveFiles(seq, files);
		
		return seq;
	}


	@Transactional(rollbackFor = {IllegalStateException.class, IOException.class })
	public boolean update(StudyVo study, String contentImages, String fileValues) throws IllegalStateException, IOException {
		String contents = PathHandler.changeImagePath(study.getContents(), imageTempDir, imageDir);
		study.setContents(contents);
		
		boolean result = studyDao.update(study);
		
		int seq = study.getSeq();
		
		List<ImageVo> images = new ObjectMapper().readValue(contentImages, new TypeReference<List<ImageVo>>(){});
		this.saveImages(seq, images);
		
		List<FileVo> files = new ObjectMapper().readValue(fileValues, new TypeReference<List<FileVo>>(){});
		this.saveFiles(seq, files);
		
		return result;
	}
	
	@Transactional
	public boolean delete(int seq) {
		List<FileVo> files = studyFileDao.list(seq);
		List<ImageVo> images = studyImageDao.list(seq);
		MyFileUtils fileUtils = MyFileUtils.getInstance();
		
		boolean result = studyDao.delete(seq); //CASECADE
		if(result) {
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
	

	public List<StudyVo> paging(Map<String, Object> params) {
		int page = Integer.parseInt((String) params.get("page"));
		int perPgLine = Integer.parseInt((String) params.get("perPgLine"));
		int startRow = (page - 1) * perPgLine;
		params.put("startRow", startRow);

		List<StudyVo> studies = studyDao.list(params);
		StudyVo study = null;
		String contents = null;
		String newContents = "";
		Document doc = null;
		Elements els = null;
		for (int i = 0; i < studies.size(); i++) {
			study = studies.get(i);

			// 내용중 텍스트만 뽑기
			contents = study.getContents();
			newContents = "";
			doc = Jsoup.parse(contents);
			els = doc.select("*");
			if (els.eachText().size() > 0) {
				newContents = els.eachText().get(0);
			}

			newContents.replace("\n", " ");
			study.setContents(newContents);
		}

		return studies;
	}

	/***
	 * 내용 중 이미지 첨부 관련
	 ***/
	private void saveImages(int studySeq, List<ImageVo> images) {
		ImageVo image;
		MyFileUtils fileUtils = MyFileUtils.getInstance();
		
		for (int i = 0; i < images.size(); i++) {
			image = images.get(i);
			image.setBoardSeq(studySeq);
			switch(image.getStatus()) {
			case "NEW" : //새롭게 추가된 이미지
				if(studyImageDao.insert(image)) {
					//임시폴더에서 본 폴더로 이동
					File existFile  = new File(realPath + imageTempDir, image.getPathname());
					File newFile	= new File(realPath + imageDir, image.getPathname());
					fileUtils.move(existFile, newFile);
				}
				break;
			case "REMOVE" : //기존에 있던 이미지 중, 삭제된 이미지
				if(studyImageDao.delete(image.getSeq())) {
					fileUtils.delete(realPath + imageDir, image.getPathname());
				}
				break;
			}
		}
		
		//업로드 파일로 이동했음에도 불구하고, 남아있는 TEMP 폴더의 이미지 파일을 삭제.
		//즉, 이전에 글 작성 중 작성을 취소한 경우 업로드가 되었던 이미지파일들이 삭제됨.
		fileUtils.emptyDir(realPath + imageTempDir);
		
	}
	
	
	/***
	 *  파일 첨부 관련 
	 **/
	private void saveFiles(int seq, List<FileVo> files) throws IllegalStateException, IOException {
		MyFileUtils fileUtils = MyFileUtils.getInstance();
		
		FileVo file;
		for (int i = 0; i < files.size(); i++) {
			file = files.get(i);
			file.setBoardSeq(seq);
			switch(file.getStatus()) {
			case "NEW" : //새롭게 추가된 파일
				if(studyFileDao.insert(file)) {
					//임시폴더에서 본 폴더로 이동
					File existFile  = new File(realPath + fileTempDir, file.getPathname());
					File newFile	= new File(realPath + fileDir, file.getPathname());
					fileUtils.move(existFile, newFile);
				}
				break;
			case "REMOVE" : //기존에 있던 이미지 중, 삭제된 이미지
				if(studyFileDao.delete(file.getSeq())) {
					fileUtils.delete(realPath + fileDir, file.getPathname());
				}
				break;
			}
		}
		
		fileUtils.emptyDir(realPath + fileTempDir);
	}
	


}
