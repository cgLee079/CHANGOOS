package com.cglee079.changoos.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.LogRqstVo;
import com.cglee079.changoos.model.VisitMsgVo;

@Repository
public class VisitMsgDao {
	private static final String namespace = "com.cglee079.changoos.mapper.VisitMsgMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;

	public boolean insert(VisitMsgVo visitMsgVo) {
		return sqlSession.insert(namespace + ".insert", visitMsgVo) == 1;
	}
}
