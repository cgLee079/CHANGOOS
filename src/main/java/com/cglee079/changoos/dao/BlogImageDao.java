package com.cglee079.changoos.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.ImageVo;

@Repository
public class BlogImageDao {
	private static final String namespace = "com.cglee079.changoos.mapper.BlogImageMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	public boolean insert(ImageVo blogImage) {
		return sqlSession.insert(namespace +".insert", blogImage) == 1;
	}

	public List<ImageVo> list(int blogSeq) {
		return sqlSession.selectList(namespace +".list", blogSeq);
	}

	public boolean delete(int seq) {
		return  sqlSession.delete(namespace +".delete", seq) == 1;
	}

}
