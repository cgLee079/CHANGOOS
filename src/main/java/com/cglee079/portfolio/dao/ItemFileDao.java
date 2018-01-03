package com.cglee079.portfolio.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.portfolio.model.FileVo;

@Repository
public class ItemFileDao {
	private static final String namespace = "com.cglee079.portfolio.mapper.ItemFileMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(FileVo boardFile) {
		return sqlSession.insert(namespace +".insert", boardFile) == 1;
	}

	public List<FileVo> getFiles(int boardSeq) {
		return sqlSession.selectList(namespace +".list", boardSeq);
	}

	public FileVo getFile(String pathNm) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pathNm", pathNm);
		return  sqlSession.selectOne(namespace +".get", map);
	}
	
	public FileVo getFile(int seq) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("seq", seq);
		return  sqlSession.selectOne(namespace +".get", map);
	}

	public boolean delete(int seq) {
		return  sqlSession.delete(namespace +".delete", seq) == 1;
	}

}
