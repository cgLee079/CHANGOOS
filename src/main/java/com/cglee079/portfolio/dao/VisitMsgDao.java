package com.cglee079.portfolio.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.portfolio.model.LogRqstVo;
import com.cglee079.portfolio.model.VisitMsgVo;

@Repository
public class VisitMsgDao {
	private static final String namespace = "com.cglee079.portfolio.mapper.VisitMsgMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;

	public boolean insert(VisitMsgVo visitMsgVo) {
		return sqlSession.insert(namespace + ".insert", visitMsgVo) == 1;
	}
}
