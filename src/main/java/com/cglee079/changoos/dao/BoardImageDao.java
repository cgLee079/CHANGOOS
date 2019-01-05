package com.cglee079.changoos.dao;

import java.util.HashMap;
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
	
	public BoardImageVo get(String TB, int seq) {
		Map<String, Object> params = new HashMap<>();
		params.put("TB", TB);
		params.put("seq", seq);
		
		return sqlSession.selectOne(namespace +".get", params);
	}
	
	public boolean insert(String TB, BoardImageVo image) {
		Map<String, Object> map = new HashMap<>();
		map.put("TB", TB);
		map.put("image", image);
		
		return sqlSession.insert(namespace +".insert", map) == 1;
	}

	public boolean delete(String TB, int seq) {
		Map<String, Object> map = new HashMap<>();
		map.put("TB", TB);
		map.put("seq", seq);
		return  sqlSession.delete(namespace +".delete", map) == 1;
	}

}
