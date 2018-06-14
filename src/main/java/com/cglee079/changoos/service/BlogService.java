package com.cglee079.changoos.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.BlogDao;
import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.TimeStamper;
import com.google.gson.Gson;

@Service
public class BlogService{
	public static final String SNAPSHT_PATH		= "/uploaded/blogs/snapshts/";
	public static final String CONTENTS_PATH	= "/uploaded/blogs/contents/";
	
	@Autowired
	BlogDao blogDao;
	
	@Value("#{servletContext.contextPath}")
    private String contextPath;
	
	@Value("#{servletContext.getRealPath('/')}")
    private String realPath;
	
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
		for(int i = 0; i < blogs.size(); i++) {
			blog 		= blogs.get(i);
			
			//내용중 텍스트만 뽑기
			contents 	= blog.getContents();
			newContents = "";
			doc 		= Jsoup.parse(contents);
			els 		= doc.select("*");
			newContents = els.eachText().get(0);
			
			newContents.replace("\n", " ");
			blog.setContents(newContents);
			
			//스냅샷 없을 경우, 설정하기
			if(blog.getSnapsht() == null) {
				els 	= doc.select("img");
				if(els.size() > 0) {
					blog.setSnapsht(els.get(0).attr("src"));
				}
			} else {
				blog.setSnapsht(contextPath + blog.getSnapsht());
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
			} else {
				blog.setSnapsht(contextPath + blog.getSnapsht());
			}
		}
		
		return blog;
	}
	
	public BlogVo doView(List<Integer> isVisitBlogs, int seq) {
		BlogVo blog = blogDao.get(seq);
		
		if(!isVisitBlogs.contains(seq)) {
			isVisitBlogs.add(seq);
			blog.setHits(blog.getHits() + 1);
			blogDao.update(blog);
		}
		
		if(blog.getSnapsht() == null) {
			String contents = blog.getContents();
			Document doc 	= Jsoup.parse(contents);
			Elements els 	= doc.select("img");
			
			if(els.size() > 0) {
				blog.setSnapsht(els.get(0).attr("src"));
			} else {
				blog.setSnapsht(contextPath + blog.getSnapsht());
			}
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

	public String saveSnapsht(BlogVo blog, MultipartFile snapshtFile) throws IllegalStateException, IOException {
		String filename	= "snapshot_" + blog.getTitle() + "_";
		String imgExt	= null;
		String path = blog.getSnapsht();
		if(snapshtFile.getSize() > 0){
			File existFile = new File (realPath + blog.getSnapsht());
			if(existFile.exists()){
				existFile.delete();
			}
			
			filename += snapshtFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(realPath + SNAPSHT_PATH, filename);
			snapshtFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
			path = SNAPSHT_PATH + filename;
		} 
		
		return path;
	}

	public void removeSnapshtFile(BlogVo blog) {
		File existFile = null;
		existFile = new File (realPath + blog.getSnapsht());
		if(existFile.exists()){
			existFile.delete();
		}
	}
	
	public String saveContentImage(MultipartFile multiFile) throws IllegalStateException, IOException {
		String filename	= "content_" + TimeStamper.stamp() + "_";
		String imgExt 	= null;
		
		if(multiFile != null){
			filename += multiFile.getOriginalFilename();
			imgExt = ImageManager.getExt(filename);
			File file = new File(realPath + CONTENTS_PATH, filename);
			multiFile.transferTo(file);
			BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
			ImageIO.write(image, imgExt, file);
		}
		
		return CONTENTS_PATH + filename;
	}
	
	public void removeContentImageFile(BlogVo blog){
		List<String> imgPaths = new ArrayList<String>();
		String content = blog.getContents();
		
		Document doc = Jsoup.parse(content);
		Elements els = doc.select("img");
		Element el = null;
		
		for(int i = 0; i < els.size(); i++) {
			el = els.get(i);
			imgPaths.add(el.attr("src"));
		}
		
		File existFile;
		int imgPathsLength = imgPaths.size();
		for (int i = 0; i < imgPathsLength; i++){
			existFile = new File (realPath + imgPaths.get(i));
			if(existFile.exists()){
				existFile.delete();
			}
		}
	}

}
