package com.cglee079.changoos.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.ProjectComtDao;
import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.model.ProjectComtVo;

@Service
public class ProjectComtService{
	
	@Autowired
	ProjectComtDao projectcomtDao;

	public List<ProjectComtVo> paging(int boardSeq, int page, int perPgLine) {
		int startRow = (page - 1) * perPgLine;
		return projectcomtDao.list(boardSeq, startRow, perPgLine);
	}

	public int count(int boardSeq) {
		return projectcomtDao.count(boardSeq);
	}

	public boolean insert(ProjectComtVo comt) {
		String date = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		comt.setDate(date);
		return projectcomtDao.insert(comt);
	}

	public boolean delete(int seq, String password, boolean isAdmin) {
		ProjectComtVo comtVo = projectcomtDao.get(seq);
		if(comtVo.getPassword().equals(password) || isAdmin){
			return projectcomtDao.delete(seq);			
		} else {
			return false;
		}
	}
	
	public boolean checkPwd(int seq, String password, boolean isAdmin) {
		ProjectComtVo comtVo = projectcomtDao.get(seq);
		if(comtVo.getPassword().equals(password) || isAdmin){
			return true;			
		} else {
			return false;
		}
	}

	public boolean update(int seq, String contents) {
		return projectcomtDao.update(seq, contents);
	}
}
