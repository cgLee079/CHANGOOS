package com.cglee079.portfolio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.ItemDao;
import com.cglee079.portfolio.model.Item;

@Service
public class ItemService {

	@Autowired
	private ItemDao itemDao;
	
	public List<Item> list(){
		return itemDao.list();
	}
}
