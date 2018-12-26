package com.cglee079.changoos.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.StudyComtDao;
import com.cglee079.changoos.model.ComtVo;

@Service
public class StudyComtService{
	
	@Autowired
	StudyComtDao scomtDao;

	public List<ComtVo> paging(int studySeq, int page, int perPgLine) {
		int startRow = (page - 1) * perPgLine;
		return scomtDao.list(studySeq, startRow, perPgLine);
	}

	public int count(int studySeq) {
		return scomtDao.count(studySeq);
	}

	public boolean insert(ComtVo comt) {
		String date = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		comt.setDate(date);
		return scomtDao.insert(comt);
	}

	public boolean delete(int seq, String password, boolean isAdmin) {
		ComtVo comtVo = scomtDao.get(seq);
		if(comtVo.getPassword().equals(password) || isAdmin){
			return scomtDao.delete(seq);			
		} else {
			return false;
		}
	}
	

	public boolean checkPwd(int seq, String password, boolean isAdmin) {
		ComtVo comtVo = scomtDao.get(seq);
		if(comtVo.getPassword().equals(password) || isAdmin){
			return true;			
		} else {
			return false;
		}
	}

	public boolean update(int seq, String contents) {
		return scomtDao.update(seq, contents);
	}
}
