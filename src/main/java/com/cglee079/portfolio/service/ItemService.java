package com.cglee079.portfolio.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.ItemDao;
import com.cglee079.portfolio.model.ItemVo;

@Service
public class ItemService {

	@Autowired
	private ItemDao itemDao;
	
	public List<ItemVo> list(){
		return itemDao.list();
	}
	
	public boolean insert(ItemVo item) {
		if(item.getDirtURL() != null){
			item.setDirt(true);
		}
		item.setContent(filterWrapChar(item.getContent()));
		item.setHits(0);
		item.setWrDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		return itemDao.insert(item);
	}

	public boolean update(ItemVo item) {
		if(item.getDirtURL() != null){
			item.setDirt(true);
		}
		item.setHits(0);
		item.setWrDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		
		return itemDao.update(item);
	}
	
	public boolean delete(int seq) {
		return itemDao.delete(seq);
	}
	
	public ItemVo get(int seq){
		return itemDao.get(seq);
	}
	
	public String filterWrapChar(String text){
		text = text.replaceAll("\\r|\\n", "");
		return text;
	}
}
