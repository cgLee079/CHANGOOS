package com.cglee079.changoos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.SessionLogDao;
import com.cglee079.changoos.model.SessionLogVo;

@Service
public class SessionLogService {
	
	@Autowired
	private SessionLogDao sessionLogDao;
	
	public boolean insert(SessionLogVo sessionLog){
		return sessionLogDao.insert(sessionLog);
	}
}
