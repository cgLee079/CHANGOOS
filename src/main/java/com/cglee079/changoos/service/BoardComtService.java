package com.cglee079.changoos.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.BoardComtDao;
import com.cglee079.changoos.model.ComtVo;
import com.cglee079.changoos.util.AuthManager;

@Service
public class BoardComtService {

	@Autowired private BoardComtDao scomtDao;

	@Value("#{tb['project.comt.tb.name']}")	private String projectComtTB;
	@Value("#{tb['study.comt.tb.name']}") 	private String studyComtTB;
	@Value("#{tb['blog.comt.tb.name']}") 	private String blogComtTB;

	@Value("#{constant['board.type.id.project']}") 	private String projectID;
	@Value("#{constant['board.type.id.study']}") 	private String studyID;
	@Value("#{constant['board.type.id.blog']}")		private String blogID;

	public List<ComtVo> paging(String boardType, int studySeq, int page, int perPgLine) {
		String TB = this.getTB(boardType);

		int startRow = (page - 1) * perPgLine;
		return scomtDao.list(TB, studySeq, startRow, perPgLine);
	}

	public int count(String boardType, int boardSeq) {
		String TB = this.getTB(boardType);
		return scomtDao.count(TB, boardSeq);
	}

	public boolean insert(String boardType, ComtVo comt) {
		String date = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		comt.setDate(date);
		
		String TB = this.getTB(boardType);
		
		return scomtDao.insert(TB, comt);
	}

	public boolean delete(String boardType, ComtVo comt) {
		String TB = this.getTB(boardType);
		
		ComtVo comtVo = scomtDao.get(TB, comt.getSeq());
		if (comtVo.getPassword().equals(comt.getPassword()) || AuthManager.isAdmin()) {
			return scomtDao.delete(TB, comt.getSeq());
		} else {
			return false;
		}
	}

	public boolean checkPwd(String boardType, ComtVo comt) {
		String TB = this.getTB(boardType);
		
		ComtVo comtVo = scomtDao.get(TB, comt.getSeq());
		if (comtVo.getPassword().equals(comt.getPassword()) || AuthManager.isAdmin()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean update(String boardType, ComtVo comt) {
		String TB = this.getTB(boardType);
		return scomtDao.update(TB, comt);
	}

	private String getTB(String boardType) {
		String TB = "";
		
		if (boardType.equals(projectID)) {
			TB = projectComtTB;
		} else if (boardType.equals(studyID)) {
			TB = studyComtTB;
		} else if (boardType.equals(blogID)) {
			TB = blogComtTB;
		}

		return TB;
	}
}
