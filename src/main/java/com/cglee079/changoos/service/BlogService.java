package com.cglee079.changoos.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.cglee079.changoos.util.FileHandler;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.ImageHandler;
import com.cglee079.changoos.util.MyFilenameUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class BlogService{
	@Autowired private BoardImageService boardImageService;
	@Autowired private BoardFileService boardFileService;
	@Autowired private BlogDao blogDao;
	@Autowired private ImageHandler imageHandler;
	@Autowired private FileHandler fileHandler; 
	
	@Value("#{servletContext.getRealPath('/')}")	private String realPath;
	
	@Value("#{location['blog.file.dir.url']}") 	private String fileDir;
	@Value("#{location['blog.image.dir.url']}") private String imageDir;
	@Value("#{location['blog.thumb.dir.url']}")	private String thumbDir;
	@Value("#{location['temp.dir.url']}")	private String tempDir;
	
	@Value("#{db['blog.file.tb.name']}") private String fileTB;
	@Value("#{db['blog.image.tb.name']}")private String imageTB;
	
	@Value("#{constant['blog.thumb.max.width']}")private int thumbMaxWidth;
	
	public int count(Map<String, Object> params) {
		return blogDao.count(params);
	}
	
	public Set<String> getTags(Map<String,Object> params) {
		List<String> tagDummys = blogDao.getTags(params);
		Set<String> tags = new HashSet<String>();
		
		String[] split = null;
		String	tagDummy = null;
		for(int i = 0; i < tagDummys.size(); i++) {
			tagDummy = tagDummys.get(i);
			split = tagDummy.split(" ");
			
			for(int j = 0; j < split.length; j++) {
				tags.add(split[j]);
			}
		}
		
		
		return tags;
	}
	
	public BlogVo get(int seq) {
		BlogVo blog = blogDao.get(seq);
		
		String contents = blog.getContents();
		if (contents != null) {
			blog.setContents(contents.replace("&", "&amp;"));
		}
		
		return blog;
	}
	
	@Transactional
	public BlogVo doView(Set<Integer> isVisitBlogs, int seq) {
		BlogVo blog = blogDao.get(seq);
		
		if(!isVisitBlogs.contains(seq) && !AuthManager.isAdmin() ) {
			isVisitBlogs.add(seq);
			blog.setHits(blog.getHits() + 1);
			blogDao.update(blog);
		}
		
		return blog;
	}
	
	public List<BlogVo> list(Map<String, Object> params){
		
		List<BlogVo> blogs 	= blogDao.list(params);
		BlogVo blog 		= null;
		String contents 	= "";
		String newContents 	= "";
		Document doc 		= null;
		Elements els		= null;
		for(int i = 0; i < blogs.size(); i++) {
			blog 	= blogs.get(i);
			
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

	@Transactional
	public int insert(BlogVo blog, String tempDirId, String imageValues, String fileValues) throws JsonParseException, JsonMappingException, IOException{
		blog.setDate(Formatter.toDate(new Date()));
		int seq = blogDao.insert(blog);
		
		if(blog.getThumbnail() != null && blog.getThumbnail().length() > 0) {
			fileHandler.move(realPath + tempDir + tempDirId + blog.getThumbnail(), realPath + thumbDir + blog.getThumbnail());
		}
		
		boardFileService.insertFiles(fileTB, tempDirId, fileDir, seq, fileValues);
		
		String contents = boardImageService.insertImages(imageTB, tempDirId, imageDir, seq, blog.getContents(), imageValues);
		blog.setContents(contents);
		blogDao.update(blog);
		
		return seq;
	}

	@Transactional
	public boolean update(BlogVo blog,  String tempDirId, String imageValues, String fileValues) throws JsonParseException, JsonMappingException, IOException{
		int seq = blog.getSeq();
		
		BlogVo savedBlog = blogDao.get(seq);
		
		if(!blog.getThumbnail().equals(savedBlog.getThumbnail())) {
			fileHandler.delete(realPath + thumbDir + savedBlog.getThumbnail());
			fileHandler.move(realPath + tempDir + tempDirId + blog.getThumbnail(), realPath + thumbDir + blog.getThumbnail());
		}
		
		boardFileService.insertFiles(fileTB, tempDirId,fileDir, seq, fileValues);
		
		String contents = boardImageService.insertImages(imageTB, tempDirId,imageDir, seq, blog.getContents(), imageValues);
		blog.setContents(contents);
		
		boolean result = blogDao.update(blog);
		return result;
	}

	@Transactional
	public boolean delete(int seq) {
		BlogVo blog = blogDao.get(seq);
		List<BoardFileVo> files = blog.getFiles();
		List<BoardImageVo> images = blog.getImages();
		
		if(blogDao.delete(seq)) {
			//스냅샷 삭제
			if(blog.getThumbnail() != null) {
				fileHandler.delete(realPath + thumbDir, blog.getThumbnail());
			}
			
			//첨부 파일 삭제
			for (int i = 0; i < files.size(); i++) {
				fileHandler.delete(realPath + fileDir, files.get(i).getPathname());
			}
			
			//첨부 이미지 삭제
			for (int i = 0; i < images.size(); i++) {
				fileHandler.delete(realPath + imageDir, images.get(i).getPathname());
			}
			
			return true;
		}
		
		return false;
	}
	
	public String saveThumbnail(MultipartFile thumbnailFile, String tempDirId) {
		String pathname = null;
		
		if(thumbnailFile.getSize() > 0){
			String filename = thumbnailFile.getOriginalFilename();
			String imgExt = MyFilenameUtils.getExt(filename);
			
			pathname = "BLOG.THUMB." + MyFilenameUtils.getRandomImagename(imgExt);
			File file = fileHandler.save(realPath + tempDir + tempDirId + pathname, thumbnailFile);
			
			imageHandler.saveLowscaleImage(file, thumbMaxWidth, imgExt);
			
		} 
		
		return pathname;
	}
	
}
