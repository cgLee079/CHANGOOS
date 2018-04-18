package com.cglee079.changoos.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.LogRqstVo;

@Repository
public class LogRqstDao {
	private static final String namespace = "com.cglee079.changoos.mapper.LogRqstMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;

	public boolean insert(LogRqstVo logRqst) {
		return sqlSession.insert(namespace + ".insert", logRqst) == 1;
	}
}
