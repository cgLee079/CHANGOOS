package com.cglee079.changoos.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.StudyFileVo;
import com.cglee079.changoos.model.ProjectFileVo;

@Repository
public class ProjectFileDao {
	private static final String namespace = "com.cglee079.changoos.mapper.ProjectFileMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(ProjectFileVo projectFile) {
		return sqlSession.insert(namespace +".insert", projectFile) == 1;
	}

	public List<ProjectFileVo> list(int projectSeq) {
		return sqlSession.selectList(namespace +".list", projectSeq);
	}

	public ProjectFileVo get(String pathNm) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pathNm", pathNm);
		return  sqlSession.selectOne(namespace +".get", map);
	}
	
	public ProjectFileVo get(int seq) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("seq", seq);
		return  sqlSession.selectOne(namespace +".get", map);
	}

	public boolean delete(int seq) {
		return  sqlSession.delete(namespace +".delete", seq) == 1;
	}

}
