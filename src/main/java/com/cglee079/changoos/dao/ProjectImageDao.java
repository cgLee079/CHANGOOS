package com.cglee079.changoos.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.ProjectImageVo;

@Repository
public class ProjectImageDao {
	private static final String namespace = "com.cglee079.changoos.mapper.ProjectImageMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(ProjectImageVo projectImage) {
		return sqlSession.insert(namespace +".insert", projectImage) == 1;
	}

	public List<ProjectImageVo> list(int projectSeq) {
		return sqlSession.selectList(namespace +".list", projectSeq);
	}

	public boolean delete(int seq) {
		return  sqlSession.delete(namespace +".delete", seq) == 1;
	}

}
