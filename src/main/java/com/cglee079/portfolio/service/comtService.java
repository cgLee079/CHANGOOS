package com.cglee079.portfolio.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.ComtDao;
import com.cglee079.portfolio.model.ComtVo;

@Service
public class comtService{
	
	@Autowired
	ComtDao comtDao;

	public List<ComtVo> paging(int itemSeq, int page, int perPgLine) {
		int startRow = (page - 1) * perPgLine;
		return comtDao.list(itemSeq, startRow, perPgLine);
	}

	public int count(int itemSeq) {
		return comtDao.count(itemSeq);
	}

	public boolean insert(ComtVo comt) {
		String date = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		comt.setDate(date);
		return comtDao.insert(comt);
	}

	public boolean delete(int seq, String password) {
		ComtVo comtVo = comtDao.get(seq);
		if(comtVo.getPassword().equals(password)){
			return comtDao.delete(seq);			
		} else {
			return false;
		}
	}
	
}
