package com.cglee079.changoos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.CommonStrDao;

@Service
public class CommonStrService{
	@Autowired
	CommonStrDao commonStringDao;
	
	public String get(String groupID, String strID) {
		return commonStringDao.get(groupID, strID);
	}
	
}
