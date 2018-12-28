package com.cglee079.changoos.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.BoardFileVo;

@Repository
public class BoardFileDao {
	private static final String namespace = "com.cglee079.changoos.mapper.BoardFileMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(String TB, BoardFileVo file) {
		Map<String, Object> map = new HashMap<>();
		map.put("TB", TB);
		map.put("file", file);
		
		
		return sqlSession.insert(namespace +".insert", map) == 1;
	}

	public List<BoardFileVo> list(String TB, int boardSeq) {
		Map<String, Object> map = new HashMap<>();
		map.put("TB", TB);
		map.put("boardSeq", boardSeq);
		
		return sqlSession.selectList(namespace +".list", map);
	}

	public boolean delete(String TB, int seq) {
		Map<String, Object> map = new HashMap<>();
		map.put("TB", TB);
		map.put("seq", seq);
		return  sqlSession.delete(namespace +".delete", map) == 1;
	}

}
