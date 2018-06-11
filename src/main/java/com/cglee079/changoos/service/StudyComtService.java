package com.cglee079.changoos.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.StudyComtDao;
import com.cglee079.changoos.model.StudyComtVo;

@Service
public class StudyComtService{
	
	@Autowired
	StudyComtDao bcomtDao;

	public List<StudyComtVo> paging(int studySeq, int page, int perPgLine) {
		int startRow = (page - 1) * perPgLine;
		return bcomtDao.list(studySeq, startRow, perPgLine);
	}

	public int count(int studySeq) {
		return bcomtDao.count(studySeq);
	}

	public boolean insert(StudyComtVo comt) {
		String date = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		comt.setDate(date);
		return bcomtDao.insert(comt);
	}

	public boolean delete(int seq, String password, boolean isAdmin) {
		StudyComtVo comtVo = bcomtDao.get(seq);
		if(comtVo.getPassword().equals(password) || isAdmin){
			return bcomtDao.delete(seq);			
		} else {
			return false;
		}
	}
	

	public boolean checkPwd(int seq, String password, boolean isAdmin) {
		StudyComtVo comtVo = bcomtDao.get(seq);
		if(comtVo.getPassword().equals(password) || isAdmin){
			return true;			
		} else {
			return false;
		}
	}

	public boolean update(int seq, String contents) {
		return bcomtDao.update(seq, contents);
	}
}
