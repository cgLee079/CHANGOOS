package com.cglee079.changoos.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cglee079.changoos.dao.BlogDao;
import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.util.Formatter;
import com.google.gson.Gson;

@Service
public class BlogService{
	
	@Autowired
	BlogDao blogDao;
	
	public List<BlogVo> list(Map<String, Object> map){
		return blogDao.list(map);
	}
	
	public List<BlogVo> paging(Map<String, Object> params){
		int page = Integer.parseInt((String)params.get("page"));
		int perPgLine = Integer.parseInt((String)params.get("perPgLine"));
		int startRow = (page - 1) * perPgLine;
		params.put("startRow", startRow);
		
		String tags = (String)params.get("tags");
		String[] tagArray = new Gson().fromJson(tags, String[].class);
		params.put("tags", new ArrayList<String>(Arrays.asList(tagArray)));
		
		List<BlogVo> blogs 	= blogDao.paging(params);
		BlogVo blog 		= null;
		String contents 	= null;
		String newContents 	= null;
		Document doc 		= null;
		Elements els		= null;
		List<String> eachText = null;
		for(int i = 0; i < blogs.size(); i++) {
			blog 		= blogs.get(i);
			
			//내용중 텍스트만 뽑기
			contents 	= blog.getContents();
			newContents = "";
			doc 		= Jsoup.parse(contents);
			els 		= doc.select("*");
			eachText 	= els.eachText();
			
			for(int j = 0; j < eachText.size(); j++) {
				newContents += eachText.get(j) + " ";
			}
			
			newContents.replace("\n", " ");
			blog.setContents(newContents);
			
			//스냅샷 없을 경우, 설정하기
			if(blog.getSnapsht() == null) {
				els 	= doc.select("img");
				if(els.size() > 0) {
					blog.setSnapsht(els.get(0).attr("src"));
				}
			}
		}
		
		return blogs;
	}

	public int count(Map<String, Object> params) {
		return blogDao.count(params);
	}

	public BlogVo get(int seq) {
		BlogVo blog = blogDao.get(seq);
		if(blog.getSnapsht() == null) {
			String contents = blog.getContents();
			Document doc 	= Jsoup.parse(contents);
			Elements els 	= doc.select("img");
			
			if(els.size() > 0) {
				blog.setSnapsht(els.get(0).attr("src"));
			}
		}
		
		return blog;
	}
	
	public BlogVo doView(List<Integer> isVisitBlogs, int seq) {
		BlogVo blog = this.get(seq);
		
		if(!isVisitBlogs.contains(seq)) {
			isVisitBlogs.add(seq);
			blog.setHits(blog.getHits() + 1);
			blogDao.update(blog);
		}
		return blog;
	}

	public int insert(BlogVo blog) {
		blog.setDate(Formatter.toDate(new Date()));
		blog.setHits(0);
		return blogDao.insert(blog);
	}

	public boolean delete(int seq) {
		return blogDao.delete(seq);
	}
	
	public List<String> getContentImgPath(int seq, String path){
		List<String> imgPaths = new ArrayList<String>();
		BlogVo blog = blogDao.get(seq);
		String content = blog.getContents();
		
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
	
	public boolean update(BlogVo blog) {
		return blogDao.update(blog);
	}

	public List<String> getTags() {
		HashMap<String, Object> tagMap = new HashMap<>();
		List<String> tagDummys = blogDao.getTags();
		
		String[] split = null;
		String	tagDummy = null;
		for(int i = 0; i < tagDummys.size(); i++) {
			tagDummy = tagDummys.get(i);
			split = tagDummy.split(" ");
			
			for(int j = 0; j < split.length; j++) {
				tagMap.put(split[j], null);
			}
		}
		
		List<String> tags = new ArrayList<String>();
		Iterator<String> iter = tagMap.keySet().iterator();
		while(iter.hasNext()) {
			tags.add(iter.next());
		}
		
		return tags;
	}
	
}
