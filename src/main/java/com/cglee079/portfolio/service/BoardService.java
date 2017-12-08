package com.cglee079.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.BoardDao;
import com.cglee079.portfolio.model.BoardVo;

@Service
public class BoardService{
	
	@Autowired
	BoardDao boardDao;
	
	public List<BoardVo> list(){
		return null;
		//return boardDao.list();
	}
	
	public List<BoardVo> paging(int page, int perPgLine){
		int startRow = (page - 1) * perPgLine + 1;
		return boardDao.list(startRow, perPgLine);
	}

	public int count() {
		return boardDao.count();
	}
}
