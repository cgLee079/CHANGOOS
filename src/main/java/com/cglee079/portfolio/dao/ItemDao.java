package com.cglee079.portfolio.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.portfolio.model.ItemVo;

@Repository
public class ItemDao {
	private static final String namespace ="com.cglee079.portfolio.mapper.ItemMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public List<ItemVo> list(){	
		return sqlSession.selectList(namespace + ".list");
	}
	
	public ItemVo get(int seq) {
		return sqlSession.selectOne(namespace + ".get", seq);
	}
	
	public int insert(ItemVo item) {
		sqlSession.insert(namespace + ".insert", item);
		return item.getSeq();
	}

	public boolean delete(int seq) {
		return sqlSession.delete(namespace + ".delete", seq) == 1;
	}

	public boolean update(ItemVo item) {
		return sqlSession.update(namespace + ".update", item) == 1;
	}

}
