package com.cglee079.changoos.model;

import java.util.List;

public class BlogVo {
	private int seq;
	private String snapsht;
	private String title;
	private String contents;
	private String date;
	private String tag;
	private int hits;
	private int comtCnt;
	private boolean enabled;
	private List<ImageVo> images;
	private List<FileVo> files;

	//첨부 이미지중, 첫번째 이미지를 스냅샷으로
	public String extractSnapsht() {
		//스냅샷 없을 경우, 설정하기
		if(snapsht == null && images != null && images.size() > 0) {
			ImageVo image = images.get(0);
			snapsht =  image.getPathname();
		}
		return snapsht; 
	}
	
	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getSnapsht() {
		return snapsht;
	}

	public void setSnapsht(String snapsht) {
		this.snapsht = snapsht;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getComtCnt() {
		return comtCnt;
	}

	public void setComtCnt(int comtCnt) {
		this.comtCnt = comtCnt;
	}

	public List<ImageVo> getImages() {
		return images;
	}

	public void setImages(List<ImageVo> images) {
		this.images = images;
	}

	public List<FileVo> getFiles() {
		return files;
	}

	public void setFiles(List<FileVo> files) {
		this.files = files;
	}
	
	
	
}
