package com.cglee079.portfolio.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.BComtDao;
import com.cglee079.portfolio.model.BComtVo;

@Service
public class BComtService{
	
	@Autowired
	BComtDao bcomtDao;

	public List<BComtVo> paging(int boardSeq, int page, int perPgLine) {
		int startRow = (page - 1) * perPgLine;
		return bcomtDao.list(boardSeq, startRow, perPgLine);
	}

	public int count(int boardSeq) {
		return bcomtDao.count(boardSeq);
	}

	public boolean insert(BComtVo bcomt) {
		String date = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		bcomt.setDate(date);
		return bcomtDao.insert(bcomt);
	}

	public boolean delete(int seq) {
		return bcomtDao.delete(seq);
	}
	
}
