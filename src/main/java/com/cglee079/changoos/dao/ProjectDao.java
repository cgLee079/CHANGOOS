package com.cglee079.changoos.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.ProjectVo;

@Repository
public class ProjectDao {
	private static final String namespace ="com.cglee079.changoos.mapper.ProjectMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<ProjectVo> list(){	
		return sqlSession.selectList(namespace + ".list");
	}
	
	public ProjectVo get(int seq) {
		return sqlSession.selectOne(namespace + ".get", seq);
	}
	
	public int insert(ProjectVo project) {
		sqlSession.insert(namespace + ".insert", project);
		return project.getSeq();
	}

	public boolean delete(int seq) {
		return sqlSession.delete(namespace + ".delete", seq) == 1;
	}

	public boolean update(ProjectVo project) {
		return sqlSession.update(namespace + ".update", project) == 1;
	}

	public ProjectVo getBefore(int sort) {
		return sqlSession.selectOne(namespace + ".getBefore", sort);
	}

	public ProjectVo getAfter(int sort) {
		return sqlSession.selectOne(namespace + ".getAfter", sort);
	}

}
