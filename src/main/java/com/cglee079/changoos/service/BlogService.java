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

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.BlogDao;
import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.model.BoardFileVo;
import com.cglee079.changoos.model.BoardImageVo;
import com.cglee079.changoos.util.AuthManager;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.MyFileUtils;
import com.cglee079.changoos.util.MyFilenameUtils;
import com.google.gson.Gson;

@Service
public class BlogService{
	@Autowired private BoardImageService boardImageService;
	@Autowired private BoardFileService boardFileService;
	@Autowired private BlogDao blogDao;
	
	@Value("#{servletContext.getRealPath('/')}")	private String realPath;
	
	@Value("#{location['blog.file.dir.url']}") 	private String fileDir;
	@Value("#{location['blog.image.dir.url']}") private String imageDir;
	@Value("#{location['blog.thumb.dir.url']}")	private String thumbDir;
	
	@Value("#{db['blog.file.tb.name']}") private String fileTB;
	@Value("#{db['blog.image.tb.name']}")private String imageTB;
	
	@Value("#{constant['blog.thumb.max.width']}")private int thumbMaxWidth;
	
	
	
	public BlogVo get(int seq) {
		BlogVo blog = blogDao.get(seq);
		blog.setFiles(boardFileService.list(fileTB, seq));
		blog.setImages(boardImageService.list(imageTB, seq));
		
		String contents = blog.getContents();
		if (contents != null) {
			blog.setContents(contents.replace("&", "&amp;"));
		}
		
		return blog;
	}
	
	public BlogVo doView(List<Integer> isVisitBlogs, int seq) {
		BlogVo blog = blogDao.get(seq);
		blog.setFiles(boardFileService.list(fileTB, seq));
		blog.setImages(boardImageService.list(imageTB, seq));
		
		if(!isVisitBlogs.contains(seq) && !AuthManager.isAdmin() ) {
			isVisitBlogs.add(seq);
			blog.setHits(blog.getHits() + 1);
			blogDao.update(blog);
		}
		
		return blog;
	}
	
	public List<BlogVo> list(Map<String, Object> map){
		List<BlogVo> blogs 	= blogDao.list(map);
		BlogVo blog = null;
		for(int i = 0; i < blogs.size(); i++) {
			blog 		= blogs.get(i);
			blog.setImages(boardImageService.list(imageTB, blog.getSeq()));
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
			blog 	= blogs.get(i);
			blog.setImages(boardImageService.list(imageTB, blog.getSeq()));
			
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

	@Transactional
	public int insert(BlogVo blog, MultipartFile thunmbnailFile, String imageValues, String fileValues) throws IllegalStateException, IOException {
		blog.setThumbnail( this.saveThumbnail(blog, thunmbnailFile));
		blog.setDate(Formatter.toDate(new Date()));
		blog.setHits(0);
		
		int seq = blogDao.insert(blog);
		
		boardFileService.insertFiles(fileTB, fileDir, seq, fileValues);
		
		String contents = boardImageService.insertImages(imageTB, imageDir, seq, blog.getContents(), imageValues);
		blog.setContents(contents);
		blogDao.update(blog);
		
		return seq;
	}

	@Transactional
	public boolean update(BlogVo blog, MultipartFile thunmbnailFile, String imageValues, String fileValues) throws IllegalStateException, IOException {
		blog.setThumbnail(this.saveThumbnail(blog, thunmbnailFile));
		
		int seq = blog.getSeq();
		
		boardFileService.insertFiles(fileTB, fileDir, seq, fileValues);
		
		String contents = boardImageService.insertImages(imageTB, imageDir, seq, blog.getContents(), imageValues);
		blog.setContents(contents);
		
		boolean result = blogDao.update(blog);
		return result;
	}
	
	@Transactional
	public boolean delete(int seq) {
		BlogVo blog = blogDao.get(seq);
		List<BoardFileVo> files = boardFileService.list(fileTB, seq);
		List<BoardImageVo> images = boardImageService.list(imageTB, seq);
		MyFileUtils fileUtils = MyFileUtils.getInstance();
		
		
		if(blogDao.delete(seq)) {
			//스냅샷 삭제
			if(blog.getThumbnail() != null) {
				fileUtils.delete(realPath + thumbDir, blog.getThumbnail());
			}
			
			//첨부 파일 삭제
			for (int i = 0; i < files.size(); i++) {
				fileUtils.delete(realPath + fileDir, files.get(i).getPathname());
			}
			
			//첨부 이미지 삭제
			for (int i = 0; i < images.size(); i++) {
				fileUtils.delete(realPath + imageDir, images.get(i).getPathname());
			}
			
			return true;
		}
		
		return false;
	}
	
	public List<String> getTags() {
		Map<String, Object> params = new HashMap<>();
		params.put("enabled", true);
		List<String> tagDummys = blogDao.getTags(params);
		
		HashMap<String, Object> tagMap = new HashMap<>();
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
	
	public String saveThumbnail(BlogVo blog, MultipartFile thumbnailFile) throws IllegalStateException, IOException {
		String filename = thumbnailFile.getOriginalFilename();
		String imgExt = MyFilenameUtils.getExt(filename);
		String pathname = null;
		
		if(thumbnailFile.getSize() > 0){
			MyFileUtils fileUtils = MyFileUtils.getInstance();
			if(blog.getSeq() != 0) {
				fileUtils.delete(realPath + thumbDir, blogDao.get(blog.getSeq()).getThumbnail());
			}
			
			pathname = "BLOG.THUMB." + MyFilenameUtils.getRandomImagename(imgExt);
			File file = new File(realPath + thumbDir, pathname);
			thumbnailFile.transferTo(file);
			
			if(!imgExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
				ImageManager imageManager = ImageManager.getInstance();
				BufferedImage image = imageManager.getLowScaledImage(file, thumbMaxWidth, imgExt);
				ImageIO.write(image, imgExt, file);
			}
			
			
		} 
		
		return pathname;
	}
	
}
