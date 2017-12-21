package com.cglee079.portfolio.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.BoardDao;
import com.cglee079.portfolio.model.BoardVo;
import com.cglee079.portfolio.util.Formatter;

@Service
public class BoardService{
	
	@Autowired
	BoardDao boardDao;
	
	public List<BoardVo> paging(int page, int perPgLine){
		int startRow = (page - 1) * perPgLine;
		return boardDao.list(startRow, perPgLine);
	}

	public int count() {
		return boardDao.count();
	}

	public BoardVo get(int seq) {
		return boardDao.get(seq);
	}

	public boolean insert(BoardVo board) {
		board.setDate(Formatter.toDate(new Date()));
		board.setHits(0);
		return boardDao.insert(board);
	}
}
