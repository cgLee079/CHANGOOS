package com.cglee079.portfolio.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.IComtDao;
import com.cglee079.portfolio.model.ComtVo;

@Service
public class IComtService{
	
	@Autowired
	IComtDao icomtDao;

	public List<ComtVo> paging(int boardSeq, int page, int perPgLine) {
		int startRow = (page - 1) * perPgLine;
		return icomtDao.list(boardSeq, startRow, perPgLine);
	}

	public int count(int boardSeq) {
		return icomtDao.count(boardSeq);
	}

	public boolean insert(ComtVo comt) {
		String date = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		comt.setDate(date);
		return icomtDao.insert(comt);
	}

	public boolean delete(int seq, String password) {
		ComtVo comtVo = icomtDao.get(seq);
		if(comtVo.getPassword().equals(password)){
			return icomtDao.delete(seq);			
		} else {
			return false;
		}
	}
	
	public boolean checkPwd(int seq, String password) {
		ComtVo comtVo = icomtDao.get(seq);
		if(comtVo.getPassword().equals(password)){
			return true;			
		} else {
			return false;
		}
	}

	public boolean update(int seq, String contents) {
		return icomtDao.update(seq, contents);
	}
}
