package com.cglee079.changoos.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.PhotoComtDao;
import com.cglee079.changoos.model.BoardComtVo;
import com.cglee079.changoos.model.PhotoComtVo;
import com.cglee079.changoos.util.AuthManager;

@Service
public class PhotoComtService{
	
	@Autowired
	PhotoComtDao photocomtDao;
	
	public List<PhotoComtVo> list(int photoSeq) {
		return photocomtDao.list(photoSeq);
	}
	
	public int count(int photoSeq) {
		return photocomtDao.count(photoSeq);
	}

	public boolean insert(PhotoComtVo comt) {
		String date = new SimpleDateFormat("YYYY-MM-dd").format(new Date());
		comt.setDate(date);
		return photocomtDao.insert(comt);
	}

	public boolean delete(PhotoComtVo comt) {
		int seq = comt.getSeq();
		PhotoComtVo savedComt = photocomtDao.get(seq);
		if(savedComt.getPassword().equals(comt.getPassword()) || AuthManager.isAdmin()){
			return photocomtDao.delete(seq);			
		} else {
			return false;
		}
	}
	
}
