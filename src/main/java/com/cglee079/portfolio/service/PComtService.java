package com.cglee079.portfolio.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.PComtDao;
import com.cglee079.portfolio.model.ComtVo;

@Service
public class PComtService{
	
	@Autowired
	PComtDao pcomtDao;

	public List<ComtVo> paging(int boardSeq, int page, int perPgLine) {
		int startRow = (page - 1) * perPgLine;
		return pcomtDao.list(boardSeq, startRow, perPgLine);
	}

	public int count(int boardSeq) {
		return pcomtDao.count(boardSeq);
	}

	public boolean insert(ComtVo comt) {
		String date = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		comt.setDate(date);
		return pcomtDao.insert(comt);
	}

	public boolean delete(int seq, String password, boolean isAdmin) {
		ComtVo comtVo = pcomtDao.get(seq);
		if(comtVo.getPassword().equals(password) || isAdmin){
			return pcomtDao.delete(seq);			
		} else {
			return false;
		}
	}
	
	public boolean checkPwd(int seq, String password, boolean isAdmin) {
		ComtVo comtVo = pcomtDao.get(seq);
		if(comtVo.getPassword().equals(password) || isAdmin){
			return true;			
		} else {
			return false;
		}
	}

	public boolean update(int seq, String contents) {
		return pcomtDao.update(seq, contents);
	}
}
