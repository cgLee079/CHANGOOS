package com.cglee079.portfolio.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.portfolio.model.BoardFileVo;

@Repository
public class BoardFileDao {
	private static final String namespace = "com.cglee079.portfolio.mapper.BoardFileMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(BoardFileVo boardFile) {
		return sqlSession.insert(namespace +".insert", boardFile) == 1;
	}

	public List<BoardFileVo> getFiles(int boardSeq) {
		return sqlSession.selectList(namespace +".list", boardSeq);
	}

	public BoardFileVo BoardFileVo(String pathNm) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pathNm", pathNm);
		return  sqlSession.selectOne(namespace +".get", map);
	}

}
