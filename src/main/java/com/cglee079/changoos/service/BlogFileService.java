package com.cglee079.changoos.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.BlogFileDao;
import com.cglee079.changoos.model.BlogFileVo;
import com.cglee079.changoos.util.FileUtils;
import com.cglee079.changoos.util.TimeStamper;

@Service
public class BlogFileService {
	public static final String FILE_PATH = "/uploaded/blogs/files/";
	
	@Autowired
	BlogFileDao blogFileDao;
	
	@Value("#{servletContext.getRealPath('/')}")
    private String realPath;
	
	public boolean insert(BlogFileVo studyFile) {
		return blogFileDao.insert(studyFile);
	}

	public List<BlogFileVo> list(int seq) {
		return blogFileDao.list(seq);
	}

	public BlogFileVo get(String pathNm) {
		return blogFileDao.getFile(pathNm);
	}

	public BlogFileVo get(int seq) {
		return blogFileDao.getFile(seq);
	}

	public boolean delete(int seq) {
		return blogFileDao.delete(seq);
	}
	
	/** 여러 파일 저장 */
	public void saveFiles(int seq, List<MultipartFile> files) throws IllegalStateException, IOException {
		File file = null;
		MultipartFile multipartFile = null;
		BlogFileVo blogFile = null;
		String realNm = null;
		String pathNm = null;
		long size = -1;
		int length = files.size();
		
		for(int i = 0 ; i < length ; i++){
			multipartFile = files.get(i);
			realNm 	= FileUtils.sanitizeFilename(multipartFile.getOriginalFilename());
			pathNm	= "blog" + seq + "_" + TimeStamper.stamp() + "_" + realNm;
			size 	= multipartFile.getSize();
			
			if(size > 0 ){
				file = new File(realPath + FILE_PATH, pathNm);
				multipartFile.transferTo(file);
				
				blogFile = new BlogFileVo();
				blogFile.setPathNm(pathNm);
				blogFile.setRealNm(realNm);
				blogFile.setSize(size);
				blogFile.setBlogSeq(seq);
				this.insert(blogFile);
			}
		}
	}
	
	/** 한 게시글에 종속된 파일 삭제 **/
	public void deleteFiles(int studySeq) {
		//File 삭제
		File existFile = null;
		List<BlogFileVo> files = this.list(studySeq);
		BlogFileVo file = null;
		int fileLength = files.size();
		for(int i = 0 ;  i < fileLength; i++){
			file = files.get(i);
			existFile = new File(realPath + FILE_PATH, file.getPathNm());
			if(existFile.exists()){
				existFile.delete();
			}
		}
	}
	
	/** 파일 삭제 **/
	public boolean deleteFile(int fileSeq) {
		BlogFileVo studyFile = this.get(fileSeq);
		File file = new File(realPath + FILE_PATH, studyFile.getPathNm());
		if(file.exists()){
			if(file.delete()){
				if(this.delete(fileSeq)){
					return true;
				};
			}
		} 
		return false;
	}
	

}
