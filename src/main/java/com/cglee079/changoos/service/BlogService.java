package com.cglee079.changoos.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.dao.BlogDao;
import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.util.MyFileUtils;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.TimeStamper;
import com.google.gson.Gson;

@Service
public class BlogService{
	@Autowired
	BlogDao blogDao;
	
	@Value("#{servletContext.getRealPath('/')}")
    private String realPath;
	
	public List<BlogVo> list(Map<String, Object> map){
		List<BlogVo> blogs 	= blogDao.list(map);
		BlogVo blog = null;
		for(int i = 0; i < blogs.size(); i++) {
			blog 		= blogs.get(i);
			
			//스냅샷 없을 경우, 설정하기
			blog.setSnapsht(extractSnapsht(blog));
		}
		
		return blogs;
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
		String contents 	= "";
		String newContents 	= "";
		Document doc 		= null;
		Elements els		= null;
		for(int i = 0; i < blogs.size(); i++) {
			blog 		= blogs.get(i);
			
			//스냅샷 없을 경우, 설정하기
			blog.setSnapsht(extractSnapsht(blog));
			
			//내용중 텍스트만 뽑기
			contents 	= blog.getContents();
			newContents = "";
			doc 		= Jsoup.parse(contents);
			els 		= doc.select("*");
			if(els.eachText().size() > 0) {
				newContents = els.eachText().get(0);
			}
			
			newContents.replace("\n", " ");
			blog.setContents(newContents);
			
		}
		
		return blogs;
	}

	public int count(Map<String, Object> params) {
		return blogDao.count(params);
	}

	//No Extract Snapshot
	public BlogVo get(int seq) {
		return blogDao.get(seq);
	}
	
	public BlogVo doView(List<Integer> isVisitBlogs, int seq) {
		BlogVo blog = blogDao.get(seq);
		
		if(!isVisitBlogs.contains(seq)) {
			isVisitBlogs.add(seq);
			blog.setHits(blog.getHits() + 1);
			blogDao.update(blog);
		}
		
		blog.setSnapsht(extractSnapsht(blog));
		
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
	
	public String extractSnapsht(BlogVo blog) {
		Document doc 		= null;
		Elements els		= null;
		String contents 	= blog.getContents();
		String snapsht 		= blog.getSnapsht();
			
		//스냅샷 없을 경우, 설정하기
		if(blog.getSnapsht() == null) {
			doc	= Jsoup.parse(contents);
			els = doc.select("img");
			if(els.size() > 0) {
				snapsht = els.get(0).attr("src");
			}
		}
		
		return snapsht; 
	}

	public String saveSnapsht(BlogVo blog, MultipartFile snapshtFile) throws IllegalStateException, IOException {
		String filename	= "snapshot_" + blog.getTitle() + "_";
		String imgExt	= null;
		String path 	= blog.getSnapsht();
		if(snapshtFile.getSize() > 0){
			if(blog.getSeq() != 0) {
				MyFileUtils.delete(realPath + this.get(blog.getSeq()).getSnapsht());
			}
			
			filename += MyFileUtils.sanitizeRealFilename(snapshtFile.getOriginalFilename());
			imgExt = ImageManager.getExt(filename);
			File file = new File(realPath + Path.BLOG_SNAPSHT_PATH, filename);
			snapshtFile.transferTo(file);
			if(!imgExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
				BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
				ImageIO.write(image, imgExt, file);
			}
			path = Path.BLOG_SNAPSHT_PATH + filename;
		} 
		
		return path;
	}

	public void removeSnapshtFile(BlogVo blog) {
		MyFileUtils.delete(realPath + blog.getSnapsht());
	}
	
}
