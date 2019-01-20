package com.cglee079.changoos.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.config.annotation.SolrData;
import com.cglee079.changoos.model.ProjectVo;

@Repository
public class ProjectDao {
	private static final String namespace ="com.cglee079.changoos.mapper.ProjectMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public ProjectVo get(int seq) {
		return sqlSession.selectOne(namespace + ".get", seq);
	}
	
	public List<ProjectVo> list(Map<String, Object> param) {
		return sqlSession.selectList(namespace + ".list", param);
	}
	
	@SolrData
	public int insert(ProjectVo project) {
		sqlSession.insert(namespace + ".insert", project);
		return project.getSeq();
	}

	@SolrData
	public boolean update(ProjectVo project) {
		return sqlSession.update(namespace + ".update", project) == 1;
	}

	@SolrData
	public boolean delete(int seq) {
		return sqlSession.delete(namespace + ".delete", seq) == 1;
	}
	
	public ProjectVo getBefore(int seq) {
		return sqlSession.selectOne(namespace + ".getBefore", seq);
	}

	public ProjectVo getAfter(int seq) {
		return sqlSession.selectOne(namespace + ".getAfter", seq);
	}
}
