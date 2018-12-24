package com.cglee079.changoos.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.FileVo;
import com.cglee079.changoos.model.FileVo;

@Repository
public class ProjectFileDao {
	private static final String namespace = "com.cglee079.changoos.mapper.ProjectFileMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(FileVo projectFile) {
		return sqlSession.insert(namespace +".insert", projectFile) == 1;
	}

	public List<FileVo> list(int projectSeq) {
		return sqlSession.selectList(namespace +".list", projectSeq);
	}

	public FileVo get(String pathNm) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pathNm", pathNm);
		return  sqlSession.selectOne(namespace +".get", map);
	}
	
	public FileVo get(int seq) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("seq", seq);
		return  sqlSession.selectOne(namespace +".get", map);
	}

	public boolean delete(int seq) {
		return  sqlSession.delete(namespace +".delete", seq) == 1;
	}

}
