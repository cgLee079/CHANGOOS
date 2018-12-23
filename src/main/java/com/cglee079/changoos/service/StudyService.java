package com.cglee079.changoos.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.dao.StudyDao;
import com.cglee079.changoos.dao.StudyFileDao;
import com.cglee079.changoos.dao.StudyImageDao;
import com.cglee079.changoos.model.StudyFileVo;
import com.cglee079.changoos.model.StudyImageVo;
import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.util.AuthManager;
import com.cglee079.changoos.util.ContentImageManager;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.MyFileUtils;
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
	public int insert(StudyVo study, String contentImages, List<MultipartFile> files) throws IllegalStateException, IOException {
		List<StudyImageVo> images = new ObjectMapper().readValue(contentImages, new TypeReference<List<StudyImageVo>>(){});
		
		String contents = ContentImageManager.moveToSavePath(study.getContents(), Path.STUDY_CONTENTS_PATH);
		study.setContents(contents);
		study.setDate(Formatter.toDate(new Date()));
		study.setHits(0);
		
		int seq = studyDao.insert(study);
		this.saveImages(seq, images);
		this.saveFiles(seq, files);
		
		return seq;
	}

	@Transactional
	public boolean delete(int seq) {
		boolean result = studyDao.delete(seq);
		
		List<StudyFileVo> files = studyFileDao.list(seq);
		int fileLength = files.size();
		for (int i = 0; i < fileLength; i++) {
			this.deleteFile(files.get(i));
		}
		
		return result;
	}

	@Transactional(rollbackFor = {IllegalStateException.class, IOException.class })
	public boolean update(StudyVo study, String contentImages, List<MultipartFile> files) throws IllegalStateException, IOException {
		List<StudyImageVo> images = new ObjectMapper().readValue(contentImages, new TypeReference<List<StudyImageVo>>(){});
		
		String contents = ContentImageManager.moveToSavePath(study.getContents(), Path.STUDY_CONTENTS_PATH);
		study.setContents(contents);
		
		int seq = study.getSeq();
		boolean result = studyDao.update(study);
		
		this.saveImages(seq, images);
		this.saveFiles(seq, files);
		
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

	@Transactional
	public StudyVo doView(List<Integer> isVisitStudies, int seq) {
		StudyVo study = studyDao.get(seq);
		study.setFiles(studyFileDao.list(seq));
		study.setImages(studyImageDao.list(seq));

		if (!isVisitStudies.contains(seq) && !AuthManager.isAdmin()) {
			isVisitStudies.add(seq);
			study.setHits(study.getHits() + 1);
			studyDao.update(study);
		}
		return study;
	}
	
	/***
	 * 내용 중 사진 첨부 관련
	 */
	private void saveImages(int studySeq, List<StudyImageVo> images) {
		StudyImageVo image;
		for (int i = 0; i < images.size(); i++) {
			image = images.get(i);
			image.setStudySeq(studySeq);
			switch(image.getStatus()) {
			case "NEW" : 
				image.setPath(Path.STUDY_CONTENTS_PATH);
				studyImageDao.insert(image);
				break;
			case "UNNEW" : 
				MyFileUtils.delete(realPath + image.getPath(), image.getPathname());
				break;
			case "REMOVE" : 
				if(studyImageDao.delete(image.getSeq())) {
					MyFileUtils.delete(realPath + image.getPath(), image.getPathname());
				}
				break;
			}
			
		}
	}
	
	
	/***
	 * 첨부 파일 관련 함수
	 ***/
	public StudyFileVo getFile(String pathNm) {
		return studyFileDao.get(pathNm);
	}
	
	public boolean deleteFile(int fileSeq) {
		StudyFileVo studyFile = studyFileDao.get(fileSeq);
		return this.deleteFile(studyFile);
	}
	
	private boolean deleteFile(StudyFileVo studyFile) {
		if (studyFileDao.delete(studyFile.getSeq())) {
			if (MyFileUtils.delete(realPath + Path.STUDY_FILE_PATH, studyFile.getPathNm())) {
				return true;
			}
		}
		return false;
	}

	private void saveFiles(int seq, List<MultipartFile> files) throws IllegalStateException, IOException {
		File file = null;
		MultipartFile multipartFile = null;
		StudyFileVo studyFile = null;
		String realNm = null;
		String pathNm = null;
		long size = -1;
		int length = files.size();
		for (int i = 0; i < length; i++) {
			multipartFile = files.get(i);
			realNm = MyFileUtils.sanitizeRealFilename(multipartFile.getOriginalFilename());
			pathNm = MyFileUtils.getRandomFilename(MyFileUtils.getExt(realNm));
			size = multipartFile.getSize();

			if (size > 0) {
				file = new File(realPath + Path.STUDY_FILE_PATH, pathNm);
				multipartFile.transferTo(file);

				studyFile = new StudyFileVo();
				studyFile.setPathNm(pathNm);
				studyFile.setRealNm(realNm);
				studyFile.setSize(size);
				studyFile.setStudySeq(seq);
				studyFileDao.insert(studyFile);
			}
		}
	}


}
