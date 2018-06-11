package com.cglee079.changoos.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.PhotoComtDao;
import com.cglee079.changoos.model.PhotoComtVo;

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

	public boolean delete(int seq, String password, boolean isAdmin) {
		PhotoComtVo comtVo = photocomtDao.get(seq);
		if(comtVo.getPassword().equals(password) || isAdmin){
			return photocomtDao.delete(seq);			
		} else {
			return false;
		}
	}
	
	public boolean checkPwd(int seq, String password, boolean isAdmin) {
		PhotoComtVo comtVo = photocomtDao.get(seq);
		if(comtVo.getPassword().equals(password) || isAdmin){
			return true;			
		} else {
			return false;
		}
	}

	public boolean update(int seq, String contents) {
		return photocomtDao.update(seq, contents);
	}
}
