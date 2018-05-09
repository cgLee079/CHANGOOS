package com.cglee079.changoos.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.AdminVo;
import com.cglee079.changoos.model.BoardVo;
import com.cglee079.changoos.model.Role;

@Repository
public class BoardDao {
	private static final String namespace = "com.cglee079.changoos.mapper.BoardMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public BoardVo get(int seq) {
		return sqlSession.selectOne(namespace +".S01", seq);
	}
	
	public BoardVo getBefore(int seq, String sect) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("sect", sect);
		return sqlSession.selectOne(namespace +".S02", map);
	}
	
	public BoardVo getAfter(int seq, String sect) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("sect", sect);
		return sqlSession.selectOne(namespace +".S03", map);
	}

	public List<BoardVo> list() {
		return sqlSession.selectList(namespace +".S04");
	}
	
	public List<BoardVo> list(Map<String, Object> params) {
		return sqlSession.selectList(namespace +".S04", params);
	}
	
	public int count(Map<String, Object> params) {
		return sqlSession.selectOne(namespace +".S05", params);
	}

	public List<String> getSects() {
		return sqlSession.selectList(namespace +".S06");
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
}
