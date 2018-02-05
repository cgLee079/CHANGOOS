package com.cglee079.portfolio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.portfolio.dao.ProjectDao;
import com.cglee079.portfolio.dao.PorjectFileDao;
import com.cglee079.portfolio.model.FileVo;
import com.cglee079.portfolio.model.ProjectVo;

@Service
public class ProjectService {
	@Autowired
	private ProjectDao itemDao;
	
	@Autowired
	private PorjectFileDao itemFileDao;
	
	public List<ProjectVo> list(){
		return itemDao.list();
	}
	
	public int insert(ProjectVo item) {
		item.setContents(item.getContents());
		item.setDesc(item.getDesc());
		
		item.setHits(0);
		return itemDao.insert(item);
	}

	public boolean update(ProjectVo item) {
		return itemDao.update(item);
	}
	
	public boolean delete(int seq) {
		return itemDao.delete(seq);
	}
	
	public ProjectVo get(int seq){
		return itemDao.get(seq);
	}
	
	public boolean saveFile(FileVo boardFile) {
		return itemFileDao.insert(boardFile);
	}

	public List<FileVo> getFiles(int seq) {
		return itemFileDao.getFiles(seq);
	}

	public FileVo getFile(String pathNm) {
		return itemFileDao.getFile(pathNm);
	}

	public FileVo getFile(int seq) {
		return itemFileDao.getFile(seq);
	}

	public boolean deleteFile(int seq) {
		return itemFileDao.delete(seq);
	}
	
	public List<String> getContentImgPath(int seq, String path){
		List<String> imgPaths = new ArrayList<String>();
		ProjectVo item = itemDao.get(seq);
		String content = item.getContents();
		
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

	public ProjectVo getBefore(int sort) {
		return itemDao.getBefore(sort);
	}

	public ProjectVo getAfter(int sort) {
		return itemDao.getAfter(sort);
	}

}
