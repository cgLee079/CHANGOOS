package com.cglee079.changoos.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.BlogVo;

@Repository
public class CommonStrDao {
	private static final String namespace = "com.cglee079.changoos.mapper.CommonStrMapper";

	@Autowired
	private SqlSessionTemplate sqlSession;

	public String get(String groupID, String strID) {
		Map<String, Object> map = new HashMap<>();
		map.put("groupID", groupID);
		map.put("strID", strID);
		
		return sqlSession.selectOne(namespace +".get", map);
	}

}
