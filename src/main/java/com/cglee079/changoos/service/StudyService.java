package com.cglee079.changoos.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cglee079.changoos.dao.StudyDao;
import com.cglee079.changoos.model.StudyVo;
import com.cglee079.changoos.util.FileUtils;
import com.cglee079.changoos.util.Formatter;
import com.cglee079.changoos.util.ImageManager;
import com.cglee079.changoos.util.TimeStamper;

@Service
public class StudyService{
	public static final String CONTENTS_PATH	= "/uploaded/studies/contents/";
	
	@Autowired
	StudyDao studyDao;
	
	@Value("#{servletContext.getRealPath('/')}")
    private String realPath;
	
	public List<StudyVo> list(Map<String, Object> map){
		return studyDao.list(map);
	}
	
	public List<StudyVo> paging(Map<String, Object> params){
		int page = Integer.parseInt((String)params.get("page"));
		int perPgLine = Integer.parseInt((String)params.get("perPgLine"));
		int startRow = (page - 1) * perPgLine;
		params.put("startRow", startRow);
		
		List<StudyVo> studies = studyDao.list(params);
		StudyVo study 		= null;
		String contents 	= null;
		String newContents 	= "";
		Document doc 		= null;
		Elements els		= null;
		for(int i = 0; i < studies.size(); i++) {
			study 		= studies.get(i);
			
			//내용중 텍스트만 뽑기
			contents 	= study.getContents();
			newContents = "";
			doc 		= Jsoup.parse(contents);
			els 		= doc.select("*");
			if(els.eachText().size() > 0) {
				newContents = els.eachText().get(0);
			}
			
			newContents.replace("\n", " ");
			study.setContents(newContents);
		}
		
		return studies;
	}

	public int count(Map<String, Object> params) {
		return studyDao.count(params);
	}

	public StudyVo get(int seq) {
		return studyDao.get(seq);
	}
	
	public StudyVo doView(List<Integer> isVisitStudies, int seq) {
		StudyVo study = studyDao.get(seq);
		
		if(!isVisitStudies.contains(seq)) {
			isVisitStudies.add(seq);
			study.setHits(study.getHits() + 1);
			studyDao.update(study);
		}
		return study;
	}

	public int insert(StudyVo study) {
		study.setDate(Formatter.toDate(new Date()));
		study.setHits(0);
		return studyDao.insert(study);
	}

	public boolean delete(int seq) {
		return studyDao.delete(seq);
	}
	
	public boolean update(StudyVo study) {
		return studyDao.update(study);
	}

	public StudyVo getBefore(int seq, String category) {
		return studyDao.getBefore(seq, category);
	}

	public StudyVo getAfter(int seq, String category) {
		return studyDao.getAfter(seq, category);
	}

	public List<String> getCategories() {
		return studyDao.getCategories();
	}

	public void removeContentImageFile(int seq) {
		List<String> imgPaths = new ArrayList<String>();
		StudyVo study = studyDao.get(seq);
		String content = study.getContents();
		
		Document doc = Jsoup.parse(content);
		Elements els = doc.select("img");
		Element el = null;
		
		for(int i = 0; i < els.size(); i++) {
			el = els.get(i);
			imgPaths.add(el.attr("src"));
		}
		
		File existFile = null;
		int imgPathsLength = imgPaths.size();
		for (int i = 0; i < imgPathsLength; i++){
			existFile = new File (realPath + imgPaths.get(i));
			if(existFile.exists()){
				existFile.delete();
			}
		}
		
	}

	public String saveContentImageFile(MultipartFile multiFile) throws IllegalStateException, IOException {
		String filename	= "content_" + TimeStamper.stamp() + "_";
		String imgExt 	= null;
		
		if(multiFile != null){
			filename += FileUtils.sanitizeFilename(multiFile.getOriginalFilename());
			imgExt = ImageManager.getExt(filename);
			File file = new File(realPath + CONTENTS_PATH, filename);
			multiFile.transferTo(file);
			if(!imgExt.equalsIgnoreCase(ImageManager.EXT_GIF)) {
				BufferedImage image = ImageManager.getLowScaledImage(file, 720, imgExt);
				ImageIO.write(image, imgExt, file);
			}
		}
		
		return CONTENTS_PATH + filename;
	}
}
