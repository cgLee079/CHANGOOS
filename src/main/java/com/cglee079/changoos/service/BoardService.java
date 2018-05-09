package com.cglee079.changoos.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.BoardDao;
import com.cglee079.changoos.dao.BoardFileDao;
import com.cglee079.changoos.model.BoardVo;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.ProjectVo;
import com.cglee079.changoos.util.Formatter;

@Service
public class BoardService{
	
	@Autowired
	BoardDao boardDao;
	
	public List<BoardVo> paging(Map<String, Object> params){
		int page = Integer.parseInt((String)params.get("page"));
		int perPgLine = Integer.parseInt((String)params.get("perPgLine"));
		int startRow = (page - 1) * perPgLine;
		
		params.put("startRow", startRow);
		return boardDao.list(params);
	}

	public int count(Map<String, Object> params) {
		return boardDao.count(params);
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

	public BoardVo getBefore(int seq, String sect) {
		return boardDao.getBefore(seq, sect);
	}

	public BoardVo getAfter(int seq, String sect) {
		return boardDao.getAfter(seq, sect);
	}

	public List<String> getSects() {
		return boardDao.getSects();
	}
}
