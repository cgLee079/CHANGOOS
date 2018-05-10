package com.cglee079.changoos.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.PhotoVo;

@Repository
public class PhotoDao {
	private static final String namespace ="com.cglee079.changoos.mapper.PhotoMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public PhotoVo get(int seq) {
		return sqlSession.selectOne(namespace + ".get", seq);
	}
	
	public List<PhotoVo> list(Map<String, Object> map){
		return sqlSession.selectList(namespace + ".list", map);
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
