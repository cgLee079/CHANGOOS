package com.cglee079.portfolio.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.portfolio.model.ComtVo;
import com.cglee079.portfolio.model.BoardVo;

@Repository
public class ComtDao {
	private static final String namespace = "com.cglee079.portfolio.mapper.ComtMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<BoardVo> list() {
		return sqlSession.selectList(namespace +".list");
	}

	public List<ComtVo> list(int itemSeq, int startRow, int perPgLine) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemSeq", itemSeq);
		map.put("startRow", startRow);
		map.put("perPgLine", perPgLine);
		return sqlSession.selectList(namespace +".list", map);
	}

	public int count(int itemSeq) {
		return sqlSession.selectOne(namespace +".count", itemSeq);
	}

	public boolean insert(ComtVo bcomt) {
		return sqlSession.insert(namespace + ".insert", bcomt) == 1;
	}

	public boolean delete(int seq) {
		return sqlSession.delete(namespace + ".delete", seq) == 1;
	}

	public ComtVo get(int seq) {
		return sqlSession.selectOne(namespace +".get", seq);
	}

}
