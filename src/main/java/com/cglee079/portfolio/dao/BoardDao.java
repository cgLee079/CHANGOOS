package com.cglee079.portfolio.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.portfolio.model.AdminVo;
import com.cglee079.portfolio.model.BoardVo;
import com.cglee079.portfolio.model.Role;

@Repository
public class BoardDao {
	private static final String namespace = "com.cglee079.portfolio.mapper.BoardMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<BoardVo> list() {
		return sqlSession.selectList(namespace +".list");
	}
	
	public List<BoardVo> list(String type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		return sqlSession.selectList(namespace +".list", map);
	}

	public List<BoardVo> list(int startRow, int perPgLine, String type, String searchType, String searchValue) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startRow", startRow);
		map.put("perPgLine", perPgLine);
		map.put("type", type);
		map.put("searchType", searchType);
		map.put("searchValue", searchValue);
		return sqlSession.selectList(namespace +".list", map);
	}

	public int count(String type, String searchType, String searchValue) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("type", type);
		map.put("searchType", searchType);
		map.put("searchValue", searchValue);
		return sqlSession.selectOne(namespace +".count", map);
	}

	public BoardVo get(int seq) {
		return sqlSession.selectOne(namespace +".get", seq);
	}

	public int insert(BoardVo board) {
		sqlSession.insert(namespace +".insert", board);
		return board.getSeq();
	}

	public boolean delete(int seq) {
		return  sqlSession.delete(namespace +".delete", seq) == 1;
	}

	public boolean update(BoardVo board) {
		return  sqlSession.update(namespace +".update", board) == 1;
	}

	public BoardVo getBefore(int seq, String type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("type", type);
		return sqlSession.selectOne(namespace +".getBefore", map);
	}
	
	public BoardVo getAfter(int seq, String type) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("type", type);
		return sqlSession.selectOne(namespace +".getAfter", map);
	}
}
