package com.cglee079.changoos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.CommonCodeDao;

@Service
public class CommonCodeService{
	@Autowired
	CommonCodeDao commonCodeDao;
	

	public String get(String group, String code) {
		return commonCodeDao.get(group, code);
	}
	
}
