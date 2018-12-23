package com.cglee079.changoos.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.StudyComtVo;

@Repository
public class StudyComtDao {
	private static final String namespace = "com.cglee079.changoos.mapper.StudyComtMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<StudyComtVo> list() {
		return sqlSession.selectList(namespace +".list");
	}

	public List<StudyComtVo> list(int studySeq, int startRow, int perPgLine) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("studySeq", studySeq);
		map.put("startRow", startRow);
		map.put("perPgLine", perPgLine);
		return sqlSession.selectList(namespace +".list", map);
	}

	public int count(int studySeq) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("studySeq", studySeq);
		return sqlSession.selectOne(namespace +".count", map);
	}

	public boolean insert(StudyComtVo bcomt) {
		return sqlSession.insert(namespace + ".insert", bcomt) == 1;
	}

	public boolean delete(int seq) {
		return sqlSession.delete(namespace + ".delete", seq) == 1;
	}

	public StudyComtVo get(int seq) {
		return sqlSession.selectOne(namespace +".get", seq);
	}
	
	public boolean update(int seq, String contents) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("contents", contents);
		return sqlSession.update(namespace +".update", map) == 1;
	}


}
