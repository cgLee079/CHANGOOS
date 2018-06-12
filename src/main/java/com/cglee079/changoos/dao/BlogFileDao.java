package com.cglee079.changoos.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.BlogFileVo;

@Repository
public class BlogFileDao {
	private static final String namespace = "com.cglee079.changoos.mapper.BlogFileMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(BlogFileVo blogFile) {
		return sqlSession.insert(namespace +".insert", blogFile) == 1;
	}

	public List<BlogFileVo> list(int blogSeq) {
		return sqlSession.selectList(namespace +".list", blogSeq);
	}

	public BlogFileVo getFile(String pathNm) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("pathNm", pathNm);
		return  sqlSession.selectOne(namespace +".get", map);
	}
	
	public BlogFileVo getFile(int seq) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("seq", seq);
		return  sqlSession.selectOne(namespace +".get", map);
	}

	public boolean delete(int seq) {
		return  sqlSession.delete(namespace +".delete", seq) == 1;
	}

}
