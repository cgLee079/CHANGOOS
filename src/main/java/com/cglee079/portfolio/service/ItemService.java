package com.cglee079.portfolio.service;

import java.util.ArrayList;
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
		item.setContent(item.getContent());
		item.setDesc(item.getDesc());
		
		item.setHits(0);
		return itemDao.insert(item);
	}

	public boolean update(ItemVo item) {
		item.setContent(item.getContent());
		item.setDesc(item.getDesc());
		return itemDao.update(item);
	}
	
	public boolean delete(int seq) {
		return itemDao.delete(seq);
	}
	
	public ItemVo get(int seq){
		return itemDao.get(seq);
	}
	
	public List<String> getContentImgPath(int seq, String path){
		List<String> imgPaths = new ArrayList<String>();
		ItemVo item = itemDao.get(seq);
		String content = item.getContent();
		
		int stIndex = 0;
		int endIndex= 0;
		
		if(content != null){
			while(true){
				stIndex = content.indexOf(path, endIndex);
				endIndex = content.indexOf("\"", stIndex);
				
				if(stIndex == -1){ break;}
				if(endIndex == -1){ break;}
				
				imgPaths.add(content.substring(stIndex, endIndex));
			}
		}
		
		return imgPaths;
	}
}
