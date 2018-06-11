package com.cglee079.changoos.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.StudyComtVo;
import com.cglee079.changoos.model.ProjectComtVo;

@Repository
public class ProjectComtDao {
	private static final String namespace = "com.cglee079.changoos.mapper.ProjectComtMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<ProjectComtVo> list() {
		return sqlSession.selectList(namespace +".list");
	}

	public List<ProjectComtVo> list(int projectSeq, int startRow, int perPgLine) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("projectSeq", projectSeq);
		map.put("startRow", startRow);
		map.put("perPgLine", perPgLine);
		return sqlSession.selectList(namespace +".list", map);
	}

	public int count(int projectSeq) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("projectSeq", projectSeq);
		return sqlSession.selectOne(namespace +".count", map);
	}

	public boolean insert(ProjectComtVo pcomt) {
		return sqlSession.insert(namespace + ".insert", pcomt) == 1;
	}

	public boolean delete(int seq) {
		return sqlSession.delete(namespace + ".delete", seq) == 1;
	}

	public ProjectComtVo get(int seq) {
		return sqlSession.selectOne(namespace +".get", seq);
	}
	
	public boolean update(int seq, String contents) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("contents", contents);
		return sqlSession.update(namespace +".update", map) == 1;
	}


}
