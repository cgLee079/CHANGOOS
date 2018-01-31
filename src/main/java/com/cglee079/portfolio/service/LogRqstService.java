package com.cglee079.portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.LogRqstDao;
import com.cglee079.portfolio.model.LogRqstVo;

@Service
public class LogRqstService {
	
	@Autowired
	private LogRqstDao logRqstDao;
	
	public boolean insert(LogRqstVo logRqst){
		return logRqstDao.insert(logRqst);
	}
}
