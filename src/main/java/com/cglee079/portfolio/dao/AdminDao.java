package com.cglee079.portfolio.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.portfolio.model.AdminVo;
import com.cglee079.portfolio.model.Role;

@Repository
public class AdminDao {
	private static final String namespace = "com.cglee079.portfolio.mapper.AdminMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public AdminVo get(String username) {
		return sqlSession.selectOne(namespace +".get", username);
	}
	
	public List<Role> getAuths(String username){
		return sqlSession.selectList(namespace +".getAuths", username);
	}
}
