package com.cglee079.changoos.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.StudyImageVo;

@Repository
public class StudyImageDao {
	private static final String namespace = "com.cglee079.changoos.mapper.StudyImageMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(StudyImageVo studyImage) {
		return sqlSession.insert(namespace +".insert", studyImage) == 1;
	}

	public List<StudyImageVo> list(int studySeq) {
		return sqlSession.selectList(namespace +".list", studySeq);
	}

	public boolean delete(int seq) {
		return  sqlSession.delete(namespace +".delete", seq) == 1;
	}

}
