package com.cglee079.portfolio.service;

import java.util.List;

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
		item.setContent(filterWrapChar(item.getContent()));
		item.setDesc(filterWrapChar(item.getDesc()));
		
		item.setHits(0);
		return itemDao.insert(item);
	}

	public boolean update(ItemVo item) {
		item.setContent(filterWrapChar(item.getContent()));
		item.setDesc(filterWrapChar(item.getDesc()));
		return itemDao.update(item);
	}
	
	public boolean delete(int seq) {
		return itemDao.delete(seq);
	}
	
	public ItemVo get(int seq){
		return itemDao.get(seq);
	}
	
	public String filterWrapChar(String text){
		text = text.replaceAll("\\r\\n|\\r|\\n", "");
//		text = text.replaceAll("\\R", "");
		return text;
	}
}
