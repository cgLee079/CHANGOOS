package com.cglee079.portfolio.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.portfolio.model.ProjectVo;
import com.cglee079.portfolio.model.PhotoVo;

@Repository
public class PhotoDao {
	private static final String namespace ="com.cglee079.portfolio.mapper.PhotoMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<PhotoVo> list(){
		return sqlSession.selectList(namespace + ".list");
	}
	
	public PhotoVo get(int seq) {
		return sqlSession.selectOne(namespace + ".get", seq);
	}
	
	public boolean insert(PhotoVo photo) {
		return sqlSession.insert(namespace + ".insert", photo) == 1;
	}

	public boolean delete(int seq) {
		return sqlSession.delete(namespace + ".delete", seq) == 1;
	}

	public boolean update(PhotoVo photo) {
		return sqlSession.update(namespace + ".update", photo) == 1;
	}
}
