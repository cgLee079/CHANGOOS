package com.cglee079.portfolio.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.portfolio.model.Item;

@Repository
public class ItemDao {
	private static final String namespace ="com.cglee079.portfolio.mapper.ItemMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<Item> list(){
		return sqlSession.selectList(namespace + ".list");
	}
	
	public Item get(int seq) {
		return sqlSession.selectOne(namespace + ".get", seq);
	}
	
	public boolean insert(Item item) {
		return sqlSession.insert(namespace + ".insert", item) == 1;
	}

	public boolean delete(int seq) {
		return sqlSession.delete(namespace + ".delete", seq) == 1;
	}
}
