package com.cglee079.changoos.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.BoardComtDao;
import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.util.AuthManager;
import com.cglee079.changoos.util.Formatter;

@Service
public class BoardComtService {

	@Autowired private BoardComtDao boardcomtDao;

	@Value("#{db['project.comt.tb.name']}")	private String projectComtTB;
	@Value("#{db['study.comt.tb.name']}") 	private String studyComtTB;
	@Value("#{db['blog.comt.tb.name']}") 	private String blogComtTB;

	@Value("#{constant['board.type.id.project']}") 	private String projectID;
	@Value("#{constant['board.type.id.study']}") 	private String studyID;
	@Value("#{constant['board.type.id.blog']}")		private String blogID;

	public List<BoardComtVo> paging(String boardType, int boardSeq, int page, int perPgLine) {
		String TB = this.getTB(boardType);

		int startRow = (page - 1) * perPgLine;
		return boardcomtDao.list(TB, boardSeq, startRow, perPgLine);
	}

	public int count(String boardType, int boardSeq) {
		String TB = this.getTB(boardType);
		return boardcomtDao.count(TB, boardSeq);
	}

	public boolean insert(String boardType, BoardComtVo comt) {
		String date = Formatter.toDate(new Date());
		comt.setDate(date);
		
		String TB = this.getTB(boardType);
		
		return boardcomtDao.insert(TB, comt);
	}

	public boolean update(String boardType, BoardComtVo comt) {
		String TB = this.getTB(boardType);
		return boardcomtDao.update(TB, comt);
	}
	
	public boolean delete(String boardType, BoardComtVo comt) {
		String TB = this.getTB(boardType);
		
		BoardComtVo savedComt = boardcomtDao.get(TB, comt.getSeq());
		if (AuthManager.isAdmin() || savedComt.getPassword().equals(comt.getPassword()) ) {
			return boardcomtDao.delete(TB, comt.getSeq());
		} else {
			return false;
		}
	}

	public boolean checkPwd(String boardType, BoardComtVo comt) {
		String TB = this.getTB(boardType);
		
		BoardComtVo savedComt = boardcomtDao.get(TB, comt.getSeq());
		if (AuthManager.isAdmin() || savedComt.getPassword().equals(comt.getPassword())) {
			return true;
		} else {
			return false;
		}
	}

	public String getTB(String boardType) {
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
