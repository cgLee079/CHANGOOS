package com.cglee079.changoos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.VisitMsgDao;
import com.cglee079.changoos.model.VisitMsgVo;

@Service
public class VisitMsgService {
	
	@Autowired
	private VisitMsgDao visitMsgDao;
	
	public boolean insert(VisitMsgVo visitMsgVo){
		return visitMsgDao.insert(visitMsgVo);
	}
}
