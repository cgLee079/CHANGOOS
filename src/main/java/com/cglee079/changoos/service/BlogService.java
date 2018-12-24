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

import com.cglee079.changoos.constants.Path;
import com.cglee079.changoos.dao.BlogDao;
import com.cglee079.changoos.dao.BlogFileDao;
import com.cglee079.changoos.dao.BlogImageDao;
import com.cglee079.changoos.model.BlogFileVo;
import com.cglee079.changoos.model.BlogImageVo;
import com.cglee079.changoos.model.BlogVo;
import com.cglee079.changoos.util.AuthManager;
import com.cglee079.changoos.util.PathHandler;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.MyFileUtils;
import com.cglee079.changoos.util.MyFilenameUtils;
import com.cglee079.changoos.util.TempFolderManager;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Service
public class BlogService{
	@Autowired
	BlogDao blogDao;
	
	@Autowired
	BlogFileDao blogFileDao;
	
	@Autowired
	BlogImageDao blogImageDao;
	
	@Value("#{servletContext.getRealPath('/')}")
    private String realPath;
	
	
	public BlogVo get(int seq) {
		BlogVo blog = blogDao.get(seq);
		blog.setFiles(blogFileDao.list(seq));
		blog.setImages(blogImageDao.list(seq));
		
		String contents = blog.getContents();
		if (contents != null) {
			blog.setContents(contents.replace("&", "&amp;"));
		}
		
		return blog;
	}
	
	public BlogVo doView(List<Integer> isVisitBlogs, int seq) {
		BlogVo blog = this.get(seq);
		
		if(!isVisitBlogs.contains(seq) && !AuthManager.isAdmin() ) {
			isVisitBlogs.add(seq);
			blog.setHits(blog.getHits() + 1);
			blogDao.update(blog);
		}
		
		blog.setSnapsht(blog.extractSnapsht());
		
		return blog;
	}
	
	public List<BlogVo> list(Map<String, Object> map){
		List<BlogVo> blogs 	= blogDao.list(map);
		BlogVo blog = null;
		for(int i = 0; i < blogs.size(); i++) {
			blog 		= blogs.get(i);
			
			//스냅샷 없을 경우, 설정하기
			blog.setSnapsht(blog.extractSnapsht());
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
			blog.setSnapsht(blog.extractSnapsht());
			
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

	public int insert(BlogVo blog, MultipartFile snapshtFile, String imageValues, List<MultipartFile> files) throws IllegalStateException, IOException {
		String contents = PathHandler.changeImagePath(blog.getContents(), Path.TEMP_PATH, Path.BLOG_IMAGE_PATH);
		blog.setContents(contents);
		blog.setDate(Formatter.toDate(new Date()));
		blog.setHits(0);
		blog.setSnapsht( this.saveSnapsht(blog, snapshtFile));
		
		int seq = blogDao.insert(blog);
		
		List<BlogImageVo> images = new ObjectMapper().readValue(imageValues, new TypeReference<List<BlogImageVo>>(){});
		this.saveImages(seq, images);
		this.saveFiles(seq, files);
		
		return seq;
	}

	public boolean update(BlogVo blog, MultipartFile snapshtFile, String imageValues, List<MultipartFile> files) throws IllegalStateException, IOException {
		String contents = PathHandler.changeImagePath(blog.getContents(), Path.TEMP_PATH, Path.BLOG_IMAGE_PATH);
		blog.setContents(contents);
		blog.setSnapsht(this.saveSnapsht(blog, snapshtFile));
		
		int seq = blog.getSeq();
		boolean result = blogDao.update(blog);
		
		List<BlogImageVo> images = new ObjectMapper().readValue(imageValues, new TypeReference<List<BlogImageVo>>(){});
		this.saveImages(seq, images);
		this.saveFiles(seq, files);
		
		return result;
	}
	
	@Transactional
	public boolean delete(int seq) {
		BlogVo blog = this.get(seq);
		List<BlogFileVo> files = blog.getFiles();
		
		if(blogDao.delete(seq)) {
			//스냅샷 삭제
			if(blog.getSnapsht() != null) {
				MyFileUtils.delete(realPath + blog.getSnapsht());
			}
			
			//첨부 파일 삭제
			for (int i = 0; i < files.size(); i++) {
				MyFileUtils.delete(realPath + Path.STUDY_FILE_PATH, files.get(i).getPathname());
			}
			
			return true;
		}
		
		return false;
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
		String path 	= blog.getSnapsht();
		if(snapshtFile.getSize() > 0){
			if(blog.getSeq() != 0) {
				MyFileUtils.delete(realPath + this.get(blog.getSeq()).getSnapsht());
			}
			
			filename += MyFilenameUtils.sanitizeRealFilename(snapshtFile.getOriginalFilename());
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
	
	/***
	 * 내용 중 이미지 첨부 관련
	 ***/
	private void saveImages(int blogSeq, List<BlogImageVo> images) {
		BlogImageVo image;
		for (int i = 0; i < images.size(); i++) {
			image = images.get(i);
			image.setBlogSeq(blogSeq);
			switch(image.getStatus()) {
			case "NEW" : //새롭게 추가된 이미지
				String path = image.getPath();
				String movedPath = Path.BLOG_IMAGE_PATH;
				image.setPath(movedPath);
				
				if(blogImageDao.insert(image)) {
					//임시폴더에서 본 폴더로 이동
					File existFile  = new File(realPath + path, image.getPathname());
					File newFile	= new File(realPath + movedPath, image.getPathname());
					MyFileUtils.move(existFile, newFile);
				}
				break;
			case "REMOVE" : //기존에 있던 이미지 중, 삭제된 이미지
				if(blogImageDao.delete(image.getSeq())) {
					MyFileUtils.delete(realPath + image.getPath(), image.getPathname());
				}
				break;
			}
		}
		
		
		//업로드 파일로 이동했음에도 불구하고, 남아있는 TEMP 폴더의 이미지 파일을 삭제.
		//즉, 이전에 글 작성 중 작성을 취소한 경우 업로드가 되었던 이미지파일들이 삭제됨.
		TempFolderManager.getInstance().removeAll();
	}
	
	/***
	 *  파일 첨부 관련 
	 **/
	
	public boolean deleteFile(int fileSeq) {
		BlogFileVo blogFile = blogFileDao.get(fileSeq);
		if(blogFileDao.delete(fileSeq)) {
			if(MyFileUtils.delete(realPath + Path.BLOG_FILE_PATH, blogFile.getPathname())) {
				return true;
			}
		}
		return false;
	}
	
	
	private void saveFiles(int seq, List<MultipartFile> files) throws IllegalStateException, IOException {
		File file = null;
		MultipartFile multipartFile = null;
		BlogFileVo blogFile = null;
		String realname = null;
		String pathname = null;
		String path = Path.BLOG_FILE_PATH;
		long size = -1;
		int length = files.size();
		for (int i = 0; i < length; i++) {
			multipartFile = files.get(i);
			realname = MyFilenameUtils.sanitizeRealFilename(multipartFile.getOriginalFilename());
			pathname = MyFilenameUtils.getRandomFilename(MyFilenameUtils.getExt(realname));
			size = multipartFile.getSize();

			if (size > 0) {
				file = new File(realPath + path, pathname);
				multipartFile.transferTo(file);

				blogFile = new BlogFileVo();
				blogFile.setPath(path);
				blogFile.setPathname(pathname);
				blogFile.setFilename(realname);
				blogFile.setSize(size);
				blogFile.setBlogSeq(seq);
				blogFileDao.insert(blogFile);
			}
		}
	}

}
