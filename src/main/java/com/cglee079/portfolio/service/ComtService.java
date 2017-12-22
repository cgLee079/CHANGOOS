package com.cglee079.portfolio.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.ComtDao;
import com.cglee079.portfolio.model.ComtVo;

@Service
public class ComtService{
	
	@Autowired
	ComtDao comtDao;

	public List<ComtVo> paging(int parentSeq, int page, int perPgLine) {
		int startRow = (page - 1) * perPgLine;
		return comtDao.list(parentSeq, startRow, perPgLine);
	}

	public int count(int parentSeq) {
		return comtDao.count(parentSeq);
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

	public boolean checkPwd(int seq, String password) {
		ComtVo comtVo = comtDao.get(seq);
		if(comtVo.getPassword().equals(password)){
			return true;			
		} else {
			return false;
		}
	}

	public boolean update(int seq, String contents) {
		return comtDao.update(seq, contents);
	}
	
	private String changeLiner(String text){
		text = text.replaceAll("\\r\\n|\\r|\\n", "<br/>");
		return text;
	}
}
