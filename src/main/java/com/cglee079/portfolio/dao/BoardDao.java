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

	public List<BoardVo> list( int startRow, int perPgLine) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("startRow", startRow);
		map.put("perPgLine", perPgLine);
		return sqlSession.selectList(namespace +".list", map);
	}

	public int count() {
		return sqlSession.selectOne(namespace +".count");
	}
}
