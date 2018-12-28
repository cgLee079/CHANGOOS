package com.cglee079.changoos.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.BoardComtVo;

@Repository
public class BoardComtDao {
	private static final String namespace = "com.cglee079.changoos.mapper.BoardComtMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<BoardComtVo> list(String TB, int studySeq, int startRow, int perPgLine) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("TB", TB);
		map.put("boardSeq", studySeq);
		map.put("startRow", startRow);
		map.put("perPgLine", perPgLine);
		return sqlSession.selectList(namespace +".list", map);
	}

	public int count(String TB, int boardSeq) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("TB", TB);
		map.put("boardSeq", boardSeq);
		return sqlSession.selectOne(namespace +".count", map);
	}

	public boolean insert(String TB, BoardComtVo comt) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("TB", TB);
		map.put("comt", comt);
		return sqlSession.insert(namespace + ".insert", map) == 1;
	}

	public boolean delete(String TB, int seq) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("TB", TB);
		map.put("seq", seq);
		return sqlSession.delete(namespace + ".delete", map) == 1;
	}

	public BoardComtVo get(String TB, int seq) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("TB", TB);
		map.put("seq", seq);
		return sqlSession.selectOne(namespace +".get", map);
	}
	
	public boolean update(String TB, BoardComtVo comt) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("TB", TB);
		map.put("comt", comt);
		return sqlSession.update(namespace +".update", map) == 1;
	}


}
