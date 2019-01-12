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

	public List<BoardComtVo> list(String TB, int boardSeq, int offset, int limit) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("TB", TB);
		param.put("boardSeq", boardSeq);
		param.put("offset", offset);
		param.put("limit", limit);
		return sqlSession.selectList(namespace +".list", param);
	}

	public int count(String TB, int boardSeq) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("TB", TB);
		params.put("boardSeq", boardSeq);
		return sqlSession.selectOne(namespace +".count", params);
	}

	public boolean insert(String TB, BoardComtVo comt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("TB", TB);
		params.put("comt", comt);
		return sqlSession.insert(namespace + ".insert", params) == 1;
	}

	public boolean delete(String TB, int seq) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("TB", TB);
		params.put("seq", seq);
		return sqlSession.delete(namespace + ".delete", params) == 1;
	}

	public BoardComtVo get(String TB, int seq) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("TB", TB);
		params.put("seq", seq);
		return sqlSession.selectOne(namespace +".get", params);
	}
	
	public boolean update(String TB, BoardComtVo comt) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("TB", TB);
		params.put("comt", comt);
		return sqlSession.update(namespace +".update", params) == 1;
	}


}
