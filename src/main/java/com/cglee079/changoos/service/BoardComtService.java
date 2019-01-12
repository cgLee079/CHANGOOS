package com.cglee079.changoos.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.config.code.BoardType;
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

	public List<BoardComtVo> paging(String boardType, int boardSeq, int offset, int limit) {
		String TB = this.getTB(boardType);

		return boardcomtDao.list(TB, boardSeq, offset, limit);
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
	
	public boolean delete(String boardType, int seq) {
		String TB = this.getTB(boardType);
		return boardcomtDao.delete(TB, seq);
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
		
		switch(BoardType.from(boardType)) {
		case PROJECT: TB = projectComtTB; break;
		case STUDY	: TB = studyComtTB; break;
		case BLOG	: TB = blogComtTB; break;
		}

		return TB;
	}
}
