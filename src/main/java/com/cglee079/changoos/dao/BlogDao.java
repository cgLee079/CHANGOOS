package com.cglee079.changoos.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.BlogVo;

@Repository
public class BlogDao {
	private static final String namespace = "com.cglee079.changoos.mapper.BlogMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public int count(Map<String, Object> params) {
		return sqlSession.selectOne(namespace +".count", params);
	}

	public List<String> getTags() {
		return sqlSession.selectList(namespace +".getTags");
	}
	
	public BlogVo get(int seq) {
		return sqlSession.selectOne(namespace +".get", seq);
	}
	
	public List<BlogVo> list(Map<String, Object> params) {
		return sqlSession.selectList(namespace +".list", params);
	}
	
	public int insert(BlogVo blog) {
		sqlSession.insert(namespace +".insert", blog);
		return blog.getSeq();
	}

	public boolean update(BlogVo blog) {
		return  sqlSession.update(namespace +".update", blog) == 1;
	}
	
	public boolean delete(int seq) {
		return  sqlSession.delete(namespace +".delete", seq) == 1;
	}
}
