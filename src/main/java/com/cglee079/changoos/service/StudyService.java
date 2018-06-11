package com.cglee079.changoos.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.StudyDao;
import com.cglee079.changoos.dao.StudyFileDao;
import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.model.StudyFileVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.util.Formatter;

@Service
public class StudyService{
	
	@Autowired
	StudyDao studyDao;
	
	public List<StudyVo> list(Map<String, Object> map){
		return studyDao.list(map);
	}
	
	public List<StudyVo> paging(Map<String, Object> params){
		int page = Integer.parseInt((String)params.get("page"));
		int perPgLine = Integer.parseInt((String)params.get("perPgLine"));
		int startRow = (page - 1) * perPgLine;
		
		params.put("startRow", startRow);
		return studyDao.list(params);
	}

	public int count(Map<String, Object> params) {
		return studyDao.count(params);
	}

	public StudyVo get(int seq) {
		return studyDao.get(seq);
	}
	
	public StudyVo doView(List<Integer> isVisitStudies, int seq) {
		StudyVo study = studyDao.get(seq);
		
		if(!isVisitStudies.contains(seq)) {
			isVisitStudies.add(seq);
			study.setHits(study.getHits() + 1);
			studyDao.update(study);
		}
		return study;
	}

	public int insert(StudyVo study) {
		study.setDate(Formatter.toDate(new Date()));
		study.setHits(0);
		return studyDao.insert(study);
	}

	public boolean delete(int seq) {
		return studyDao.delete(seq);
	}
	
	public List<String> getContentImgPath(int seq, String path){
		List<String> imgPaths = new ArrayList<String>();
		StudyVo study = studyDao.get(seq);
		String content = study.getContents();
		
		int stIndex = 0;
		int endIndex= 0;
		
		if(content != null){
			while(true){
				stIndex = content.indexOf(path, endIndex);
				endIndex = content.indexOf("\"", stIndex);
				
				if(stIndex == -1){ break;}
				if(endIndex == -1){ break;}
				
				imgPaths.add(content.substring(stIndex, endIndex));
			}
		}
		
		return imgPaths;
	}

	public boolean update(StudyVo study) {
		return studyDao.update(study);
	}

	public StudyVo getBefore(int seq, String sect) {
		return studyDao.getBefore(seq, sect);
	}

	public StudyVo getAfter(int seq, String sect) {
		return studyDao.getAfter(seq, sect);
	}

	public List<String> getSects() {
		return studyDao.getSects();
	}
}
