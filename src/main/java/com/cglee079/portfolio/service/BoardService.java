package com.cglee079.portfolio.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.BoardDao;
import com.cglee079.portfolio.model.BoardVo;
import com.cglee079.portfolio.model.ItemVo;
import com.cglee079.portfolio.util.Formatter;

@Service
public class BoardService{
	
	@Autowired
	BoardDao boardDao;
	
	public List<BoardVo> paging(int page, int perPgLine, String type){
		int startRow = (page - 1) * perPgLine;
		return boardDao.list(startRow, perPgLine, type);
	}

	public int count() {
		return boardDao.count();
	}

	public BoardVo get(int seq) {
		return boardDao.get(seq);
	}
	
	public BoardVo doView(int seq) {
		BoardVo board = boardDao.get(seq);
		board.setHits(board.getHits() + 1);
		boardDao.update(board);
		return board;
	}

	public int insert(BoardVo board) {
		board.setDate(Formatter.toDate(new Date()));
		board.setHits(0);
		return boardDao.insert(board);
	}

	public boolean delete(int seq) {
		return boardDao.delete(seq);
	}
	
	public List<String> getContentImgPath(int seq, String path){
		List<String> imgPaths = new ArrayList<String>();
		BoardVo board = boardDao.get(seq);
		String content = board.getContents();
		
		int stIndex = 0;
		int endIndex= 0;
		
		if(content != null){
			while(true){
				stIndex = content.indexOf(path, endIndex);
				endIndex = content.indexOf("\"", stIndex);
				
				if(stIndex == -1){ break;}
				if(endIndex == -1){ break;}
				
				imgPaths.add(content.substring(stIndex, endIndex));
			}
		}
		
		return imgPaths;
	}

	public boolean update(BoardVo board) {
		return boardDao.update(board);
	}

	public BoardVo getBefore(int seq, String type) {
		return boardDao.getBefore(seq, type);
	}

	public BoardVo getAfter(int seq, String type) {
		return boardDao.getAfter(seq, type);
	}

	public List<BoardVo> list(String type) {
		return boardDao.list(type);
	}

}
