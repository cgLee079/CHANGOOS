package com.cglee079.changoos.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cglee079.changoos.model.PhotoComtVo;

@Repository
public class PhotoComtDao {
	private static final String namespace = "com.cglee079.changoos.mapper.PhotoComtMapper";
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	public List<PhotoComtVo> list() {
		return sqlSession.selectList(namespace +".list");
	}

	public List<PhotoComtVo> list(int photoSeq) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("photoSeq", photoSeq);
		return sqlSession.selectList(namespace +".list", map);
	}
	
	public int count(int photoSeq) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("photoSeq", photoSeq);
		return sqlSession.selectOne(namespace +".count", map);
	}

	public boolean insert(PhotoComtVo pcomt) {
		return sqlSession.insert(namespace + ".insert", pcomt) == 1;
	}

	public boolean delete(int seq) {
		return sqlSession.delete(namespace + ".delete", seq) == 1;
	}

	public PhotoComtVo get(int seq) {
		return sqlSession.selectOne(namespace +".get", seq);
	}
	
	public boolean update(int seq, String contents) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("seq", seq);
		map.put("contents", contents);
		return sqlSession.update(namespace +".update", map) == 1;
	}
}
