package com.cglee079.portfolio.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.portfolio.model.LogRqstVo;

@Repository
public class LogRqstDao {
	private static final String namespace = "com.cglee079.portfolio.mapper.LogRqstMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;

	public boolean insert(LogRqstVo logRqst) {
		return sqlSession.insert(namespace + ".insert", logRqst) == 1;
	}
}
