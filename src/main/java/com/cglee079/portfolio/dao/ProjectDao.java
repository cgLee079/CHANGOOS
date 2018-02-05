package com.cglee079.portfolio.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.portfolio.model.ProjectVo;

@Repository
public class ProjectDao {
	private static final String namespace ="com.cglee079.portfolio.mapper.ProjectMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<ProjectVo> list(){	
		return sqlSession.selectList(namespace + ".list");
	}
	
	public ProjectVo get(int seq) {
		return sqlSession.selectOne(namespace + ".get", seq);
	}
	
	public int insert(ProjectVo item) {
		sqlSession.insert(namespace + ".insert", item);
		return item.getSeq();
	}

	public boolean delete(int seq) {
		return sqlSession.delete(namespace + ".delete", seq) == 1;
	}

	public boolean update(ProjectVo item) {
		return sqlSession.update(namespace + ".update", item) == 1;
	}

	public ProjectVo getBefore(int sort) {
		return sqlSession.selectOne(namespace + ".getBefore", sort);
	}

	public ProjectVo getAfter(int sort) {
		return sqlSession.selectOne(namespace + ".getAfter", sort);
	}

}
