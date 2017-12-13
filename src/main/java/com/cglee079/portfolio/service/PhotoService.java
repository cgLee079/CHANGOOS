package com.cglee079.portfolio.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.PhotoDao;
import com.cglee079.portfolio.model.ItemVo;
import com.cglee079.portfolio.model.PhotoVo;

@Service
public class PhotoService {

	@Autowired
	private PhotoDao photoDao;
	
	public List<PhotoVo> list(){
		return photoDao.list();
	}
	
	public boolean insert(PhotoVo photo){
		photo.setLike(0);
		photo.setDate(formatDate(photo.getDate()));
		return photoDao.insert(photo);
	}

	public boolean update(PhotoVo photo){
		photo.setDate(formatDate(photo.getDate()));
		return photoDao.update(photo);
	}
	
	public boolean delete(int seq) {
		return photoDao.delete(seq);
	}
	
	public PhotoVo get(int seq){
		return photoDao.get(seq);
	}
	
	private String formatDate(String dateStr){
		try {
			if (dateStr != null){
				SimpleDateFormat transFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
				Date date;
				date = transFormat.parse(dateStr);
				return new SimpleDateFormat("yyyy.MM.dd").format(date);
			} else {
				return "";
			}
		} catch (ParseException e) {
			return dateStr;
		}
	
	}
}
