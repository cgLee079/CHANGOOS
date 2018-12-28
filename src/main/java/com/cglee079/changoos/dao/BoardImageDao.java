package com.cglee079.changoos.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.BoardImageVo;

@Repository
public class BoardImageDao {
	private static final String namespace = "com.cglee079.changoos.mapper.BoardImageMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(String TB, BoardImageVo image) {
		Map<String, Object> map = new HashMap<>();
		map.put("TB", TB);
		map.put("image", image);
		
		return sqlSession.insert(namespace +".insert", map) == 1;
	}

	public List<BoardImageVo> list(String TB, int boardSeq) {
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
