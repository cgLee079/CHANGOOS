package com.cglee079.changoos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.LogRqstDao;
import com.cglee079.changoos.model.LogRqstVo;

@Service
public class LogRqstService {
	
	@Autowired
	private LogRqstDao logRqstDao;
	
	public boolean insert(LogRqstVo logRqst){
		return logRqstDao.insert(logRqst);
	}
}
